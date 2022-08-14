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

; 换行
endl equ <0DH, 0AH>

.data
	; 数组A、B、result，数组初始化0，长度变量初始化为0
	numCharA byte 500 dup(0)
	numCharB byte 500 dup(0)
	numCharResult byte 1000 dup(0)
	numIntA dword 500 dup(0)
	numIntB dword 500 dup(0)
	numIntResult dword 1000 dup(0)

	lengthA dword 0
	lengthB dword 0
	lengthResult dword 0

	; 输入格式
	inputMsg byte "input two numbers:", endl, 0
	inputFmt byte "%s", 0

	; 输出格式
	outputMsgPositive byte "result: %s", endl, 0
	outputMsgNegative byte "result: %s%s", endl, 0

	base dword 10

	; 符号位，初值0表示正，每读到一个负号改负
	sign byte 0
	negativeSign byte "-"



.code

getLength proc far c numChar: ptr byte, len: ptr dword

	; 数组长度写入eax
	mov esi, numChar
	movzx eax, byte ptr [esi]
	.if eax == 2DH
		xor sign, 1
		invoke strlen, numChar
		dec eax
	.else
		invoke strlen, numChar
	.endif

	; eax写入len地址
	mov ecx, len
	mov [ecx], eax

	ret

getLength endp

reverseStrToInt proc far c numChar: ptr byte, numInt: ptr dword, len: dword
	
	mov esi, numChar
	movzx eax, byte ptr [esi]

	; 负数从数字开始
	.if eax == 2DH
		inc esi
	.endif

	; 设置循环次数
	mov ecx, len

	; 数字逐个压入栈
	l1:
		movzx eax, byte ptr[esi]
		sub eax, 30H
		push eax
		inc esi
		loop l1

	; 重置循环次数
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

	; 外层循环变量ebx，遍历A每一位
	mov ebx, -1
	loopX:
		inc ebx
		cmp ebx, lengthA
		jnb endLoopX

		; 内层循环变量ecx，遍历B每一位
		xor ecx, ecx
		loopY:
			; A[ebx] * B[ecx]
			mov eax, dword ptr numIntA[4 * ebx]
			mul numIntB[4 * ecx]    ; 结果存在edx:eax中
			
			; 当前结果应保存在哪一位
			mov esi, ebx
			add esi, ecx

			; 保存结果
			add numIntResult[4 * esi], eax

			inc ecx
			cmp ecx, lengthB
			jnb loopX
			jmp loopY
	
	; 进位
	endLoopX:
		; 不会超过lengthA+lengthB位，lengthResult=lengthA+lengthB
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
			; 商eax，余数edx
			add numIntResult[4 * ebx + 4], eax
			mov numIntResult[4 * ebx], edx

			inc ebx
			jmp forward
		
	; 去除前缀0
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

		; 实际长度写入lengthResult
		inc ecx
		mov esi, offset lengthResult
		mov [esi], ecx

		; 倒序存入byte数组
		invoke reverseIntToStr
	ret
largeNumberMultiply endp


start proc

	; 输入数字A、B，存入数组
	invoke printf, offset inputMsg
	invoke scanf, addr inputFmt, addr numCharA
	invoke scanf, addr inputFmt, addr numCharB

	; 取出两数长度存入length变量，逆序排列存入数值数组
	invoke getLength, addr numCharA, addr lengthA
	invoke getLength, addr numCharB, addr lengthB

	; 逆序数组
	invoke reverseStrToInt, offset numCharA, offset numIntA, lengthA
	invoke reverseStrToInt, offset numCharB, offset numIntB, lengthB

	; 大数乘法
	invoke largeNumberMultiply

	; 打印结果
	.if sign == 1
		invoke printf, offset outputMsgNegative, addr negativeSign, addr numCharResult
	.else
		invoke printf, offset outputMsgPositive, addr numCharResult
	.endif

	ret
start endp
end start