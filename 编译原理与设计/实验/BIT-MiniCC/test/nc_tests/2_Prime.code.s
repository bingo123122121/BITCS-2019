.386
.model flat, stdcall
option casemap:none
include windows.inc
include user32.inc
includelib user32.lib
include kernel32.inc
includelib kernel32.lib
include msvcrt.inc
includelib msvcrt.lib
includelib ucrt.lib
scanf proto c :dword, :vararg
printf proto c :dword, :vararg

.data
digitPrintFmt db "%d ", 0
digitGetFmt db "%d", 0
Mars_PrintStr0 db "Please input a number:", 0ah, 0
Mars_PrintStr1 db "The number of prime numbers within n is:", 0ah, 0

.code
prime proc n:dword
local sum:dword
local i:dword
local j:dword
local flag:dword
local @1:dword
local @3:dword
local @2:dword
local @5:dword
local @4:dword
local @6:dword
local @7:dword
local @8:dword
local @9:dword

mov eax, 0
mov sum, eax
mov eax, 1
mov flag, eax
mov eax, 2
mov i, eax
iterationCondInLabel0:
mov eax, i
cmp eax, n
jg iterationGotoEnd1
mov eax, 1
mov flag, eax
mov eax, 2
mov j, eax
iterationCondInLabel2:
mov eax, j
imul eax, j
mov @3, eax
mov eax, @3
cmp eax, i
jg iterationGotoEnd3
xor edx, edx
mov eax, i
mov ebx, j
div ebx
mov @5, edx
mov eax, @5
cmp eax, 0
jnz ifCondFalseLabel4
mov eax, 0
mov flag, eax
jmp gotoEndLabel5
ifCondFalseLabel4:
gotoEndLabel5:
iterationGotoCond6:
mov eax, j
mov @6, eax
mov eax, j
inc eax
mov j, eax
jmp iterationCondInLabel2
iterationGotoEnd3:
mov eax, flag
cmp eax, 1
jnz ifCondFalseLabel7
mov eax, sum
mov @8, eax
mov eax, sum
inc eax
mov sum, eax
invoke printf, addr digitPrintFmt, i
jmp gotoEndLabel8
ifCondFalseLabel7:
gotoEndLabel8:
iterationGotoCond9:
mov eax, i
mov @9, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel0
iterationGotoEnd1:
mov eax, sum
ret
prime endp

main proc
local n:dword
local @10:dword
local res:dword
local @11:dword

invoke printf, addr Mars_PrintStr0
invoke scanf, addr digitGetFmt, addr @10
mov eax, @10
mov n, eax
invoke prime, n
mov @11, eax
mov eax, @11
mov res, eax
invoke printf, addr Mars_PrintStr1
invoke printf, addr digitPrintFmt, res
mov eax, 0
ret
main endp
end main

