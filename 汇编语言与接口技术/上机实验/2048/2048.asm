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

; ���ڳߴ�
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

; ���塢��ˢ��ʽ
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

; �����ʽ
szFormat byte '%d', 0

; ��ʾ��ʾ
szFontName byte 'Clear Sans', 0
TitleMessage byte '2048', 0
CurrentScoreMessage byte 'CURRENT MAX', 0
BestScoreMessage byte 'HISTORY MAX', 0
hColor_d1 dd 0656e77h
hColor_d2 dd 0f2f6f9h

; ������
hInstance dd ?
hWinMain dd ?

; ��Ϸ���ݱ���
hGame dd 25 dup(?)

seed dd 0
score dd 0
bestScore dd 0

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

moveDelta dd 16 DUP(0)

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
max EQU 16 ;4*4����

.code

; ��Ϸʤ����Ϣ��������Ҽ���������gameContinueΪ1
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

; ����ͬ�෨��msvcrt�Ĳ��������������������γɾ���
random32       proc     
               local @a, @c, @randNum, @randPos

			    mov @a, 0343FDH
				mov @c, 269EC3H
				;�����Ĵ���
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
				;���λ������2��4���г�ͻ����ѭ��
				mov		eax, @randPos
                cmp     gameMat[eax*4],0
                je      inital_mat
				loop        lcg
    inital_mat:
                mov     eax,@randPos
				mov		edx,@randNum
                mov     gameMat[eax*4],edx

				;����seed����һ�δ��������ʼ������������
				mov		seed, eax
                popa
                ret        
    random32    Endp

; ���ݾ���ǰ״̬�������
getScore proc
    push ecx
    push edx
	push esi
	push edi

    xor ecx, ecx
    xor eax,eax
	mov score, 0
	;��������
	mov ecx, 16  ; �ܹ���16������
	mov edi, 0  ; ��ʼʱ�����ֵ����Ϊ0
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


; �������ĸ������ϵ��ƶ���ϳ�
; ����
moveW proc 
	
	MOV changedW,0
	
	mov col,4

.while col
	;���ѭ��
	mov row,1
	.while row < 5
	;�ڲ�ѭ��
	;����Ƚ�����edx����ǰλ�õ�ebx
	imul eax,row,4
	add eax,col
	sub eax,5
	mov edx,gameMat[eax*4]
	mov ebx,eax

	.if row == 1
		w_merge:
		;�ж��Ƿ�Ϊ0����Ϊ0�������ж�
		.if edx == 0
		inc row
		.continue
		.endif

		add ebx,4
		;�ж��Ƿ���бȽ�
		.if ebx >= 16
		inc row
		.continue
		.endif

		;�Ƿ��뱾λ������бȽ�
		.if eax == ebx
			jmp w_merge
		.endif

		;���ںϲ�������Ѱ�ҵ�0�������̽���Ƿ��пɺϲ�����
		.if gameMat[ebx*4] == 0
			jmp w_merge
		.endif
		;���ںϲ�������Ѱ�ҵ���ͬ���ַ��飬��ת��ϲ�����
		.if gameMat[ebx*4] == edx
			w_equ:
			;���ж�����������ַ��飬����кϲ�
			imul edx,2
			mov gameMat[eax*4],edx
			mov gameMat[ebx*4],0

			;��exchangeNum��־λ���и���
			mov exchangeNum,edx
			mov edx,1
			mov changedW,edx
			mov edx,exchangeNum

			inc row
			.continue
		.endif

	.else
		;�򷴷���̽����������0������ƶ�
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
	;����0���ƶ�����
	mov gameMat[ebx*4],edx
	mov gameMat[eax*4],0
	
	;��exchangeNum��־λ���и���
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

	ret
moveW endp

; ����
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

; ����
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

; ����
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


; �����Ϸ�Ƿ���������Ƿ������ƶ��ķ�����Ϸ�������޸�gameIsEnd=1
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

; ��ȡ���ֿ�λ��
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

; ��������
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
    
    ; �������ֱ�����ɫ
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
            
    ; ��������������ɫ
    .if _num < 8
        invoke SetTextColor, _hDc, hColor_d1
    .else
        invoke SetTextColor, _hDc, hColor_d2
    .endif

    mov eax, _cellStartX
    mov @digitStartX, eax
    mov eax, _cellStartY
    mov @digitStartY, eax

    ; ��������λ��
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

; ���ݵ�ǰ����������߷�
updateBestScore proc far C
	
	pushad

	mov eax, score
	.if bestScore < eax
		mov bestScore, eax
	.endif

	popad

	ret

updateBestScore endp

; ������Ϸ���棬������ʾ�򡢷�����4*4����
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

	; 4*4����λ��
    mov @boardStartX, (WINDOW_WIDTH - BOARD_EDGE) / 2 - WINDOW_BIAS
    mov @boardStartY, 225
    mov @boardEndX, (WINDOW_WIDTH + BOARD_EDGE) / 2 - WINDOW_BIAS
    mov @boardEndY, BOARD_EDGE + 225

	; ��ʷ��߷ֱ���λ��
	mov eax, @boardEndX
    mov @bestScoreEndX, eax
    sub eax, SCORE_WIDTH
    mov @bestScoreStartX, eax
    mov @bestScoreStartY, 65
    mov @bestScoreEndY, SCORE_HEIGHT + 65

	; ��ǰ��߷ֱ���λ��
	mov eax, @bestScoreStartX
	sub eax, 10
	mov @currentScoreEndX, eax
	sub eax, SCORE_WIDTH
	mov @currentScoreStartX, eax
	mov eax, @bestScoreStartY
	mov @currentScoreStartY, eax
	mov eax, @bestScoreEndY
	mov @currentScoreEndY, eax

	; ���λ��
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
    
	; ���ɵ�ǰ������ʾ
	invoke SelectObject, _hDc, hFont1
    invoke SetTextColor, _hDc, 0dae4eeh
    mov eax, @currentScoreStartX
    mov ebx, @currentScoreStartY
    add eax, 20
    add ebx, 6
    invoke TextOut, _hDc, eax, ebx, addr CurrentScoreMessage, lengthof CurrentScoreMessage
	
	; ���Ƶ�ǰ��������
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

	; ������ʷ�������ʾ
	invoke SelectObject, _hDc, hFont1
    invoke SetTextColor, _hDc, 0dae4eeh
    mov eax, @bestScoreStartX
    mov ebx, @bestScoreStartY
    add eax, 18
    add ebx, 6
    invoke TextOut, _hDc, eax, ebx, addr BestScoreMessage, lengthof BestScoreMessage
	
	; ������ʷ��߷�����
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


	; ������ʾ��Ϣ
	; ���Ʊ�����
	invoke CreateSolidBrush, 0f1f2e0h
    mov @deletedummy, eax
    invoke SelectObject, _hDc, eax
    invoke RoundRect, _hDc, @introStartX, @introStartY, @introEndX, @introEndY, 6, 6
    invoke DeleteObject, @deletedummy
    invoke SelectObject, _hDc, hFont3
    invoke SetTextColor, _hDc, 0cfdee9h
    mov eax, @introStartX
    mov ebx, @introStartY

	; ����������Ϣ
	invoke SelectObject, _hDc, hFont3
    invoke SetTextColor, _hDc, 0656e77h
	mov eax, @introStartY
	add eax, 2
    invoke TextOut, _hDc, @introStartX, eax, addr szText1, lengthof szText1
	mov eax, @introStartY
	add eax, 25
    invoke TextOut, _hDc, @introStartX, eax, addr szText2, lengthof szText2


    ; �������ֵ�ǳɫ������
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

	; ���ɾ�̬����
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
		;���gameIsWin==1����Ϸ��ʤ��������Ϸ��ʤ��Ϣ
		.if gameIsWin == 1
			invoke gameWin
		.endif
	.endif
    popad
    ret

DrawGame endp


; ��ʼ����Ϸ����
initFmt proc far C

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

	;�������������
	INVOKE GetTickCount
	mov seed, eax

	;gameMat�����������ֵ
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

; ������Ϣ������
ProcWinMain proc , hWnd, uMsg, wParam, lParam
	local @stPs:PAINTSTRUCT
	local @hDc
	
	mov eax,uMsg  ;uMsg����Ϣ���ͣ��������WM_PAINT,WM_CREATE

	.if eax==WM_CREATE  ;��������
		invoke initFmt
	
	.elseif eax==WM_CLOSE  ;���ڹر���Ϣ
		invoke DestroyWindow,hWinMain
		invoke PostQuitMessage,NULL

	.elseif eax== WM_KEYDOWN	;WM_CHARΪ���¼�����Ϣ�����µļ���ֵ����wParam��
		MOV edx,wParam
		;���ΪW�������ƶ�
		.if edx == 'W' || edx == 'w' || edx == VK_UP
			
			invoke moveW
			;������������ƶ��������λ�ò���һ��2
			.IF changedW == 1
				invoke random32
			.endif
			;�������
			;INVOKE getScore
			;���½���
			;INVOKE UpdataGame,hWnd
			invoke InvalidateRect, hWnd, NULL, FALSE ; ���´���
		;ͬ��
		.elseif edx == 'S' || edx == 's' || edx == VK_DOWN
			invoke moveS
			
			.IF changedS == 1
				invoke random32
			.endif
			;INVOKE getScore
			;NVOKE UpdataGame,hWnd
			invoke InvalidateRect, hWnd, NULL, FALSE
		;ͬ��
		.elseif edx =='A' || edx == 'a' || edx == VK_LEFT
			
			invoke moveA
			
			.IF changedA == 1
				invoke random32
			.endif
			;INVOKE getScore

			invoke InvalidateRect, hWnd, NULL, FALSE
		;ͬ��
		.elseif edx == 'D' || edx == 'd' || edx == VK_RIGHT
			invoke moveD
			.IF changedD == 1
				invoke random32
			.endif

			invoke InvalidateRect, hWnd, NULL, FALSE
		.endif
		
		;�����Ϸ��δ��ʤ��gameContinue=0�������Ϸ�Ѿ���ʤ���ˣ������ѡ������棬��gameContinue=1���������ٵ�����ʤ��Ϣ
		;.if gameContinue == 0
			;���gameIsWin==1����Ϸ��ʤ��������Ϸ��ʤ��Ϣ
		;	.if gameIsWin == 1
		;		invoke gameWin
		;	.endif
		;.endif
		
		;�ж���Ϸ�Ƿ����
		invoke gameEnd
		;�����Ϸ����������ʧ�ܵ���
		.if gameIsEnd == 1
			invoke MessageBox,hWinMain,offset szText7,offset szText6,MB_OK
			;���¿�ʼ��Ϸ
			.if eax == IDOK
				invoke initFmt
				invoke InvalidateRect, hWnd, NULL, FALSE
			.endif
		.endif
	
	.elseif eax==WM_PAINT  ;������Լ����ƿͻ�����������Щ���룬����һ�δ򿪴��ڻ���ʾʲô��Ϣ
		

		invoke BeginPaint,hWnd,addr @stPs
		mov @hDc, eax

		;���ƽ���
		invoke DrawGame,hWnd, @hDc

		invoke EndPaint,hWnd,addr @stPs
	.else  ;����Ĭ�ϴ�����������Ϣ
		invoke DefWindowProc,hWnd,uMsg,wParam,lParam
		ret
	.endif

	xor eax,eax
	ret
ProcWinMain endp

; ���ڳ���ע��������
WinMain proc
	local @stWndClass:WNDCLASSEX
	local @stMsg:MSG
	
	;��ȡӦ�ó���ľ�����Ѹþ����ֵ����hInstance
	invoke GetModuleHandle,NULL		
	mov hInstance,eax

	invoke RtlZeroMemory,addr @stWndClass,sizeof @stWndClass  ;��stWndClass��ʼ��ȫ0

	;ע�ᴰ����
	invoke LoadCursor,0,IDC_ARROW
	mov @stWndClass.hCursor,eax
	push hInstance
	pop @stWndClass.hInstance
	mov @stWndClass.cbSize,sizeof WNDCLASSEX
	mov @stWndClass.style,CS_HREDRAW or CS_VREDRAW
	mov @stWndClass.lpfnWndProc,offset ProcWinMain
	mov @stWndClass.hbrBackground,COLOR_WINDOW+1
	mov @stWndClass.lpszClassName,offset szClassName

	;ע�ᴰ���࣬ע��ǰ����д����WNDCLASSEX�ṹ
	invoke RegisterClassEx,addr @stWndClass  

	;�������ڲ����������hWinMain��
	invoke CreateWindowEx,WS_EX_CLIENTEDGE,\ 
			offset szClassName,offset szCaptionMain,\ 
			WS_OVERLAPPEDWINDOW,400,50,WINDOW_WIDTH,WINDOW_HEIGHT,\
			NULL,NULL,hInstance,NULL
	mov hWinMain,eax  

	;��ʾ����
	invoke ShowWindow,hWinMain,SW_SHOWNORMAL

	;ˢ�´��ڿͻ���
	invoke UpdateWindow,hWinMain  

	;�������޵���Ϣ��ȡ�ʹ����ѭ��
	.while TRUE  
		invoke GetMessage,addr @stMsg,NULL,0,0  ;����Ϣ������ȡ����һ����Ϣ������stMsg�ṹ��
		.break .if eax==0						;������˳���Ϣ��eax�����ó�0���˳�ѭ��
		invoke TranslateMessage,addr @stMsg		;�����ڼ���ɨ����İ�����Ϣת���ɶ�Ӧ��ASCII�룬�����Ϣ����ͨ����������ģ��ⲽ������
		invoke DispatchMessage,addr @stMsg		;���������������ҵ��ô��ڳ���Ĵ��ڹ��̣�ͨ���ô��ڹ�����������Ϣ
	.endw
	ret
WinMain endp

main proc

	invoke WinMain  ;������͵����˴��ڳ���ͽ���������������
	invoke ExitProcess,NULL
	ret
main endp
end main


