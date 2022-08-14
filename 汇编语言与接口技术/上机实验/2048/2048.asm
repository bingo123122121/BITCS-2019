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

; 窗口尺寸
WINDOW_WIDTH equ 600
WINDOW_HEIGHT equ 850
WINDOW_BIAS equ 10
BOARD_EDGE equ 502
CELL_EDGE equ 108
PATH_WIDTH equ 14
SCORE_WIDTH equ 138
SCORE_HEIGHT equ 65
SCORE_PATH equ 5
INTRO_WIDTH equ 502
INTRO_HEIGHT equ 110

; 字体、笔刷格式
hFontDigit1 dd ?
hFontDigit3 dd ?
hFontDigit4 dd ?
hFont0 dd ?
hFont1 dd ?
hFont2 dd ?
hFont3 dd ?
hFont4 dd ?

hBrush_d2 dd ?
hBrush_d4 dd ?
hBrush_d8 dd ?
hBrush_d16 dd ?
hBrush_d32 dd ?
hBrush_d64 dd ?
hBrush_d128 dd ?
hBrush_d256 dd ?
hBrush_d512 dd ?
hBrush_d1024 dd ?
hBrush_d2048 dd ?

; 输出格式
szFormat byte '%d', 0

; 显示提示
szFontName byte 'Clear Sans', 0
TitleMessage byte '2048', 0
CurrentScoreMessage byte 'CURRENT MAX', 0
BestScoreMessage byte 'HISTORY MAX', 0
hColor_d1 dd 0656e77h
hColor_d2 dd 0f2f6f9h

; 程序句柄
hInstance dd ?
hWinMain dd ?

; 游戏数据保存
hGame dd 25 dup(?)

seed dd 0
score dd 0
bestScore dd 0

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

moveDelta dd 16 DUP(0)

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
szText2 byte "Use WASD or Arrow to move the tiles along the grid",0
szText5 byte "If two tiles of the same number touch, they'll merge",0
szText3 byte "Try to get a 2048 tile, or go as high as you can!",0
szText4 byte "The game will end if every tile gets filled and you can't merge any tiles",0
szText7 byte "Game Is Over",0
szText6 byte "2048",0
szWinText byte 2000 dup("Congratulations!",0ah,"You have won this game!",0ah,"Thank you for playing!",0ah,0)
szText12 byte "Now you can choose 'YES' to continue,or 'No' to quit",0
BITMAP1 EQU 101
BITMAP2 EQU 104
max EQU 16 ;4*4矩阵

.code

; 游戏胜利消息弹窗，玩家继续玩则置gameContinue为1
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

; 线性同余法，msvcrt的参数，产生随机数，随后形成矩阵
random32       proc     
               local @a, @c, @randNum, @randPos

			    mov @a, 0343FDH
				mov @c, 269EC3H
				;保护寄存器
				pusha
				;x_0
				mov eax, seed 
				
				;a*x_n+c mod max
    lcg:		mul @a
				add eax, @c
				xor edx,edx
				mov ebx, max
				div ebx
				mov eax,edx
				mov	@randPos,eax
				mul @a
				add eax, @c
				xor edx,edx
				mov ebx, max
				div ebx
				mov eax,edx
				; %2
				xor edx,edx
				mov ebx, 2
				div ebx
				.if edx
					mov @randNum,4
				.else 
					mov @randNum,2
				.endif
    loop_lcg:
				;随机位置生成2或4，有冲突继续循环
				mov		eax, @randPos
                cmp     gameMat[eax*4],0
                je      inital_mat
				loop        lcg
    inital_mat:
                mov     eax,@randPos
				mov		edx,@randNum
                mov     gameMat[eax*4],edx

				;保存seed，下一次从这个数开始继续往下生成
				mov		seed, eax
                popa
                ret        
    random32    Endp

; 根据矩阵当前状态计算分数
getScore proc
    push ecx
    push edx
	push esi
	push edi

    xor ecx, ecx
    xor eax,eax
	mov score, 0
	;遍历矩阵
	mov ecx, 16  ; 总共有16个方格
	mov edi, 0  ; 初始时，最大值设置为0
	.while ecx > 0
		dec ecx
		mov esi, gameMat[ecx * (sizeof dword)]
		.if esi > edi
			mov edi, esi
		.endif
	.endw
	mov score, edi
	.if score == 2048
		mov gameIsWin, 1
	.endif
    
	pop edi
	pop esi
	pop edx
    pop ecx
    ret
getScore        Endp


; 方块在四个方向上的移动与合成
; 上移
moveW proc 
	
	MOV changedW,0
	
	mov col,4

.while col
	;外层循环
	mov row,1
	.while row < 5
	;内层循环
	;保存比较数到edx，当前位置到ebx
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	.if row == 1
		w_merge:
		;判断是否为0，若为0则跳过判断
		.if edx == 0
		inc row
		.continue
		.endif

		add ebx,4
		;判断是否跨行比较
		.if ebx >= 16
		inc row
		.continue
		.endif

		;是否与本位方块进行比较
		.if eax == ebx
			jmp w_merge
		.endif

		;若在合并方向上寻找到0，则继续探索是否有可合并方块
		.if gameMat[ebx*4] == 0
			jmp w_merge
		.endif
		;若在合并方向上寻找到相同数字方块，则转入合并函数
		.if gameMat[ebx*4] == edx
			w_equ:
			;若判断遇到相等数字方块，则进行合并
			imul edx,2
			mov gameMat[eax*4],edx
			mov gameMat[ebx*4],0

			;对exchangeNum标志位进行更新
			mov exchangeNum,edx
			mov edx,1
			mov changedW,edx
			mov edx,exchangeNum

			inc row
			.continue
		.endif

	.else
		;向反方向探索，若遇到0则进行移动
		.if edx == 0
		inc row
		.continue
		.endif
		mov ebx,eax
		sub ebx,4
	
		cmp gameMat[ebx*4],0
		je w_zero

		jmp w_merge

	.endif
	inc row
	.endw
	dec col
.endw
	ret

w_zero:
	;遇到0后移动方块
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0
	
	;对exchangeNum标志位进行更新
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

	ret
moveW endp

; 右移
moveD proc 
	mov changedD,0
	mov row,4

.while row 
	mov col,4
.while col
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax
	.if col == 4
	d_merge:
		.if edx == 0
		dec col
		.continue
		.endif

		dec ebx
		mov overEdge,eax
		mov eax,row
		dec eax
		imul eax,4
		dec eax
		.if eax==ebx
		dec col
		.continue
		.endif
		mov eax,overEdge

		cmp eax,ebx
		je d_merge

		cmp gameMat[ebx*4],0
		je d_merge

		.if gameMat[ebx*4]==edx
			imul edx,2
			mov gameMat[eax*4],edx
			mov gameMat[ebx*4],0
			mov exchangeNum,edx
			mov edx,1
			mov changedD,edx
			mov edx,exchangeNum

			dec col
			.continue
		.endif
	.else
			.if edx==0
			dec col
			.continue
			.endif
			mov ebx,eax
			inc ebx

			cmp gameMat[ebx*4],0
			je d_zero

			jmp d_merge
	.endif
	dec col
.endw
	dec row
.endw
	ret
d_zero:
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0
    mov exchangeNum,edx
	mov edx,1
	mov changedD,edx
	mov edx,exchangeNum
    
	mov eax,ebx
	inc ebx

	mov overEdge,ebx
	mov ebx,row
	imul ebx,4
	cmp overEdge,ebx
	je d_merge
	mov ebx,overEdge

	cmp gameMat[ebx*4],0
	je d_zero

	jmp d_merge

moveD endp

; 左移
moveA proc 
	mov changedA,0
	mov row,4
.while row
	mov col,1
.while col < 5
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax
	.if col == 1
		a_merge:
		.if edx==0
		inc col
		.continue
		.endif
		inc ebx
		mov overEdge,eax
		mov eax,row
		imul eax,4
		.if eax==ebx
		inc col
		.continue
		.endif
		mov eax,overEdge

		cmp eax,ebx
		je a_merge

		cmp gameMat[ebx*4],0
		je a_merge
		.if gameMat[ebx*4]==edx
			imul edx,2
			mov gameMat[eax*4],edx
			mov gameMat[ebx*4],0

			mov exchangeNum,edx
			mov edx,1
			mov changedA,edx
			mov edx,exchangeNum
    
			inc col
			.continue
		.endif
	.else
		.if edx==0
		inc col
		.continue
		.endif
		mov ebx,eax
		dec ebx
	
		cmp gameMat[ebx*4],0
		je a_zero

		jmp a_merge
	.endif
	inc col
	.endw
	dec row
	.endw
	ret

a_zero:
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0

	mov exchangeNum,edx
	mov edx,1
	mov changedA,edx
	mov edx,exchangeNum

	mov eax,ebx
	dec ebx

	mov overEdge,ebx
	mov ebx,row
	dec ebx
	imul ebx,4
	dec ebx
	cmp overEdge,ebx
	je a_merge
	mov ebx,overEdge

	cmp gameMat[ebx*4],0
	je a_zero

	jmp a_merge

moveA endp

; 下移
moveS proc 
	mov changedS,0
	mov col,4
.while col
	mov row,4
.while row
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	.if row==4
		s_merge:
		.if edx==0
		dec row
		.continue
		.endif

		sub ebx,4
		.if ebx>=400
		dec row
		.continue
		.endif
	
		cmp eax,ebx
		je s_merge

		cmp gameMat[ebx*4],0
		je s_merge

		.if gameMat[ebx*4]==edx
			imul edx,2
			mov gameMat[eax*4],edx
			mov gameMat[ebx*4],0

			mov exchangeNum,edx
			mov edx,1
			mov changedS,edx
			mov edx,exchangeNum

			dec row
			.continue
		.endif
	.else
		.if edx==0
		dec row
		.continue
		.endif
		mov ebx,eax
		add ebx,4

		cmp gameMat[ebx*4],0
		je s_zero

		jmp s_merge
	.endif
	dec row
	.endw
	dec col
	.endw
	ret
s_zero:
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0

    mov exchangeNum,edx
	mov edx,1
	mov changedS,edx
	mov edx,exchangeNum

	mov eax,ebx
	add ebx,4
	cmp ebx,16 
	jae s_merge

	cmp gameMat[ebx*4],0
	je s_zero
	jmp s_merge

moveS endp


; 检查游戏是否结束，即是否有能移动的方向，游戏结束则修改gameIsEnd=1
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

; 获取数字框位置
_GetPosition proc, _StartX, _StartY, _dX, _dY
    
    local @RetX, @RetY

    mov eax, _StartX
    mov @RetX, eax
    add @RetX, PATH_WIDTH
    mov eax, _StartY
    mov @RetY, eax
    add @RetY, PATH_WIDTH

    .while _dX > 0
        add @RetX, CELL_EDGE
        add @RetX, PATH_WIDTH
        dec _dX
    .endw
    .while _dY > 0
        add @RetY, CELL_EDGE
        add @RetY, PATH_WIDTH
        dec _dY
    .endw

    mov eax, @RetX
    mov ebx, @RetY
    ret
_GetPosition endp

; 绘制数字
_DrawDigit proc, _hDc, _cellStartX, _cellStartY, _num

    local @cellEndX, @cellEndY
    local @digitStartX, @digitStartY
    local @szBuffer[10]: byte

    .if _num == 0
        ret
    .endif

    pushad
    
    mov eax, _cellStartX
    mov @cellEndX, eax
    add @cellEndX, CELL_EDGE
    mov eax, _cellStartY
    mov @cellEndY, eax
    add @cellEndY, CELL_EDGE
    
    ; 设置数字背景颜色
    .if _num == 2
        invoke SelectObject, _hDc, hBrush_d2
    .elseif _num == 4
        invoke SelectObject, _hDc, hBrush_d4
    .elseif _num == 8
        invoke SelectObject, _hDc, hBrush_d8
    .elseif _num == 16
        invoke SelectObject, _hDc, hBrush_d16
    .elseif _num == 32
        invoke SelectObject, _hDc, hBrush_d32
    .elseif _num == 64
        invoke SelectObject, _hDc, hBrush_d64
    .elseif _num == 128
        invoke SelectObject, _hDc, hBrush_d128
    .elseif _num == 256
        invoke SelectObject, _hDc, hBrush_d256
    .elseif _num == 512
        invoke SelectObject, _hDc, hBrush_d512
    .elseif _num == 1024
        invoke SelectObject, _hDc, hBrush_d1024
    .else
        invoke SelectObject, _hDc, hBrush_d2048
    .endif
    invoke RoundRect, _hDc, _cellStartX, _cellStartY, @cellEndX, @cellEndY, 3, 3
            
    ; 设置数字字体颜色
    .if _num < 8
        invoke SetTextColor, _hDc, hColor_d1
    .else
        invoke SetTextColor, _hDc, hColor_d2
    .endif

    mov eax, _cellStartX
    mov @digitStartX, eax
    mov eax, _cellStartY
    mov @digitStartY, eax

    ; 设置数字位置
    .if _num < 10
        invoke SelectObject, _hDc, hFontDigit1
        add @digitStartX, 37
        add @digitStartY, 18
    .elseif _num < 100
        invoke SelectObject, _hDc, hFontDigit1
        add @digitStartX, 21
        add @digitStartY, 18
    .elseif _num < 1000
        invoke SelectObject, _hDc, hFontDigit3
        add @digitStartX, 13
        add @digitStartY, 24
    .else
        invoke SelectObject, _hDc, hFontDigit4
        add @digitStartX, 12
        add @digitStartY, 31
    .endif

    invoke wsprintf, addr @szBuffer, addr szFormat, _num
    invoke TextOut, _hDc, @digitStartX, @digitStartY, addr @szBuffer, eax

    popad
    ret
_DrawDigit endp

; 根据当前分数更新最高分
updateBestScore proc far C
	
	pushad

	mov eax, score
	.if bestScore < eax
		mov bestScore, eax
	.endif

	popad

	ret

updateBestScore endp

; 绘制游戏界面，包括提示框、分数框、4*4方格
DrawGame proc far C, hWnd, hDc
	local @boardStartX, @boardStartY, @boardEndX, @boardEndY
    local @cellStartX, @cellStartY, @cellEndX, @cellEndY
	local @currentScoreStartX, @currentScoreStartY, @currentScoreEndX, @currentScoreEndY
    local @bestScoreStartX, @bestScoreStartY, @bestScoreEndX, @bestScoreEndY
	local @introStartX, @introStartY, @introEndX, @introEndY
    local @bghDc, @digit
    local @x, @y, @len, @bias
    local @szBuffer[10]: byte
    local _hDc, _cptBmp
    local @deletedummy
    
    pushad

    invoke CreateCompatibleDC, hDc
    mov _hDc, eax
    invoke CreateCompatibleBitmap, hDc, WINDOW_WIDTH, WINDOW_HEIGHT
    mov _cptBmp, eax
    invoke SelectObject, _hDc, _cptBmp

    invoke CreateSolidBrush, 0eff8fah
    ;mov @bias, eax
    mov @deletedummy, eax
    invoke SelectObject, _hDc, eax
    invoke Rectangle, _hDc, -5, -5, WINDOW_WIDTH, WINDOW_HEIGHT
    invoke DeleteObject, @deletedummy

	; 4*4数字位置
    mov @boardStartX, (WINDOW_WIDTH - BOARD_EDGE) / 2 - WINDOW_BIAS
    mov @boardStartY, 225
    mov @boardEndX, (WINDOW_WIDTH + BOARD_EDGE) / 2 - WINDOW_BIAS
    mov @boardEndY, BOARD_EDGE + 225

	; 历史最高分背景位置
	mov eax, @boardEndX
    mov @bestScoreEndX, eax
    sub eax, SCORE_WIDTH
    mov @bestScoreStartX, eax
    mov @bestScoreStartY, 65
    mov @bestScoreEndY, SCORE_HEIGHT + 65

	; 当前最高分背景位置
	mov eax, @bestScoreStartX
	sub eax, 10
	mov @currentScoreEndX, eax
	sub eax, SCORE_WIDTH
	mov @currentScoreStartX, eax
	mov eax, @bestScoreStartY
	mov @currentScoreStartY, eax
	mov eax, @bestScoreEndY
	mov @currentScoreEndY, eax

	; 简介位置
	mov eax, @boardEndX
    mov @introEndX, eax
    sub eax, INTRO_WIDTH
    mov @introStartX, eax
    mov @introStartY, 152
    mov @introEndY, INTRO_HEIGHT + 102


    invoke GetStockObject, NULL_PEN
    invoke SelectObject, _hDc, eax
    invoke CreateSolidBrush, 0a0adbbh
    mov @deletedummy, eax
    invoke SelectObject, _hDc, eax
    invoke RoundRect, _hDc, @boardStartX, @boardStartY, @boardEndX, @boardEndY, 6, 6
    
    invoke SetBkMode, _hDc, TRANSPARENT

	    
    invoke SelectObject, _hDc, hFontDigit1
    invoke SetTextColor, _hDc, 0656e77h
    invoke TextOut, _hDc, @boardStartX, 30, addr TitleMessage, lengthof TitleMessage

	invoke RoundRect, _hDc, @currentScoreStartX, @currentScoreStartY, @currentScoreEndX, @currentScoreEndY, 3, 3
    invoke RoundRect, _hDc, @bestScoreStartX, @bestScoreStartY, @bestScoreEndX, @bestScoreEndY, 3, 3

    invoke DeleteObject, @deletedummy
    
	; 生成当前分数提示
	invoke SelectObject, _hDc, hFont1
    invoke SetTextColor, _hDc, 0dae4eeh
    mov eax, @currentScoreStartX
    mov ebx, @currentScoreStartY
    add eax, 20
    add ebx, 6
    invoke TextOut, _hDc, eax, ebx, addr CurrentScoreMessage, lengthof CurrentScoreMessage
	
	; 绘制当前分数区域
	invoke SelectObject, _hDc, hFont4
    invoke SetTextColor, _hDc, 0ffffffh
	invoke getScore
    invoke wsprintf, addr @szBuffer, addr szFormat, score
	mov @len, eax
    mov edx, 0
    mov ebx, 15
    mul ebx
    sub eax, SCORE_WIDTH
    neg eax
    shr eax, 1
    add eax, @currentScoreStartX
    add eax, 1
    mov @x, eax
    mov @y, 90
    invoke TextOut, _hDc, @x, @y, addr @szBuffer, @len

	; 生成历史最高数提示
	invoke SelectObject, _hDc, hFont1
    invoke SetTextColor, _hDc, 0dae4eeh
    mov eax, @bestScoreStartX
    mov ebx, @bestScoreStartY
    add eax, 18
    add ebx, 6
    invoke TextOut, _hDc, eax, ebx, addr BestScoreMessage, lengthof BestScoreMessage
	
	; 绘制历史最高分区域
	invoke SelectObject, _hDc, hFont4
    invoke SetTextColor, _hDc, 0ffffffh
	invoke updateBestScore
    invoke wsprintf, addr @szBuffer, addr szFormat, bestScore
	mov @len, eax
    mov edx, 0
    mov ebx, 15
    mul ebx
    sub eax, SCORE_WIDTH
    neg eax
    shr eax, 1
    add eax, @bestScoreStartX
    add eax, 1
    mov @x, eax
    mov @y, 90
    invoke TextOut, _hDc, @x, @y, addr @szBuffer, @len


	; 绘制提示信息
	; 绘制背景框
	invoke CreateSolidBrush, 0f1f2e0h
    mov @deletedummy, eax
    invoke SelectObject, _hDc, eax
    invoke RoundRect, _hDc, @introStartX, @introStartY, @introEndX, @introEndY, 6, 6
    invoke DeleteObject, @deletedummy
    invoke SelectObject, _hDc, hFont3
    invoke SetTextColor, _hDc, 0cfdee9h
    mov eax, @introStartX
    mov ebx, @introStartY

	; 绘制描述信息
	invoke SelectObject, _hDc, hFont3
    invoke SetTextColor, _hDc, 0656e77h
	mov eax, @introStartY
	add eax, 2
    invoke TextOut, _hDc, @introStartX, eax, addr szText1, lengthof szText1
	mov eax, @introStartY
	add eax, 25
    invoke TextOut, _hDc, @introStartX, eax, addr szText2, lengthof szText2


    ; 生成数字的浅色背景框
    invoke CreateSolidBrush, 0b4c1cdh
    mov @bghDc, eax
    
    mov eax, @boardStartY
    add eax, PATH_WIDTH
    mov @cellStartY, eax
    add eax, CELL_EDGE
    mov @cellEndY, eax

    mov ecx, 0
    .while ecx < 4
        mov eax, @boardStartX
        add eax, PATH_WIDTH
        mov @cellStartX, eax
        add eax, CELL_EDGE
        mov @cellEndX, eax

        push ecx
        mov ecx, 0
        .while ecx < 4
            push ecx
            invoke SelectObject, _hDc, @bghDc
            invoke RoundRect, _hDc, @cellStartX, @cellStartY, @cellEndX, @cellEndY, 3, 3
            pop ecx
            add @cellStartX, CELL_EDGE + PATH_WIDTH
            add @cellEndX, CELL_EDGE + PATH_WIDTH
            inc ecx
        .endw
        pop ecx
        inc ecx
        add @cellStartY, CELL_EDGE + PATH_WIDTH
        add @cellEndY, CELL_EDGE + PATH_WIDTH
    .endw

	mov esi, offset gameMat
    mov edi, offset moveDelta

	; 生成静态数字
	mov @y, 0
        .while @y < 4
            mov @x, 0
            .while @x < 4
                mov edi, esi
                mov eax, @y
                shl eax, 4
                add edi, eax
                mov eax, @x
                shl eax, 2
                add edi, eax

                invoke _GetPosition, @boardStartX, @boardStartY, @x, @y
                invoke _DrawDigit, _hDc, eax, ebx, DWORD ptr [edi]

                inc @x
            .endw
            inc @y
        .endw
    invoke BitBlt, hDc, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, _hDc, 0, 0, SRCCOPY
    
    invoke DeleteObject, @bghDc
    invoke DeleteObject, _cptBmp
    invoke DeleteDC, _hDc
	.if gameContinue == 0
		;如果gameIsWin==1，游戏获胜，弹出游戏获胜消息
		.if gameIsWin == 1
			invoke gameWin
		.endif
	.endif
    popad
    ret

DrawGame endp


; 初始化游戏参数
initFmt proc far C

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

	;生成随机数种子
	INVOKE GetTickCount
	mov seed, eax

	;gameMat随机生成两个值
	INVOKE random32
	INVOKE random32
	invoke CreateFont, 105, 48, 0, 0, FW_BOLD, FALSE, FALSE, FALSE, DEFAULT_CHARSET, OUT_CHARACTER_PRECIS, CLIP_CHARACTER_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH or FF_DONTCARE, addr szFontName
    mov hFont0, eax
    invoke CreateFont, 20, 8, 0, 0, FW_BOLD, FALSE, FALSE, FALSE, DEFAULT_CHARSET, OUT_CHARACTER_PRECIS, CLIP_CHARACTER_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH or FF_DONTCARE, addr szFontName
    mov hFont1, eax
    invoke CreateFont, 26, 11, 0, 0, FW_NORMAL, FALSE, FALSE, FALSE, DEFAULT_CHARSET, OUT_CHARACTER_PRECIS, CLIP_CHARACTER_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH or FF_DONTCARE, addr szFontName
    mov hFont2, eax
    invoke CreateFont, 20, 9, 0, 0, FW_BLACK, FALSE, FALSE, FALSE, DEFAULT_CHARSET, OUT_CHARACTER_PRECIS, CLIP_CHARACTER_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH or FF_DONTCARE, addr szFontName
    mov hFont3, eax
    invoke CreateFont, 33, 15, 0, 0, FW_BLACK, FALSE, FALSE, FALSE, DEFAULT_CHARSET, OUT_CHARACTER_PRECIS, CLIP_CHARACTER_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH or FF_DONTCARE, addr szFontName
    mov hFont4, eax

	invoke CreateFont, 72, 33, 0, 0, FW_BLACK, FALSE, FALSE, FALSE, DEFAULT_CHARSET, OUT_CHARACTER_PRECIS, CLIP_CHARACTER_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH or FF_DONTCARE, addr szFontName
    mov hFontDigit1, eax
    invoke CreateFont, 59, 27, 0, 0, FW_BLACK, FALSE, FALSE, FALSE, DEFAULT_CHARSET, OUT_CHARACTER_PRECIS, CLIP_CHARACTER_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH or FF_DONTCARE, addr szFontName
    mov hFontDigit3, eax
    invoke CreateFont, 46, 21, 0, 0, FW_BLACK, FALSE, FALSE, FALSE, DEFAULT_CHARSET, OUT_CHARACTER_PRECIS, CLIP_CHARACTER_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH or FF_DONTCARE, addr szFontName
    mov hFontDigit4, eax

	invoke CreateSolidBrush, 0FFEBEEh
    mov hBrush_d2, eax
    invoke CreateSolidBrush, 0FFCDD2h
    mov hBrush_d4, eax
    invoke CreateSolidBrush, 0EF9A9Ah
    mov hBrush_d8, eax
    invoke CreateSolidBrush, 09060cdh
    mov hBrush_d16, eax
    invoke CreateSolidBrush, 0325abch
    mov hBrush_d32, eax
    invoke CreateSolidBrush, 00f858bh
    mov hBrush_d64, eax
    invoke CreateSolidBrush, 0db9300h
    mov hBrush_d128, eax
    invoke CreateSolidBrush, 0123eabh
    mov hBrush_d256, eax
    invoke CreateSolidBrush, 03b14afh
    mov hBrush_d512, eax
    invoke CreateSolidBrush, 001939ah
    mov hBrush_d1024, eax
    invoke CreateSolidBrush, 0ffab00h
    mov hBrush_d2048, eax

	ret
initFmt endp

; 窗口消息处理函数
ProcWinMain proc , hWnd, uMsg, wParam, lParam
	local @stPs:PAINTSTRUCT
	local @hDc
	
	mov eax,uMsg  ;uMsg是消息类型，如下面的WM_PAINT,WM_CREATE

	.if eax==WM_CREATE  ;创建窗口
		invoke initFmt
	
	.elseif eax==WM_CLOSE  ;窗口关闭消息
		invoke DestroyWindow,hWinMain
		invoke PostQuitMessage,NULL

	.elseif eax== WM_KEYDOWN	;WM_CHAR为按下键盘消息，按下的键的值存在wParam中
		MOV edx,wParam
		;如果为W则向上移动
		.if edx == 'W' || edx == 'w' || edx == VK_UP
			
			invoke moveW
			;如果可以向上移动，在随机位置产生一个2
			.IF changedW == 1
				invoke random32
			.endif
			;计算分数
			;INVOKE getScore
			;更新界面
			;INVOKE UpdataGame,hWnd
			invoke InvalidateRect, hWnd, NULL, FALSE ; 更新窗体
		;同上
		.elseif edx == 'S' || edx == 's' || edx == VK_DOWN
			invoke moveS
			
			.IF changedS == 1
				invoke random32
			.endif
			;INVOKE getScore
			;NVOKE UpdataGame,hWnd
			invoke InvalidateRect, hWnd, NULL, FALSE
		;同上
		.elseif edx =='A' || edx == 'a' || edx == VK_LEFT
			
			invoke moveA
			
			.IF changedA == 1
				invoke random32
			.endif
			;INVOKE getScore

			invoke InvalidateRect, hWnd, NULL, FALSE
		;同上
		.elseif edx == 'D' || edx == 'd' || edx == VK_RIGHT
			invoke moveD
			.IF changedD == 1
				invoke random32
			.endif

			invoke InvalidateRect, hWnd, NULL, FALSE
		.endif
		
		;如果游戏还未获胜，gameContinue=0，如果游戏已经获胜过了，且玩家选择继续玩，则gameContinue=1，将不会再弹出获胜消息
		;.if gameContinue == 0
			;如果gameIsWin==1，游戏获胜，弹出游戏获胜消息
		;	.if gameIsWin == 1
		;		invoke gameWin
		;	.endif
		;.endif
		
		;判断游戏是否结束
		invoke gameEnd
		;如果游戏结束，绘制失败弹窗
		.if gameIsEnd == 1
			invoke MessageBox,hWinMain,offset szText7,offset szText6,MB_OK
			;重新开始游戏
			.if eax == IDOK
				invoke initFmt
				invoke InvalidateRect, hWnd, NULL, FALSE
			.endif
		.endif
	
	.elseif eax==WM_PAINT  ;如果想自己绘制客户区，在这里些代码，即第一次打开窗口会显示什么信息
		

		invoke BeginPaint,hWnd,addr @stPs
		mov @hDc, eax

		;绘制界面
		invoke DrawGame,hWnd, @hDc

		invoke EndPaint,hWnd,addr @stPs
	.else  ;否则按默认处理方法处理消息
		invoke DefWindowProc,hWnd,uMsg,wParam,lParam
		ret
	.endif

	xor eax,eax
	ret
ProcWinMain endp

; 窗口程序，注册主窗口
WinMain proc
	local @stWndClass:WNDCLASSEX
	local @stMsg:MSG
	
	;获取应用程序的句柄，把该句柄的值放在hInstance
	invoke GetModuleHandle,NULL		
	mov hInstance,eax

	invoke RtlZeroMemory,addr @stWndClass,sizeof @stWndClass  ;将stWndClass初始化全0

	;注册窗口类
	invoke LoadCursor,0,IDC_ARROW
	mov @stWndClass.hCursor,eax
	push hInstance
	pop @stWndClass.hInstance
	mov @stWndClass.cbSize,sizeof WNDCLASSEX
	mov @stWndClass.style,CS_HREDRAW or CS_VREDRAW
	mov @stWndClass.lpfnWndProc,offset ProcWinMain
	mov @stWndClass.hbrBackground,COLOR_WINDOW+1
	mov @stWndClass.lpszClassName,offset szClassName

	;注册窗口类，注册前先填写参数WNDCLASSEX结构
	invoke RegisterClassEx,addr @stWndClass  

	;创建窗口并将句柄放在hWinMain中
	invoke CreateWindowEx,WS_EX_CLIENTEDGE,\ 
			offset szClassName,offset szCaptionMain,\ 
			WS_OVERLAPPEDWINDOW,400,50,WINDOW_WIDTH,WINDOW_HEIGHT,\
			NULL,NULL,hInstance,NULL
	mov hWinMain,eax  

	;显示窗口
	invoke ShowWindow,hWinMain,SW_SHOWNORMAL

	;刷新窗口客户区
	invoke UpdateWindow,hWinMain  

	;进入无限的消息获取和处理的循环
	.while TRUE  
		invoke GetMessage,addr @stMsg,NULL,0,0  ;从消息队列中取出第一个消息，放在stMsg结构中
		.break .if eax==0						;如果是退出消息，eax将会置成0，退出循环
		invoke TranslateMessage,addr @stMsg		;将基于键盘扫描码的按键信息转换成对应的ASCII码，如果消息不是通过键盘输入的，这步将跳过
		invoke DispatchMessage,addr @stMsg		;这条语句的作用是找到该窗口程序的窗口过程，通过该窗口过程来处理消息
	.endw
	ret
WinMain endp

main proc

	invoke WinMain  ;主程序就调用了窗口程序和结束程序两个函数
	invoke ExitProcess,NULL
	ret
main endp
end main


