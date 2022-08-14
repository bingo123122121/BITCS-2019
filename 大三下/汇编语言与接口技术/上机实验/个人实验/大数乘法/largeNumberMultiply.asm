.386
.model flat, stdcall
option casemap:none
 
include		windows.inc
include		user32.inc
includelib	user32.lib
include		kernel32.inc
includelib	kernel32.lib
include     msvcrt.inc
includelib  msvcrt.lib
includelib  ucrt.lib
 
scanf proto c: dword, :vararg
printf proto c: dword, :vararg
strlen proto c: dword, :vararg

; ����
endl equ <0DH, 0AH>

.data
	; ����A��B��result�������ʼ��0�����ȱ�����ʼ��Ϊ0
	numCharA byte 500 dup(0)
	numCharB byte 500 dup(0)
	numCharResult byte 1000 dup(0)
	numIntA dword 500 dup(0)
	numIntB dword 500 dup(0)
	numIntResult dword 1000 dup(0)

	lengthA dword 0
	lengthB dword 0
	lengthResult dword 0

	; �����ʽ
	inputMsg byte "input two numbers:", endl, 0
	inputFmt byte "%s", 0

	; �����ʽ
	outputMsgPositive byte "result: %s", endl, 0
	outputMsgNegative byte "result: %s%s", endl, 0

	base dword 10

	; ����λ����ֵ0��ʾ����ÿ����һ�����Ÿĸ�
	sign byte 0
	negativeSign byte "-"



.code

getLength proc far c numChar: ptr byte, len: ptr dword

	; ���鳤��д��eax
	mov esi, numChar
	movzx eax, byte ptr [esi]
	.if eax == 2DH
		xor sign, 1
		invoke strlen, numChar
		dec eax
	.else
		invoke strlen, numChar
	.endif

	; eaxд��len��ַ
	mov ecx, len
	mov [ecx], eax

	ret

getLength endp

reverseStrToInt proc far c numChar: ptr byte, numInt: ptr dword, len: dword
	
	mov esi, numChar
	movzx eax, byte ptr [esi]

	; ���������ֿ�ʼ
	.if eax == 2DH
		inc esi
	.endif

	; ����ѭ������
	mov ecx, len

	; �������ѹ��ջ
	l1:
		movzx eax, byte ptr[esi]
		sub eax, 30H
		push eax
		inc esi
		loop l1

	; ����ѭ������
	mov ecx, len
	mov esi, numInt
	l2:
		pop eax
		mov dword ptr[esi], eax
		add esi, 4
		loop l2

	ret

reverseStrToInt endp

reverseIntToStr proc far c
	mov ecx, lengthResult
	mov esi, 0
	l1:
		mov eax, dword ptr numIntResult[4 * esi]
		add eax, 30H
		push eax
		inc esi
		loop l1

	mov ecx, lengthResult
	mov esi, 0
	l2:
		pop eax
		mov byte ptr numCharResult[esi], al
		inc esi
		loop l2

	ret

reverseIntToStr endp


largeNumberMultiply proc far c

	; ���ѭ������ebx������Aÿһλ
	mov ebx, -1
	loopX:
		inc ebx
		cmp ebx, lengthA
		jnb endLoopX

		; �ڲ�ѭ������ecx������Bÿһλ
		xor ecx, ecx
		loopY:
			; A[ebx] * B[ecx]
			mov eax, dword ptr numIntA[4 * ebx]
			mul numIntB[4 * ecx]    ; �������edx:eax��
			
			; ��ǰ���Ӧ��������һλ
			mov esi, ebx
			add esi, ecx

			; ������
			add numIntResult[4 * esi], eax

			inc ecx
			cmp ecx, lengthB
			jnb loopX
			jmp loopY
	
	; ��λ
	endLoopX:
		; ���ᳬ��lengthA+lengthBλ��lengthResult=lengthA+lengthB
		mov ecx, lengthA
		add ecx, lengthB
		mov esi, offset lengthResult
		mov [esi], ecx

		xor ebx, ebx
		forward:
			cmp ebx, ecx
			jnb endForward
			
			xor edx, edx
			mov eax, numIntResult[4 * ebx]
			div base
			; ��eax������edx
			add numIntResult[4 * ebx + 4], eax
			mov numIntResult[4 * ebx], edx

			inc ebx
			jmp forward
		
	; ȥ��ǰ׺0
	endForward:
		mov ecx, lengthResult
		removeZero:
			cmp dword ptr numIntResult[4 * ecx], 0
			jnz finish
			cmp ecx, 0
			jz finish
			dec ecx
			jmp removeZero

	finish:

		; ʵ�ʳ���д��lengthResult
		inc ecx
		mov esi, offset lengthResult
		mov [esi], ecx

		; �������byte����
		invoke reverseIntToStr
	ret
largeNumberMultiply endp


start proc

	; ��������A��B����������
	invoke printf, offset inputMsg
	invoke scanf, addr inputFmt, addr numCharA
	invoke scanf, addr inputFmt, addr numCharB

	; ȡ���������ȴ���length�������������д�����ֵ����
	invoke getLength, addr numCharA, addr lengthA
	invoke getLength, addr numCharB, addr lengthB

	; ��������
	invoke reverseStrToInt, offset numCharA, offset numIntA, lengthA
	invoke reverseStrToInt, offset numCharB, offset numIntB, lengthB

	; �����˷�
	invoke largeNumberMultiply

	; ��ӡ���
	.if sign == 1
		invoke printf, offset outputMsgNegative, addr negativeSign, addr numCharResult
	.else
		invoke printf, offset outputMsgPositive, addr numCharResult
	.endif

	ret
start endp
end start