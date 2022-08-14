.386
.model flat,stdcall
option casemap:none

include windows.inc
include gdi32.inc
includelib gdi32.lib
include user32.inc
includelib user32.lib
include kernel32.inc
includelib kernel32.lib
include	msvcrt.inc
includelib msvcrt.lib
printf PROTO C :dword,:vararg
scanf  PROTO C :dword,:vararg
strlen PROTO C :dword


.data
hInstance dd ?  ;存放应用程序的句柄
hWinMain dd ?   ;存放窗口的句柄
hand dd ?
i dword 0
j dword 0
k dword 0
hGame dd 25 dup(?)

seed        dd          2314
max			dd			16
dat         dd          0
score        dd         0
randData    dd          0
changedW dd 1 ;向上是否发生移动，初值为0，移动后置为1
changedS dd 0 ;向下是否发生移动，初值为0，移动后置为1
changedA dd 1 ;向左是否发生移动，初值为0，移动后置为1
changedD dd 1 ;向右是否发生移动，初值为0，移动后置为1

gameIsEnd dd    0
gameIsWin dd 0
gameContinue dd 0
tmpMat dd 16 DUP(?)

Data byte 10 dup(?)

static db 'static',0
edit db 'edit',0

;操作数组
gameMat dd 0,0,0,0
		dd 0,0,0,0
		dd 0,0,0,0
		dd 0,0,0,0

;临时数组
tmpGameMat  dd 0,0,0,0
			dd 0,0,0,0
			dd 0,0,0,0
			dd 0,0,0,0
overEdge dd ? ;判断是否越界判断
exchangeNum dd ? ;中间变量，用于数字交换

row dd 1
col dd 1 

printf_pref db '%d',0ah,0 ;测试语句，输出数组数字
printf_d db '%c',0ah,0
scanf_sh byte '%c',0ah,0 ;测试语句。输入移动方向
printf_ok db 'it is ok',0ah,0 ;测试语句

hdcIDB_BITMAP1 dd ?
hbmIDB_BITMAP1 dd ?
hdcIDB_BITMAP2 dd ?
hbmIDB_BITMAP2 dd ?
IDB_BITMAP1 BYTE 'IDB_BITMAP1',0
IDB_BITMAP2 BYTE 'IDB_BITMAP2',0

.const
szClassName db 'MyClass',0
szCaptionMain db '2048',0
EmptyText byte ' ',0
szText db 'Win32 Assembly,Simple and powerful!',0
szText1 byte "Welcome to 2048!",0
szText2 byte "Use WASD to move the tiles along the grid",0
szText5 byte "If two tiles of the same number touch, they'll merge",0
szText3 byte "Try to get a 2048 tile, or go as high as you can!",0
szText4 byte "The game will end if every tile gets filled and you can't merge any tiles",0
szText7 byte "Game Is Over",0
szText6 byte "2048",0
szWinText byte 2000 dup("Congratulations!",0ah,"You have won this game!",0ah,"Thank you for playing!",0ah,0)
szText12 byte "Now you can choose 'YES' to continue,or 'No' to quit",0
BITMAP1 EQU 101
BITMAP2 EQU 104

.code
;--------------------------------------------------------------------------------------
;@Function Name :  gameWin
;@Param			:  
;@Description   :  绘制游戏胜利消息弹窗，玩家可选择继续游玩，则置gameContinue为1，之后不再弹出弹窗
;@Author        :  Chris
;--------------------------------------------------------------------------------------
gameWin proc
	invoke MessageBox,hWinMain,offset szWinText,offset szText6,MB_OK
	.if eax == IDOK
		;
		invoke MessageBox,hWinMain,offset szText12,offset szText6,MB_YESNO
		.if eax == IDYES
			mov gameContinue,1
		.elseif eax == IDNO
			invoke DestroyWindow,hWinMain
			invoke PostQuitMessage,NULL
		.endif
	.endif
	ret
	
gameWin endp
;--------------------------------------------------------------------------------------
;@Function Name :  random32
;@Param			:  random_seed  随机数种子
;					max_val		生成随机数大小限制
;@Description   :  基于输入的种子与限定的随机数最大值产生32位随机数，随后初始化矩阵
;@Author        :  WXL
;--------------------------------------------------------------------------------------
random32       proc    random_seed:DWORD,max_val:DWORD 
                push ecx				;保存寄存器信息
                push edx
                call       GetTickCount ;获取系统时间
                mov        ecx,random_seed
                add        eax,ecx		
                rol        ecx,1
                add        ecx,666h 
                mov        random_seed,ecx	;初步产生种子

                mov     ecx,32

    crc_bit:    shr        eax,1			;根据eax信息处理随机数
                jnc        loop_crc_bit 
                xor        eax,0edb88320h

    loop_crc_bit:
                loop        crc_bit
                mov         ecx,max_val

				;限定随机数大小，存入randData
                xor         edx,edx ;高16位清空
                div         ecx
                xchg        edx,eax ;余数存入eax
                or          eax,eax
				mov			randData,eax	

				;随机位置生成2，无冲突时跳转
                cmp     gameMat[eax*4],0
                je      inital_mat
				;冲突处理
                mov     ecx,16
                mov     randData,eax
                xor     eax,eax     ;存放tmp指针
                xor     edx,edx     ;存放game指针

    get_emp:    
                cmp     gameMat[edx*4],0
                jne      cmp_ne      ;格子为零
                
                mov     tmpMat[eax*4],edx
                inc     eax
    cmp_ne:         
                inc     edx
                loop    get_emp
                ;eax存放tmp长度

                mov     ecx,eax
                xor     edx,edx
                mov     eax,randData
                div     ecx
                xchg    edx,eax ;eax为tmp指针

                mov     edx,tmpMat[eax*4]
                mov     randData,edx
    inital_mat:
                mov     eax,randData
                mov     gameMat[eax*4],2
                pop edx
                pop ecx
                ret        
    random32    Endp
;--------------------------------------------------------------------------------------
;@Function Name :  getscore
;@Param			: 
;@Description   :  根据矩阵当前状态计算分数
;@Author        :  WXL
;--------------------------------------------------------------------------------------
getscore       proc
            push    ecx
            push    edx

            mov     ecx,0
            xor     eax,eax
			mov     score,0
			;遍历矩阵
 cul_score:
            mov     edx,gameMat[ecx*4]
            mov     eax,score
            .if     edx==0
            .elseif     edx==2
                
            .elseif     edx==4
                add     eax,4
            .elseif     edx==8
                add     eax,16
            .elseif     edx==16
                add     eax,48
            .elseif     edx==32
                add     eax,128
            .elseif     edx==64
                add     eax,320
            .elseif     edx==128
                add     eax,768
            .elseif     edx==256
                add     eax,1792
            .elseif     edx==512
                add     eax,4096
            .elseif     edx==1024
                add     eax,9216
            .elseif     edx==2048
                add     eax,20480
            .else
                add eax,45036
            .endif
            mov     score,eax
            inc     ecx
            cmp     ecx,16
            jb    cul_score
			;判断得分，维护标志
			.if score >= 20480
				mov gameIsWin,1
			.endif
            pop     edx
            pop     ecx

            ret
getscore        Endp

;--------------------------------------------------------------------------------------
;@Function Name :  diamondMove
;@Param			:  
;@Description   :  实现4×4的方块在四个方向上的移动与合成
;@Author        :  FY
;--------------------------------------------------------------------------------------

moveW proc far C uses eax ebx ecx edx
	;初始化是否能移动的判断变量
	MOV changedW,0
	;初始化循环起点
	mov ecx,4
	mov col,ecx
	mov row,1
w:	
	;循环到下一方向4个方块的判断起点进行判断
	mov col,ecx
	mov row,1

	jmp w_trav

w_end:
	;在同一方向结束循环，走向下一个方向的4个方块的判断
	loop w

	ret
w_trav:
	;保存比较数
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	;同一方向的四个位置进行循环判断
	cmp row,1
	je w_merge

	cmp row,2
	je w_fore

	cmp row,3
	je w_fore

	cmp row,4
	je w_fore

	jmp w_trav

w_mov:
	;跳转到下一列4方块的判断
	inc row
	cmp row,5
	jb w_trav

	jmp w_end

w_merge:
	;判断是否为0，若为0则跳过判断
	cmp edx,0
	je w_mov

	add ebx,4
	;判断是否跨行比较，若跨行比较则进行下一个同方向的方块的判断
	cmp ebx,16
	jae w_mov

	;是否与本位方块进行比较
	cmp eax,ebx
	je w_merge

	;若在合并方向上寻找到0，则继续探索是否有可合并方块
	cmp gameMat[ebx*4],0
	je w_merge
	;若在合并方向上寻找到相同数字方块，则转入合并函数
	cmp gameMat[ebx*4],edx
	je w_equ

	jmp w_mov

w_equ:
	;若判断遇到相等数字方块，则进行合并
	imul edx,2
	mov gameMat[eax*4],edx
	mov gameMat[ebx*4],0

	;对是否能进行移动标志位进行更新
    mov exchangeNum,edx
	mov edx,1
	mov changedW,edx
	mov edx,exchangeNum

	jmp w_mov

w_fore:
	;向反方向探索，若遇到0则进行移动
	cmp edx,0
	je w_mov
	mov ebx,eax
	sub ebx,4
	
	cmp gameMat[ebx*4],0
	je w_zero

	jmp w_merge

w_zero:
	;遇到0后移动方块
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0
	
	;对是否能进行移动标志位进行更新
    mov exchangeNum,edx
	mov edx,1
	mov changedW,edx
	mov edx,exchangeNum
    
	mov eax,ebx
	sub ebx,4
	;边界检测
	cmp ebx,4000
	ja w_merge
	;若前方仍有零，继续进行判断
	cmp gameMat[ebx*4],0
	je w_zero

	jmp w_merge
moveW endp

moveD proc far C uses eax ebx ecx edx
	;初始化是否能移动的判断变量
	mov changedD,0
	;初始化循环起点
	mov ecx,4
	mov col,ecx
	mov row,4

d:
	;循环到下一方向4个方块的判断起点进行判断
	mov row,ecx
	mov col,4

	jmp d_trav

d_end:
	;在同一方向结束循环，走向下一个方向的4个方块的判断
	loop d

	ret
d_trav:
	;保存比较数
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	;同一方向的四个位置进行循环判断
	cmp col,4
	je d_merge

	cmp col,3
	je d_fore

	cmp col,2
	je d_fore

	cmp col,1
	je d_fore

	jmp d_trav
d_mov:
	;跳转到下一列4方块的判断
	dec col
	cmp col,0
	ja d_trav

	jmp d_end
d_merge:
	;判断是否为0，若为0则跳过判断
	cmp edx,0
	je d_mov

	dec ebx
	;判断是否跨行比较，若跨行比较则进行下一个同方向的方块的判断
	mov overEdge,eax
	mov eax,row
	dec eax
	imul eax,4
	dec eax
	cmp eax,ebx
	je d_mov
	mov eax,overEdge

	;是否与本位方块进行比较
	cmp eax,ebx
	je d_merge

	;若在合并方向上寻找到0，则继续探索是否有可合并方块
	cmp gameMat[ebx*4],0
	je d_merge

	;若在合并方向上寻找到相同数字方块，则转入合并函数
	cmp gameMat[ebx*4],edx
	je d_equ

	jmp d_mov

d_equ:
	;若判断遇到相等数字方块，则进行合并
	imul edx,2
	mov gameMat[eax*4],edx
	mov gameMat[ebx*4],0

	;对是否能进行移动标志位进行更新
    mov exchangeNum,edx
	mov edx,1
	mov changedD,edx
	mov edx,exchangeNum

	jmp d_mov
d_fore:
	;向反方向探索，若遇到0则进行移动
	cmp edx,0
	je d_mov
	mov ebx,eax
	inc ebx

	cmp gameMat[ebx*4],0
	je d_zero

	jmp d_merge
d_zero:
	;遇到0后移动方块
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0

	;对是否能进行移动标志位进行更新
    mov exchangeNum,edx
	mov edx,1
	mov changedD,edx
	mov edx,exchangeNum
    
	mov eax,ebx
	inc ebx

	;边界检测
	mov overEdge,ebx
	mov ebx,row
	imul ebx,4
	cmp overEdge,ebx
	je d_merge
	mov ebx,overEdge

	;若前方仍有零，继续进行判断
	cmp gameMat[ebx*4],0
	je d_zero

	jmp d_merge
moveD endp

moveA proc far C uses eax ebx ecx edx
	;初始化是否能移动的判断变量
	mov changedA,0
	;初始化循环起点
	mov ecx,4
	mov row,ecx
	mov col,1
a:
	;循环到下一方向4个方块的判断起点进行判断
	mov row,ecx
	mov col,1

	jmp a_trav

a_end:
	loop a

	ret
a_trav:
	;保存比较数
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	;同一方向的四个位置进行循环判断
	cmp col,1
	je a_merge

	cmp col,2
	je a_fore

	cmp col,3
	je a_fore

	cmp col,4
	je a_fore

	jmp a_trav

a_mov:
	;跳转到下一行4方块的判断
	inc col
	cmp col,5
	jb a_trav

	jmp a_end
a_merge:
	;判断是否为0，若为0则跳过判断
	cmp edx,0
	je a_mov

	inc ebx
	;判断是否跨行比较，若跨行比较则进行下一个同方向的方块的判断
	mov overEdge,eax
	mov eax,row
	imul eax,4
	cmp eax,ebx
	je a_mov
	mov eax,overEdge

	;是否与本位方块进行比较
	cmp eax,ebx
	je a_merge

	;若在合并方向上寻找到0，则继续探索是否有可合并方块
	cmp gameMat[ebx*4],0
	je a_merge

	;若在合并方向上寻找到相同数字方块，则转入合并函数
	cmp gameMat[ebx*4],edx
	je a_equ

	jmp a_mov

a_equ:
	;若判断遇到相等数字方块，则进行合并
	imul edx,2
	mov gameMat[eax*4],edx
	mov gameMat[ebx*4],0

	;对是否能进行移动标志位进行更新
    mov exchangeNum,edx
	mov edx,1
	mov changedA,edx
	mov edx,exchangeNum
    
	jmp a_mov

a_fore:
	;向反方向探索，若遇到0则进行移动
	cmp edx,0
	je a_mov
	mov ebx,eax
	dec ebx
	
	cmp gameMat[ebx*4],0
	je a_zero

	jmp a_merge

a_zero:
	;遇到0后移动方块
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0

	;对是否能进行移动标志位进行更新
	mov exchangeNum,edx
	mov edx,1
	mov changedA,edx
	mov edx,exchangeNum

	mov eax,ebx
	dec ebx

	;边界检测
	mov overEdge,ebx
	mov ebx,row
	dec ebx
	imul ebx,4
	dec ebx
	cmp overEdge,ebx
	je a_merge
	mov ebx,overEdge

	;若前方仍有零，继续进行判断
	cmp gameMat[ebx*4],0
	je a_zero

	jmp a_merge
moveA endp

moveS proc far C uses eax ebx ecx edx
	;初始化是否能移动的判断变量
	mov changedS,0

	;初始化循环起点
	mov ecx,4
	mov row,ecx
	mov col,4


s:
	;循环到下一方向4个方块的判断起点进行判断
	mov col,ecx
	mov row,4

	jmp s_trav

s_end:
	;在同一方向结束循环，走向下一个方向的4个方块的判断
	loop s

	ret
s_trav:
	;保存比较数
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	;同一方向的四个位置进行循环判断
	cmp row,4
	je s_merge

	cmp row,3
	je s_fore

	cmp row,2
	je s_fore

	cmp row,1
	je s_fore

	jmp s_trav
s_mov:
	;跳转到下一列4方块的判断
	dec row
	cmp row,0
	ja s_trav

	jmp s_end

s_merge:
	;判断是否为0，若为0则跳过判断
	cmp edx,0
	je s_mov

	sub ebx,4

	;判断是否跨行比较，若跨行比较则进行下一个同方向的方块的判断
	cmp ebx,400
	jae s_mov
	
	;是否与本位方块进行比较
	cmp eax,ebx
	je s_merge

	;若在合并方向上寻找到0，则继续探索是否有可合并方块
	cmp gameMat[ebx*4],0
	je s_merge

	;若在合并方向上寻找到相同数字方块，则转入合并函数
	cmp gameMat[ebx*4],edx
	je s_equ

	jmp s_mov
s_equ:
	;若判断遇到相等数字方块，则进行合并
	imul edx,2
	mov gameMat[eax*4],edx
	mov gameMat[ebx*4],0

	;对是否能进行移动标志位进行更新
    mov exchangeNum,edx
	mov edx,1
	mov changedS,edx
	mov edx,exchangeNum

	jmp s_mov

s_fore:
	;向反方向探索，若遇到0则进行移动
	cmp edx,0
	je s_mov
	mov ebx,eax
	add ebx,4

	cmp gameMat[ebx*4],0
	je s_zero

	jmp s_merge
s_zero:
	;遇到0后移动方块
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0

	;对是否能进行移动标志位进行更新
    mov exchangeNum,edx
	mov edx,1
	mov changedS,edx
	mov edx,exchangeNum

	mov eax,ebx
	add ebx,4
	;边界检测
	cmp ebx,16 
	jae s_merge

	;若前方仍有零，继续进行判断
	cmp gameMat[ebx*4],0
	je s_zero
	jmp s_merge

moveS endp


;--------------------------------------------------------------------------------------
;@Function Name :  gameEnd
;@Description   :  检查游戏是否结束，游戏结束则修改gameIsEnd=1
;@Author        :  Chris
;--------------------------------------------------------------------------------------
gameEnd proc
			
	;将ecx，edx压栈，维护调用环境
    push    ecx
    push    edx
	;给ecx赋循环次数
	mov ecx,16
	;esi：访问第esi个数据
	mov esi,0
L1:
	;压栈第esi个数据
	push gameMat[esi*4]
	;出栈赋给tmpGameMat，保存当前游戏局面
	pop tmpGameMat[esi*4]
	inc esi
	loop L1

	;调用向上移动，检测是否可以移动
	invoke moveW
			
	;同上
	mov ecx,16
	mov esi,0
	;复原游戏局面
L2:
	push tmpGameMat[esi*4]
	pop gameMat[esi*4]
	inc esi
	loop L2

	;检测是否可以向左移动
	invoke moveA

	;同上，复原游戏局面，检测是否可以向下移动
	mov ecx,16
	mov esi,0
L3:
	push tmpGameMat[esi*4]
	pop gameMat[esi*4]
	inc esi
	loop L3
	invoke moveS

	;同上，复原游戏局面，检测是否可以向右移动
	mov ecx,16
	mov esi,0
L4:
	push tmpGameMat[esi*4]
	pop gameMat[esi*4]
	inc esi
	loop L4
	invoke moveD

	;复原游戏局面
	mov ecx,16
	mov esi,0
L5:
	push tmpGameMat[esi*4]
	pop gameMat[esi*4]
	inc esi
	loop L5

	;eax清0
	xor eax,eax

	;eax=changeW+changeS+changeA+changeD
    mov eax,changedW
    add eax,changedS
    add eax,changedA
	add eax,changedD

	;若eax不等于0，则表示游戏还可以向某一方向移动，游戏不结束
    cmp eax,0
    jne end_node

	;反之游戏结束，为gameIsEnd赋值1
    mov eax,1
    mov gameIsEnd,eax
    
	;还原
end_node:
    pop edx
    pop ecx

    ret

gameEnd Endp

;--------------------------------------------------------------------------------------
;@Function Name :  num2byte
;@Param			:  number(转换的数字)
;@Description   :  将数字按位转为字符存储到数组中，如2048=‘2’‘0’‘4’‘8’
;@Author        :  Chris
;--------------------------------------------------------------------------------------
num2byte proc far C uses eax esi ecx,number:dword

	;清空寄存器，32位除法需要
	xor eax,eax
	xor edx,edx
	xor ebx,ebx
	;被除数放到eax中
	mov eax,number
	mov ecx,10
	
	;2048/10=204...8压入8
	;204/10=20...4压入4
	;20/10=2...0压入0
	;2/10=0...2压入2
	;商为0结束循环
L1:
	;ebx记录number位数
	inc ebx
	;eax/ecx，32位除法，商在eax中，余数在edx中
	idiv ecx
	;余数+‘0’=‘0’-‘9’
	add edx,30H
	;先压栈后续一起处理
	push edx
	;记得清0，32位除法被除数为edx:eax
	xor edx,edx
	;eax为商，商=0表示除尽了
	cmp eax,0
	;大于0继续循环
	jg L1

	;esi=0，表示第esi个字符
	mov esi,0
L2:
	;ebx为之前记录的number位数，每次循环减1，直到为0
	dec ebx
	;将栈中的字符出栈存到eax中
	pop eax
	;结果只为‘0’-‘9’，只在8位寄存器中，无所谓了
	mov byte ptr Data[esi],al
	inc esi
	cmp ebx,0
	jg L2
	
	;循环结束，末尾赋0表示结束
	mov Data[esi],0
	ret

num2byte endp

;--------------------------------------------------------------------------------------
;@Function Name :  DrawGame
;@Param			:  hWnd（窗口句柄）
;@Description   :  绘制游戏界面，说明框，和分数框
;@Author        :  Chris
;--------------------------------------------------------------------------------------
DrawGame proc far C uses eax esi ecx edx,hWnd
	
	;定义字体
	local @hFont:HFONT
	local @logfont:LOGFONT
	invoke RtlZeroMemory,addr @logfont,sizeof @logfont
	mov @logfont.lfCharSet,GB2312_CHARSET
	mov @logfont.lfHeight,-40
	mov @logfont.lfWeight,FW_BOLD
	invoke CreateFontIndirect,addr @logfont
	mov @hFont,eax

	;i=0，无条件跳转L2
	mov i,0
	jmp L2
	
	;i++
L1:
	mov eax,i
	add eax,1
	mov i,eax

	;i<4?L3：L7
L2:
	cmp i,4
	jge L7
	
	;j=0，jmp L5
L3:
	mov j,0
	jmp L5
L4:
	;j++
	mov eax,j
	add eax,1
	mov j,eax

	;j<4?L6:L1
L5:
	cmp j,4
	jge L1

	;绘制100*100的矩形框
L6:
	;eax=i*100+140，绘制的x坐标，140为起始坐标
	imul eax,i,100
	add eax,140
	;ecx=j*100+100，绘制的y坐标，100为起始坐标
	imul ecx,j,100
	add ecx,100

	;edx=i*4+j，表示第[i][j]个方块
	imul edx,i,4
	add edx,j
	;gameMat[i][j]的值转为字符数组存到Data中，dword：*4
	invoke num2byte,dword ptr gameMat[edx*4]
	;如果为0
	;eax=i*100+140，绘制的x坐标，140为起始坐标
	imul eax,i,100
	add eax,140
	;ecx=j*100+100，绘制的y坐标，100为起始坐标
	imul ecx,j,100
	add ecx,100
	.IF Data[0] =='0'
		;创建静态控件，居中有边框
		invoke CreateWindowEx,NULL,offset static,offset EmptyText,\
		WS_CHILD or WS_VISIBLE or SS_CENTER or WS_BORDER or SS_CENTERIMAGE,ecx,eax,100,100,\  
		hWnd,edx,hInstance,NULL  ;句柄为edx
	.else
		invoke CreateWindowEx,NULL,offset static,offset Data,\
		WS_CHILD or WS_VISIBLE or SS_CENTER or WS_BORDER or SS_CENTERIMAGE,ecx,eax,100,100,\ 
		hWnd,edx,hInstance,NULL  ;句柄为edx
	.endif
	;edx=i*4+j，表示第[i][j]个方块
	imul edx,i,4
	add edx,j
	;存储窗口句柄，句柄返回值在eax中
	mov hGame[edx*4],eax
	;调用SendMessage改变字体
	invoke SendMessage,eax,WM_SETFONT,@hFont,1
	
	jmp L4
L7:
	;绘制游戏说明部分
	;创建文本框，但设为Disabeled防止玩家更改
	invoke CreateWindowEx,NULL,offset edit,offset szText1,\
	WS_CHILD or WS_VISIBLE OR WS_DISABLED,100,60,120,15,\
	hWnd,16,hInstance,NULL
	MOV hGame[64],eax
	invoke CreateWindowEx,NULL,offset edit,offset szText2,\
	WS_CHILD or WS_VISIBLE OR WS_DISABLED,100,75,400,15,\
	hWnd,17,hInstance,NULL
	mov hGame[68],eax
	invoke CreateWindowEx,NULL,offset edit,offset szText5,\
	WS_CHILD or WS_VISIBLE OR WS_DISABLED,100,90,400,15,\
	hWnd,18,hInstance,NULL
	mov hGame[72],eax
	invoke CreateWindowEx,NULL,offset edit,offset szText3,\
	WS_CHILD or WS_VISIBLE OR WS_DISABLED,100,105,400,15,\
	hWnd,19,hInstance,NULL
	mov hGame[76],eax
	invoke CreateWindowEx,NULL,offset edit,offset szText4,\
	WS_CHILD or WS_VISIBLE OR WS_DISABLED,100,120,400,15,\
	hWnd,20,hInstance,NULL
	mov hGame[80],eax

	;绘制分数框
	invoke num2byte,score
	invoke CreateWindowEx,NULL,offset edit,offset Data,\
	WS_CHILD or WS_VISIBLE OR WS_DISABLED,420,38,80,15,\ 
	hWnd,21,hInstance,NULL
	mov hGame[84],eax

	xor eax,eax
	ret

DrawGame endp

;--------------------------------------------------------------------------------------
;@Function Name :  UpdataGame
;@Param			:  hWnd（窗口句柄）
;@Description   :  由于gameMat值改变，需要改变界面的值
;@Author        :  Chris
;--------------------------------------------------------------------------------------
UpdataGame proc far C uses eax esi ecx edx,hWnd
	
	;i=0，无条件跳转L2
	mov i,0
	jmp L2

	;i++
L1:
	mov eax,i
	add eax,1
	mov i,eax

	;i<4?L3：L7
L2:
	cmp i,4
	jge L7
	
	;j=0，jmp L5
L3:
	mov j,0
	jmp L5
L4:
	;j++
	mov eax,j
	add eax,1
	mov j,eax

	;j<4?L6:L1
L5:
	cmp j,4
	jge L1
L6:
	;edx=i*4+j表示[i][j]块方块
	imul edx,i,4
	add edx,j
	;转换值
	invoke num2byte,dword ptr gameMat[edx*4]
	imul edx,i,4
	add edx,j
	;设置控件中的值
	.if Data[0] =='0'
		INVOKE SetWindowText,hGame[edx*4],offset EmptyText
	.else
		INVOKE SetWindowText,hGame[edx*4],offset Data
	.endif

	JMP L4
L7:
	;设置分数的值
	invoke num2byte,score
	INVOKE SetWindowText,hGame[84],offset Data
	xor eax,eax
	ret

UpdataGame endp
;--------------------------------------------------------------------------------------
;@Function Name :  ReStartGame
;@Param			:  
;@Description   :  重新开始游戏
;@Author        :  Chris
;--------------------------------------------------------------------------------------
ReStartGame proc far C uses eax esi ecx edx
	
	;初始化gameMat为0
	mov ecx,16
	mov esi,0
L1:
	mov gameMat[esi*4],0
	inc esi
	loop L1

	;初始化各个值
	mov gameIsEnd,0
	mov gameIsWin,0
	mov gameContinue,0
	mov score,0

	;gameMat随机生成两个值
	INVOKE random32,dat,max
	INVOKE random32,dat,max
	ret

ReStartGame endp
;--------------------------------------------------------------------------------------
;@Function Name :  _ProcWinMain
;@Param			:  
;@Description   :  窗口回调函数，处理窗口消息
;@Author        :  Chris
;--------------------------------------------------------------------------------------
_ProcWinMain proc uses ebx edi esi,hWnd,uMsg,wParam,lParam  ;窗口过程
	local @stPs:PAINTSTRUCT
	local @stRect:RECT
	local @hDc
	LOCAL @oldPen:HPEN
	local @hBm
	
	mov eax,uMsg  ;uMsg是消息类型，如下面的WM_PAINT,WM_CREATE

	.if eax==WM_PAINT  ;如果想自己绘制客户区，在这里些代码，即第一次打开窗口会显示什么信息
		

		invoke BeginPaint,hWnd,addr @stPs
		
		;加载位图
		;首先获取窗口DC
		invoke GetDC, hWnd
		mov @hDc,eax
		
		;创建兼容窗口DC的缓存dc
		invoke CreateCompatibleDC,@hDc
		mov hdcIDB_BITMAP1,eax
		
		invoke CreateCompatibleDC,@hDc
		mov hdcIDB_BITMAP2,eax
		
		;创建位图缓存
		invoke CreateCompatibleBitmap, @hDc,150,80
		mov hbmIDB_BITMAP1,eax

		invoke CreateCompatibleBitmap, @hDc,90,60
		mov hbmIDB_BITMAP2,eax

		;将hbm与hdc绑定
		invoke SelectObject,hdcIDB_BITMAP1,hbmIDB_BITMAP1
		
		;载入位图到位图句柄中
		invoke LoadBitmap,hInstance,BITMAP1
		mov @hBm,eax
		;创建以位图为图案的画刷
		invoke CreatePatternBrush,@hBm
		push eax
		;以画刷填充缓存DC
		invoke SelectObject,hdcIDB_BITMAP1,eax
		;按照PATCOPY的方式
		invoke PatBlt,hdcIDB_BITMAP1,0,0,150,80,PATCOPY
		pop eax
		;删除画刷
		invoke DeleteObject,eax
		;在主窗口DC上绘制位图dc
		invoke BitBlt,@hDc,90,0,150,80,hdcIDB_BITMAP1,0,0,SRCCOPY
		
		;同上
		invoke LoadBitmap,hInstance,BITMAP2
		mov @hBm,eax
		invoke SelectObject,hdcIDB_BITMAP2,hbmIDB_BITMAP2
		invoke CreatePatternBrush,@hBm
		push eax
		invoke SelectObject,hdcIDB_BITMAP2,eax
		invoke PatBlt,hdcIDB_BITMAP2,0,0,90,60,PATCOPY
		pop eax
		invoke DeleteObject,eax
		invoke BitBlt,@hDc,310,15,90,60,hdcIDB_BITMAP2,0,0,SRCCOPY
		invoke ReleaseDC,hWnd,@hDc

		invoke EndPaint,hWnd,addr @stPs
	
	.elseif eax==WM_CLOSE  ;窗口关闭消息
		invoke DestroyWindow,hWinMain
		invoke PostQuitMessage,NULL

	.elseif eax==WM_CREATE  ;创建窗口
		;绘制界面
		invoke DrawGame,hWnd

	.elseif eax== WM_CHAR	;WM_CHAR为按下键盘消息，按下的键的值存在wParam中
		MOV edx,wParam
		;如果为W则向上移动
		.if edx == 'W' || edx == 'w'
			
			invoke moveW
			;如果可以向上移动，在随机位置产生一个2
			.IF changedW == 1
				invoke random32,dat,max
			.endif
			;计算分数
			INVOKE getscore
			;更新界面
			INVOKE UpdataGame,hWnd
		;同上
		.elseif edx == 'S' || edx == 's'
			invoke moveS
			
			.IF changedS == 1
				invoke random32,dat,max
			.endif
			INVOKE getscore
			INVOKE UpdataGame,hWnd
		;同上
		.elseif edx =='A' || edx == 'a'
			
			invoke moveA
			
			.IF changedA == 1
				invoke random32,dat,max
			.endif
			INVOKE getscore
			INVOKE UpdataGame,hWnd
		;同上
		.elseif edx == 'D' || edx == 'd'

			invoke moveD
			
			.IF changedD == 1
				invoke random32,dat,max
			.endif
			INVOKE getscore
			INVOKE UpdataGame,hWnd
		.endif
		
		;如果游戏还未获胜，gameContinue=0，如果游戏已经获胜过了，且玩家选择继续玩，则gameContinue=1，将不会再弹出获胜消息
		.if gameContinue == 0
			;如果gameIsWin==1，游戏获胜，弹出游戏获胜消息
			.if gameIsWin == 1
				invoke gameWin
			.endif
		.endif
		
		;判断游戏是否结束
		invoke gameEnd
		;如果游戏结束，绘制失败弹窗
		.if gameIsEnd == 1
			invoke MessageBox,hWinMain,offset szText7,offset szText6,MB_OK
			;重新开始游戏
			.if eax == IDOK
				invoke ReStartGame
				INVOKE UpdataGame,hWnd
			.endif
		.endif

	.else  ;否则按默认处理方法处理消息
		invoke DefWindowProc,hWnd,uMsg,wParam,lParam
		ret
	.endif

	xor eax,eax
	ret
_ProcWinMain endp

;--------------------------------------------------------------------------------------
;@Function Name :  _WinMain
;@Param			:  
;@Description   :  窗口程序，注册主窗口
;@Author        :  Chris
;--------------------------------------------------------------------------------------

_WinMain proc  ;窗口程序
	local @stWndClass:WNDCLASSEX  ;定义了一个结构变量，它的类型是WNDCLASSEX，一个窗口类定义了窗口的一些主要属性，图标，光标，背景色等，这些参数不是单个传递，而是封装在WNDCLASSEX中传递的。
	local @stMsg:MSG	;还定义了stMsg，类型是MSG，用来作消息传递的	
	local @redbru:HBRUSH
	invoke GetModuleHandle,NULL  ;得到应用程序的句柄，把该句柄的值放在hInstance中，句柄是什么？简单点理解就是某个事物的标识，有文件句柄，窗口句柄，可以通过句柄找到对应的事物
	mov hInstance,eax
	invoke RtlZeroMemory,addr @stWndClass,sizeof @stWndClass  ;将stWndClass初始化全0

	;注册窗口类
	invoke LoadCursor,0,IDC_ARROW
	mov @stWndClass.hCursor,eax					;---------------------------------------
	push hInstance							;
	pop @stWndClass.hInstance					;
	mov @stWndClass.cbSize,sizeof WNDCLASSEX			;这部分是初始化stWndClass结构中各字段的值，即窗口的各种属性
	mov @stWndClass.style,CS_HREDRAW or CS_VREDRAW
	mov @stWndClass.lpfnWndProc,offset _ProcWinMain			;
	;上面这条语句其实就是指定了该窗口程序的窗口过程是_ProcWinMain	;
	mov @stWndClass.hbrBackground,COLOR_WINDOW+1			;
	mov @stWndClass.lpszClassName,offset szClassName		;---------------------------------------
	invoke RegisterClassEx,addr @stWndClass  ;注册窗口类，注册前先填写参数WNDCLASSEX结构

	invoke CreateWindowEx,WS_EX_CLIENTEDGE,\ 
			offset szClassName,offset szCaptionMain,\ 
			WS_OVERLAPPEDWINDOW,400,200,600,600,\
			NULL,NULL,hInstance,NULL
	mov hWinMain,eax  ;建立窗口后句柄会放在eax中，现在把句柄放在hWinMain中。
	invoke ShowWindow,hWinMain,SW_SHOWNORMAL  ;显示窗口，注意到这个函数传递的参数是窗口的句柄，正如前面所说的，通过句柄可以找到它所标识的事物
	invoke UpdateWindow,hWinMain  ;刷新窗口客户区

	.while TRUE  ;进入无限的消息获取和处理的循环
		invoke GetMessage,addr @stMsg,NULL,0,0  ;从消息队列中取出第一个消息，放在stMsg结构中
		.break .if eax==0  ;如果是退出消息，eax将会置成0，退出循环
		invoke TranslateMessage,addr @stMsg  ;这是把基于键盘扫描码的按键信息转换成对应的ASCII码，如果消息不是通过键盘输入的，这步将跳过
		invoke DispatchMessage,addr @stMsg  ;这条语句的作用是找到该窗口程序的窗口过程，通过该窗口过程来处理消息
	.endw
	ret
_WinMain endp

main proc

	;INVOKE random32,dat,max
	;INVOKE random32,dat,max
	;invoke getscore
	invoke ReStartGame
	call _WinMain  ;主程序就调用了窗口程序和结束程序两个函数
	invoke ExitProcess,NULL
	ret
main endp
end main
