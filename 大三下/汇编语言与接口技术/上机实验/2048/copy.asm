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
hInstance dd ?  ;���Ӧ�ó���ľ��
hWinMain dd ?   ;��Ŵ��ڵľ��
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
changedW dd 1 ;�����Ƿ����ƶ�����ֵΪ0���ƶ�����Ϊ1
changedS dd 0 ;�����Ƿ����ƶ�����ֵΪ0���ƶ�����Ϊ1
changedA dd 1 ;�����Ƿ����ƶ�����ֵΪ0���ƶ�����Ϊ1
changedD dd 1 ;�����Ƿ����ƶ�����ֵΪ0���ƶ�����Ϊ1

gameIsEnd dd    0
gameIsWin dd 0
gameContinue dd 0
tmpMat dd 16 DUP(?)

Data byte 10 dup(?)

static db 'static',0
edit db 'edit',0

;��������
gameMat dd 0,0,0,0
		dd 0,0,0,0
		dd 0,0,0,0
		dd 0,0,0,0

;��ʱ����
tmpGameMat  dd 0,0,0,0
			dd 0,0,0,0
			dd 0,0,0,0
			dd 0,0,0,0
overEdge dd ? ;�ж��Ƿ�Խ���ж�
exchangeNum dd ? ;�м�������������ֽ���

row dd 1
col dd 1 

printf_pref db '%d',0ah,0 ;������䣬�����������
printf_d db '%c',0ah,0
scanf_sh byte '%c',0ah,0 ;������䡣�����ƶ�����
printf_ok db 'it is ok',0ah,0 ;�������

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
;@Description   :  ������Ϸʤ����Ϣ��������ҿ�ѡ��������棬����gameContinueΪ1��֮���ٵ�������
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
;@Param			:  random_seed  ���������
;					max_val		�����������С����
;@Description   :  ����������������޶�����������ֵ����32λ�����������ʼ������
;@Author        :  WXL
;--------------------------------------------------------------------------------------
random32       proc    random_seed:DWORD,max_val:DWORD 
                push ecx				;����Ĵ�����Ϣ
                push edx
                call       GetTickCount ;��ȡϵͳʱ��
                mov        ecx,random_seed
                add        eax,ecx		
                rol        ecx,1
                add        ecx,666h 
                mov        random_seed,ecx	;������������

                mov     ecx,32

    crc_bit:    shr        eax,1			;����eax��Ϣ���������
                jnc        loop_crc_bit 
                xor        eax,0edb88320h

    loop_crc_bit:
                loop        crc_bit
                mov         ecx,max_val

				;�޶��������С������randData
                xor         edx,edx ;��16λ���
                div         ecx
                xchg        edx,eax ;��������eax
                or          eax,eax
				mov			randData,eax	

				;���λ������2���޳�ͻʱ��ת
                cmp     gameMat[eax*4],0
                je      inital_mat
				;��ͻ����
                mov     ecx,16
                mov     randData,eax
                xor     eax,eax     ;���tmpָ��
                xor     edx,edx     ;���gameָ��

    get_emp:    
                cmp     gameMat[edx*4],0
                jne      cmp_ne      ;����Ϊ��
                
                mov     tmpMat[eax*4],edx
                inc     eax
    cmp_ne:         
                inc     edx
                loop    get_emp
                ;eax���tmp����

                mov     ecx,eax
                xor     edx,edx
                mov     eax,randData
                div     ecx
                xchg    edx,eax ;eaxΪtmpָ��

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
;@Description   :  ���ݾ���ǰ״̬�������
;@Author        :  WXL
;--------------------------------------------------------------------------------------
getscore       proc
            push    ecx
            push    edx

            mov     ecx,0
            xor     eax,eax
			mov     score,0
			;��������
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
			;�жϵ÷֣�ά����־
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
;@Description   :  ʵ��4��4�ķ������ĸ������ϵ��ƶ���ϳ�
;@Author        :  FY
;--------------------------------------------------------------------------------------

moveW proc far C uses eax ebx ecx edx
	;��ʼ���Ƿ����ƶ����жϱ���
	MOV changedW,0
	;��ʼ��ѭ�����
	mov ecx,4
	mov col,ecx
	mov row,1
w:	
	;ѭ������һ����4��������ж��������ж�
	mov col,ecx
	mov row,1

	jmp w_trav

w_end:
	;��ͬһ�������ѭ����������һ�������4��������ж�
	loop w

	ret
w_trav:
	;����Ƚ���
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	;ͬһ������ĸ�λ�ý���ѭ���ж�
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
	;��ת����һ��4������ж�
	inc row
	cmp row,5
	jb w_trav

	jmp w_end

w_merge:
	;�ж��Ƿ�Ϊ0����Ϊ0�������ж�
	cmp edx,0
	je w_mov

	add ebx,4
	;�ж��Ƿ���бȽϣ������бȽ��������һ��ͬ����ķ�����ж�
	cmp ebx,16
	jae w_mov

	;�Ƿ��뱾λ������бȽ�
	cmp eax,ebx
	je w_merge

	;���ںϲ�������Ѱ�ҵ�0�������̽���Ƿ��пɺϲ�����
	cmp gameMat[ebx*4],0
	je w_merge
	;���ںϲ�������Ѱ�ҵ���ͬ���ַ��飬��ת��ϲ�����
	cmp gameMat[ebx*4],edx
	je w_equ

	jmp w_mov

w_equ:
	;���ж�����������ַ��飬����кϲ�
	imul edx,2
	mov gameMat[eax*4],edx
	mov gameMat[ebx*4],0

	;���Ƿ��ܽ����ƶ���־λ���и���
    mov exchangeNum,edx
	mov edx,1
	mov changedW,edx
	mov edx,exchangeNum

	jmp w_mov

w_fore:
	;�򷴷���̽����������0������ƶ�
	cmp edx,0
	je w_mov
	mov ebx,eax
	sub ebx,4
	
	cmp gameMat[ebx*4],0
	je w_zero

	jmp w_merge

w_zero:
	;����0���ƶ�����
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0
	
	;���Ƿ��ܽ����ƶ���־λ���и���
    mov exchangeNum,edx
	mov edx,1
	mov changedW,edx
	mov edx,exchangeNum
    
	mov eax,ebx
	sub ebx,4
	;�߽���
	cmp ebx,4000
	ja w_merge
	;��ǰ�������㣬���������ж�
	cmp gameMat[ebx*4],0
	je w_zero

	jmp w_merge
moveW endp

moveD proc far C uses eax ebx ecx edx
	;��ʼ���Ƿ����ƶ����жϱ���
	mov changedD,0
	;��ʼ��ѭ�����
	mov ecx,4
	mov col,ecx
	mov row,4

d:
	;ѭ������һ����4��������ж��������ж�
	mov row,ecx
	mov col,4

	jmp d_trav

d_end:
	;��ͬһ�������ѭ����������һ�������4��������ж�
	loop d

	ret
d_trav:
	;����Ƚ���
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	;ͬһ������ĸ�λ�ý���ѭ���ж�
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
	;��ת����һ��4������ж�
	dec col
	cmp col,0
	ja d_trav

	jmp d_end
d_merge:
	;�ж��Ƿ�Ϊ0����Ϊ0�������ж�
	cmp edx,0
	je d_mov

	dec ebx
	;�ж��Ƿ���бȽϣ������бȽ��������һ��ͬ����ķ�����ж�
	mov overEdge,eax
	mov eax,row
	dec eax
	imul eax,4
	dec eax
	cmp eax,ebx
	je d_mov
	mov eax,overEdge

	;�Ƿ��뱾λ������бȽ�
	cmp eax,ebx
	je d_merge

	;���ںϲ�������Ѱ�ҵ�0�������̽���Ƿ��пɺϲ�����
	cmp gameMat[ebx*4],0
	je d_merge

	;���ںϲ�������Ѱ�ҵ���ͬ���ַ��飬��ת��ϲ�����
	cmp gameMat[ebx*4],edx
	je d_equ

	jmp d_mov

d_equ:
	;���ж�����������ַ��飬����кϲ�
	imul edx,2
	mov gameMat[eax*4],edx
	mov gameMat[ebx*4],0

	;���Ƿ��ܽ����ƶ���־λ���и���
    mov exchangeNum,edx
	mov edx,1
	mov changedD,edx
	mov edx,exchangeNum

	jmp d_mov
d_fore:
	;�򷴷���̽����������0������ƶ�
	cmp edx,0
	je d_mov
	mov ebx,eax
	inc ebx

	cmp gameMat[ebx*4],0
	je d_zero

	jmp d_merge
d_zero:
	;����0���ƶ�����
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0

	;���Ƿ��ܽ����ƶ���־λ���и���
    mov exchangeNum,edx
	mov edx,1
	mov changedD,edx
	mov edx,exchangeNum
    
	mov eax,ebx
	inc ebx

	;�߽���
	mov overEdge,ebx
	mov ebx,row
	imul ebx,4
	cmp overEdge,ebx
	je d_merge
	mov ebx,overEdge

	;��ǰ�������㣬���������ж�
	cmp gameMat[ebx*4],0
	je d_zero

	jmp d_merge
moveD endp

moveA proc far C uses eax ebx ecx edx
	;��ʼ���Ƿ����ƶ����жϱ���
	mov changedA,0
	;��ʼ��ѭ�����
	mov ecx,4
	mov row,ecx
	mov col,1
a:
	;ѭ������һ����4��������ж��������ж�
	mov row,ecx
	mov col,1

	jmp a_trav

a_end:
	loop a

	ret
a_trav:
	;����Ƚ���
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	;ͬһ������ĸ�λ�ý���ѭ���ж�
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
	;��ת����һ��4������ж�
	inc col
	cmp col,5
	jb a_trav

	jmp a_end
a_merge:
	;�ж��Ƿ�Ϊ0����Ϊ0�������ж�
	cmp edx,0
	je a_mov

	inc ebx
	;�ж��Ƿ���бȽϣ������бȽ��������һ��ͬ����ķ�����ж�
	mov overEdge,eax
	mov eax,row
	imul eax,4
	cmp eax,ebx
	je a_mov
	mov eax,overEdge

	;�Ƿ��뱾λ������бȽ�
	cmp eax,ebx
	je a_merge

	;���ںϲ�������Ѱ�ҵ�0�������̽���Ƿ��пɺϲ�����
	cmp gameMat[ebx*4],0
	je a_merge

	;���ںϲ�������Ѱ�ҵ���ͬ���ַ��飬��ת��ϲ�����
	cmp gameMat[ebx*4],edx
	je a_equ

	jmp a_mov

a_equ:
	;���ж�����������ַ��飬����кϲ�
	imul edx,2
	mov gameMat[eax*4],edx
	mov gameMat[ebx*4],0

	;���Ƿ��ܽ����ƶ���־λ���и���
    mov exchangeNum,edx
	mov edx,1
	mov changedA,edx
	mov edx,exchangeNum
    
	jmp a_mov

a_fore:
	;�򷴷���̽����������0������ƶ�
	cmp edx,0
	je a_mov
	mov ebx,eax
	dec ebx
	
	cmp gameMat[ebx*4],0
	je a_zero

	jmp a_merge

a_zero:
	;����0���ƶ�����
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0

	;���Ƿ��ܽ����ƶ���־λ���и���
	mov exchangeNum,edx
	mov edx,1
	mov changedA,edx
	mov edx,exchangeNum

	mov eax,ebx
	dec ebx

	;�߽���
	mov overEdge,ebx
	mov ebx,row
	dec ebx
	imul ebx,4
	dec ebx
	cmp overEdge,ebx
	je a_merge
	mov ebx,overEdge

	;��ǰ�������㣬���������ж�
	cmp gameMat[ebx*4],0
	je a_zero

	jmp a_merge
moveA endp

moveS proc far C uses eax ebx ecx edx
	;��ʼ���Ƿ����ƶ����жϱ���
	mov changedS,0

	;��ʼ��ѭ�����
	mov ecx,4
	mov row,ecx
	mov col,4


s:
	;ѭ������һ����4��������ж��������ж�
	mov col,ecx
	mov row,4

	jmp s_trav

s_end:
	;��ͬһ�������ѭ����������һ�������4��������ж�
	loop s

	ret
s_trav:
	;����Ƚ���
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	;ͬһ������ĸ�λ�ý���ѭ���ж�
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
	;��ת����һ��4������ж�
	dec row
	cmp row,0
	ja s_trav

	jmp s_end

s_merge:
	;�ж��Ƿ�Ϊ0����Ϊ0�������ж�
	cmp edx,0
	je s_mov

	sub ebx,4

	;�ж��Ƿ���бȽϣ������бȽ��������һ��ͬ����ķ�����ж�
	cmp ebx,400
	jae s_mov
	
	;�Ƿ��뱾λ������бȽ�
	cmp eax,ebx
	je s_merge

	;���ںϲ�������Ѱ�ҵ�0�������̽���Ƿ��пɺϲ�����
	cmp gameMat[ebx*4],0
	je s_merge

	;���ںϲ�������Ѱ�ҵ���ͬ���ַ��飬��ת��ϲ�����
	cmp gameMat[ebx*4],edx
	je s_equ

	jmp s_mov
s_equ:
	;���ж�����������ַ��飬����кϲ�
	imul edx,2
	mov gameMat[eax*4],edx
	mov gameMat[ebx*4],0

	;���Ƿ��ܽ����ƶ���־λ���и���
    mov exchangeNum,edx
	mov edx,1
	mov changedS,edx
	mov edx,exchangeNum

	jmp s_mov

s_fore:
	;�򷴷���̽����������0������ƶ�
	cmp edx,0
	je s_mov
	mov ebx,eax
	add ebx,4

	cmp gameMat[ebx*4],0
	je s_zero

	jmp s_merge
s_zero:
	;����0���ƶ�����
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0

	;���Ƿ��ܽ����ƶ���־λ���и���
    mov exchangeNum,edx
	mov edx,1
	mov changedS,edx
	mov edx,exchangeNum

	mov eax,ebx
	add ebx,4
	;�߽���
	cmp ebx,16 
	jae s_merge

	;��ǰ�������㣬���������ж�
	cmp gameMat[ebx*4],0
	je s_zero
	jmp s_merge

moveS endp


;--------------------------------------------------------------------------------------
;@Function Name :  gameEnd
;@Description   :  �����Ϸ�Ƿ��������Ϸ�������޸�gameIsEnd=1
;@Author        :  Chris
;--------------------------------------------------------------------------------------
gameEnd proc
			
	;��ecx��edxѹջ��ά�����û���
    push    ecx
    push    edx
	;��ecx��ѭ������
	mov ecx,16
	;esi�����ʵ�esi������
	mov esi,0
L1:
	;ѹջ��esi������
	push gameMat[esi*4]
	;��ջ����tmpGameMat�����浱ǰ��Ϸ����
	pop tmpGameMat[esi*4]
	inc esi
	loop L1

	;���������ƶ�������Ƿ�����ƶ�
	invoke moveW
			
	;ͬ��
	mov ecx,16
	mov esi,0
	;��ԭ��Ϸ����
L2:
	push tmpGameMat[esi*4]
	pop gameMat[esi*4]
	inc esi
	loop L2

	;����Ƿ���������ƶ�
	invoke moveA

	;ͬ�ϣ���ԭ��Ϸ���棬����Ƿ���������ƶ�
	mov ecx,16
	mov esi,0
L3:
	push tmpGameMat[esi*4]
	pop gameMat[esi*4]
	inc esi
	loop L3
	invoke moveS

	;ͬ�ϣ���ԭ��Ϸ���棬����Ƿ���������ƶ�
	mov ecx,16
	mov esi,0
L4:
	push tmpGameMat[esi*4]
	pop gameMat[esi*4]
	inc esi
	loop L4
	invoke moveD

	;��ԭ��Ϸ����
	mov ecx,16
	mov esi,0
L5:
	push tmpGameMat[esi*4]
	pop gameMat[esi*4]
	inc esi
	loop L5

	;eax��0
	xor eax,eax

	;eax=changeW+changeS+changeA+changeD
    mov eax,changedW
    add eax,changedS
    add eax,changedA
	add eax,changedD

	;��eax������0�����ʾ��Ϸ��������ĳһ�����ƶ�����Ϸ������
    cmp eax,0
    jne end_node

	;��֮��Ϸ������ΪgameIsEnd��ֵ1
    mov eax,1
    mov gameIsEnd,eax
    
	;��ԭ
end_node:
    pop edx
    pop ecx

    ret

gameEnd Endp

;--------------------------------------------------------------------------------------
;@Function Name :  num2byte
;@Param			:  number(ת��������)
;@Description   :  �����ְ�λתΪ�ַ��洢�������У���2048=��2����0����4����8��
;@Author        :  Chris
;--------------------------------------------------------------------------------------
num2byte proc far C uses eax esi ecx,number:dword

	;��ռĴ�����32λ������Ҫ
	xor eax,eax
	xor edx,edx
	xor ebx,ebx
	;�������ŵ�eax��
	mov eax,number
	mov ecx,10
	
	;2048/10=204...8ѹ��8
	;204/10=20...4ѹ��4
	;20/10=2...0ѹ��0
	;2/10=0...2ѹ��2
	;��Ϊ0����ѭ��
L1:
	;ebx��¼numberλ��
	inc ebx
	;eax/ecx��32λ����������eax�У�������edx��
	idiv ecx
	;����+��0��=��0��-��9��
	add edx,30H
	;��ѹջ����һ����
	push edx
	;�ǵ���0��32λ����������Ϊedx:eax
	xor edx,edx
	;eaxΪ�̣���=0��ʾ������
	cmp eax,0
	;����0����ѭ��
	jg L1

	;esi=0����ʾ��esi���ַ�
	mov esi,0
L2:
	;ebxΪ֮ǰ��¼��numberλ����ÿ��ѭ����1��ֱ��Ϊ0
	dec ebx
	;��ջ�е��ַ���ջ�浽eax��
	pop eax
	;���ֻΪ��0��-��9����ֻ��8λ�Ĵ����У�����ν��
	mov byte ptr Data[esi],al
	inc esi
	cmp ebx,0
	jg L2
	
	;ѭ��������ĩβ��0��ʾ����
	mov Data[esi],0
	ret

num2byte endp

;--------------------------------------------------------------------------------------
;@Function Name :  DrawGame
;@Param			:  hWnd�����ھ����
;@Description   :  ������Ϸ���棬˵���򣬺ͷ�����
;@Author        :  Chris
;--------------------------------------------------------------------------------------
DrawGame proc far C uses eax esi ecx edx,hWnd
	
	;��������
	local @hFont:HFONT
	local @logfont:LOGFONT
	invoke RtlZeroMemory,addr @logfont,sizeof @logfont
	mov @logfont.lfCharSet,GB2312_CHARSET
	mov @logfont.lfHeight,-40
	mov @logfont.lfWeight,FW_BOLD
	invoke CreateFontIndirect,addr @logfont
	mov @hFont,eax

	;i=0����������תL2
	mov i,0
	jmp L2
	
	;i++
L1:
	mov eax,i
	add eax,1
	mov i,eax

	;i<4?L3��L7
L2:
	cmp i,4
	jge L7
	
	;j=0��jmp L5
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

	;����100*100�ľ��ο�
L6:
	;eax=i*100+140�����Ƶ�x���꣬140Ϊ��ʼ����
	imul eax,i,100
	add eax,140
	;ecx=j*100+100�����Ƶ�y���꣬100Ϊ��ʼ����
	imul ecx,j,100
	add ecx,100

	;edx=i*4+j����ʾ��[i][j]������
	imul edx,i,4
	add edx,j
	;gameMat[i][j]��ֵתΪ�ַ�����浽Data�У�dword��*4
	invoke num2byte,dword ptr gameMat[edx*4]
	;���Ϊ0
	;eax=i*100+140�����Ƶ�x���꣬140Ϊ��ʼ����
	imul eax,i,100
	add eax,140
	;ecx=j*100+100�����Ƶ�y���꣬100Ϊ��ʼ����
	imul ecx,j,100
	add ecx,100
	.IF Data[0] =='0'
		;������̬�ؼ��������б߿�
		invoke CreateWindowEx,NULL,offset static,offset EmptyText,\
		WS_CHILD or WS_VISIBLE or SS_CENTER or WS_BORDER or SS_CENTERIMAGE,ecx,eax,100,100,\  
		hWnd,edx,hInstance,NULL  ;���Ϊedx
	.else
		invoke CreateWindowEx,NULL,offset static,offset Data,\
		WS_CHILD or WS_VISIBLE or SS_CENTER or WS_BORDER or SS_CENTERIMAGE,ecx,eax,100,100,\ 
		hWnd,edx,hInstance,NULL  ;���Ϊedx
	.endif
	;edx=i*4+j����ʾ��[i][j]������
	imul edx,i,4
	add edx,j
	;�洢���ھ�����������ֵ��eax��
	mov hGame[edx*4],eax
	;����SendMessage�ı�����
	invoke SendMessage,eax,WM_SETFONT,@hFont,1
	
	jmp L4
L7:
	;������Ϸ˵������
	;�����ı��򣬵���ΪDisabeled��ֹ��Ҹ���
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

	;���Ʒ�����
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
;@Param			:  hWnd�����ھ����
;@Description   :  ����gameMatֵ�ı䣬��Ҫ�ı�����ֵ
;@Author        :  Chris
;--------------------------------------------------------------------------------------
UpdataGame proc far C uses eax esi ecx edx,hWnd
	
	;i=0����������תL2
	mov i,0
	jmp L2

	;i++
L1:
	mov eax,i
	add eax,1
	mov i,eax

	;i<4?L3��L7
L2:
	cmp i,4
	jge L7
	
	;j=0��jmp L5
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
	;edx=i*4+j��ʾ[i][j]�鷽��
	imul edx,i,4
	add edx,j
	;ת��ֵ
	invoke num2byte,dword ptr gameMat[edx*4]
	imul edx,i,4
	add edx,j
	;���ÿؼ��е�ֵ
	.if Data[0] =='0'
		INVOKE SetWindowText,hGame[edx*4],offset EmptyText
	.else
		INVOKE SetWindowText,hGame[edx*4],offset Data
	.endif

	JMP L4
L7:
	;���÷�����ֵ
	invoke num2byte,score
	INVOKE SetWindowText,hGame[84],offset Data
	xor eax,eax
	ret

UpdataGame endp
;--------------------------------------------------------------------------------------
;@Function Name :  ReStartGame
;@Param			:  
;@Description   :  ���¿�ʼ��Ϸ
;@Author        :  Chris
;--------------------------------------------------------------------------------------
ReStartGame proc far C uses eax esi ecx edx
	
	;��ʼ��gameMatΪ0
	mov ecx,16
	mov esi,0
L1:
	mov gameMat[esi*4],0
	inc esi
	loop L1

	;��ʼ������ֵ
	mov gameIsEnd,0
	mov gameIsWin,0
	mov gameContinue,0
	mov score,0

	;gameMat�����������ֵ
	INVOKE random32,dat,max
	INVOKE random32,dat,max
	ret

ReStartGame endp
;--------------------------------------------------------------------------------------
;@Function Name :  _ProcWinMain
;@Param			:  
;@Description   :  ���ڻص���������������Ϣ
;@Author        :  Chris
;--------------------------------------------------------------------------------------
_ProcWinMain proc uses ebx edi esi,hWnd,uMsg,wParam,lParam  ;���ڹ���
	local @stPs:PAINTSTRUCT
	local @stRect:RECT
	local @hDc
	LOCAL @oldPen:HPEN
	local @hBm
	
	mov eax,uMsg  ;uMsg����Ϣ���ͣ��������WM_PAINT,WM_CREATE

	.if eax==WM_PAINT  ;������Լ����ƿͻ�����������Щ���룬����һ�δ򿪴��ڻ���ʾʲô��Ϣ
		

		invoke BeginPaint,hWnd,addr @stPs
		
		;����λͼ
		;���Ȼ�ȡ����DC
		invoke GetDC, hWnd
		mov @hDc,eax
		
		;�������ݴ���DC�Ļ���dc
		invoke CreateCompatibleDC,@hDc
		mov hdcIDB_BITMAP1,eax
		
		invoke CreateCompatibleDC,@hDc
		mov hdcIDB_BITMAP2,eax
		
		;����λͼ����
		invoke CreateCompatibleBitmap, @hDc,150,80
		mov hbmIDB_BITMAP1,eax

		invoke CreateCompatibleBitmap, @hDc,90,60
		mov hbmIDB_BITMAP2,eax

		;��hbm��hdc��
		invoke SelectObject,hdcIDB_BITMAP1,hbmIDB_BITMAP1
		
		;����λͼ��λͼ�����
		invoke LoadBitmap,hInstance,BITMAP1
		mov @hBm,eax
		;������λͼΪͼ���Ļ�ˢ
		invoke CreatePatternBrush,@hBm
		push eax
		;�Ի�ˢ��仺��DC
		invoke SelectObject,hdcIDB_BITMAP1,eax
		;����PATCOPY�ķ�ʽ
		invoke PatBlt,hdcIDB_BITMAP1,0,0,150,80,PATCOPY
		pop eax
		;ɾ����ˢ
		invoke DeleteObject,eax
		;��������DC�ϻ���λͼdc
		invoke BitBlt,@hDc,90,0,150,80,hdcIDB_BITMAP1,0,0,SRCCOPY
		
		;ͬ��
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
	
	.elseif eax==WM_CLOSE  ;���ڹر���Ϣ
		invoke DestroyWindow,hWinMain
		invoke PostQuitMessage,NULL

	.elseif eax==WM_CREATE  ;��������
		;���ƽ���
		invoke DrawGame,hWnd

	.elseif eax== WM_CHAR	;WM_CHARΪ���¼�����Ϣ�����µļ���ֵ����wParam��
		MOV edx,wParam
		;���ΪW�������ƶ�
		.if edx == 'W' || edx == 'w'
			
			invoke moveW
			;������������ƶ��������λ�ò���һ��2
			.IF changedW == 1
				invoke random32,dat,max
			.endif
			;�������
			INVOKE getscore
			;���½���
			INVOKE UpdataGame,hWnd
		;ͬ��
		.elseif edx == 'S' || edx == 's'
			invoke moveS
			
			.IF changedS == 1
				invoke random32,dat,max
			.endif
			INVOKE getscore
			INVOKE UpdataGame,hWnd
		;ͬ��
		.elseif edx =='A' || edx == 'a'
			
			invoke moveA
			
			.IF changedA == 1
				invoke random32,dat,max
			.endif
			INVOKE getscore
			INVOKE UpdataGame,hWnd
		;ͬ��
		.elseif edx == 'D' || edx == 'd'

			invoke moveD
			
			.IF changedD == 1
				invoke random32,dat,max
			.endif
			INVOKE getscore
			INVOKE UpdataGame,hWnd
		.endif
		
		;�����Ϸ��δ��ʤ��gameContinue=0�������Ϸ�Ѿ���ʤ���ˣ������ѡ������棬��gameContinue=1���������ٵ�����ʤ��Ϣ
		.if gameContinue == 0
			;���gameIsWin==1����Ϸ��ʤ��������Ϸ��ʤ��Ϣ
			.if gameIsWin == 1
				invoke gameWin
			.endif
		.endif
		
		;�ж���Ϸ�Ƿ����
		invoke gameEnd
		;�����Ϸ����������ʧ�ܵ���
		.if gameIsEnd == 1
			invoke MessageBox,hWinMain,offset szText7,offset szText6,MB_OK
			;���¿�ʼ��Ϸ
			.if eax == IDOK
				invoke ReStartGame
				INVOKE UpdataGame,hWnd
			.endif
		.endif

	.else  ;����Ĭ�ϴ�����������Ϣ
		invoke DefWindowProc,hWnd,uMsg,wParam,lParam
		ret
	.endif

	xor eax,eax
	ret
_ProcWinMain endp

;--------------------------------------------------------------------------------------
;@Function Name :  _WinMain
;@Param			:  
;@Description   :  ���ڳ���ע��������
;@Author        :  Chris
;--------------------------------------------------------------------------------------

_WinMain proc  ;���ڳ���
	local @stWndClass:WNDCLASSEX  ;������һ���ṹ����������������WNDCLASSEX��һ�������ඨ���˴��ڵ�һЩ��Ҫ���ԣ�ͼ�꣬��꣬����ɫ�ȣ���Щ�������ǵ������ݣ����Ƿ�װ��WNDCLASSEX�д��ݵġ�
	local @stMsg:MSG	;��������stMsg��������MSG����������Ϣ���ݵ�	
	local @redbru:HBRUSH
	invoke GetModuleHandle,NULL  ;�õ�Ӧ�ó���ľ�����Ѹþ����ֵ����hInstance�У������ʲô���򵥵�������ĳ������ı�ʶ�����ļ���������ھ��������ͨ������ҵ���Ӧ������
	mov hInstance,eax
	invoke RtlZeroMemory,addr @stWndClass,sizeof @stWndClass  ;��stWndClass��ʼ��ȫ0

	;ע�ᴰ����
	invoke LoadCursor,0,IDC_ARROW
	mov @stWndClass.hCursor,eax					;---------------------------------------
	push hInstance							;
	pop @stWndClass.hInstance					;
	mov @stWndClass.cbSize,sizeof WNDCLASSEX			;�ⲿ���ǳ�ʼ��stWndClass�ṹ�и��ֶε�ֵ�������ڵĸ�������
	mov @stWndClass.style,CS_HREDRAW or CS_VREDRAW
	mov @stWndClass.lpfnWndProc,offset _ProcWinMain			;
	;�������������ʵ����ָ���˸ô��ڳ���Ĵ��ڹ�����_ProcWinMain	;
	mov @stWndClass.hbrBackground,COLOR_WINDOW+1			;
	mov @stWndClass.lpszClassName,offset szClassName		;---------------------------------------
	invoke RegisterClassEx,addr @stWndClass  ;ע�ᴰ���࣬ע��ǰ����д����WNDCLASSEX�ṹ

	invoke CreateWindowEx,WS_EX_CLIENTEDGE,\ 
			offset szClassName,offset szCaptionMain,\ 
			WS_OVERLAPPEDWINDOW,400,200,600,600,\
			NULL,NULL,hInstance,NULL
	mov hWinMain,eax  ;�������ں��������eax�У����ڰѾ������hWinMain�С�
	invoke ShowWindow,hWinMain,SW_SHOWNORMAL  ;��ʾ���ڣ�ע�⵽����������ݵĲ����Ǵ��ڵľ��������ǰ����˵�ģ�ͨ����������ҵ�������ʶ������
	invoke UpdateWindow,hWinMain  ;ˢ�´��ڿͻ���

	.while TRUE  ;�������޵���Ϣ��ȡ�ʹ����ѭ��
		invoke GetMessage,addr @stMsg,NULL,0,0  ;����Ϣ������ȡ����һ����Ϣ������stMsg�ṹ��
		.break .if eax==0  ;������˳���Ϣ��eax�����ó�0���˳�ѭ��
		invoke TranslateMessage,addr @stMsg  ;���ǰѻ��ڼ���ɨ����İ�����Ϣת���ɶ�Ӧ��ASCII�룬�����Ϣ����ͨ����������ģ��ⲽ������
		invoke DispatchMessage,addr @stMsg  ;���������������ҵ��ô��ڳ���Ĵ��ڹ��̣�ͨ���ô��ڹ�����������Ϣ
	.endw
	ret
_WinMain endp

main proc

	;INVOKE random32,dat,max
	;INVOKE random32,dat,max
	;invoke getscore
	invoke ReStartGame
	call _WinMain  ;������͵����˴��ڳ���ͽ���������������
	invoke ExitProcess,NULL
	ret
main endp
end main
