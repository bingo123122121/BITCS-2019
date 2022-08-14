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
Mars_PrintStr0 db "Please Input 16 numbers:", 0ah, 0
Mars_PrintStr1 db "Array A:", 0ah, 0
Mars_PrintStr2 db 0ah, 0
Mars_PrintStr3 db "Array B:", 0ah, 0
Mars_PrintStr4 db 0ah, 0

.code
array4_4 proc
local A[1000]:dword
local B[1000]:dword
local i:dword
local j:dword
local @1:dword
local @2:dword
local @4:dword
local @3:dword
local @5:dword
local @6:dword
local @7:dword
local @9:dword
local @8:dword
local @10:dword
local @12:dword
local @11:dword
local @13:dword
local @14:dword
local @15:dword
local @16:dword
local @17:dword
local @19:dword
local @18:dword
local @20:dword
local @21:dword
local @22:dword
local @23:dword
local @24:dword
local @26:dword
local @25:dword
local @27:dword
local @28:dword
local @29:dword

invoke printf, addr Mars_PrintStr0
mov eax, 0
mov i, eax
iterationCondInLabel0:
mov eax, i
cmp eax, 4
jge iterationGotoEnd1
mov eax, 0
mov j, eax
iterationCondInLabel2:
mov eax, j
cmp eax, 4
jge iterationGotoEnd3
mov eax, 0
mov @4, eax
mov eax, i
imul eax, 4
mov @3, eax
mov eax, @3
add eax, @4
mov @4, eax
mov eax, @4
add eax, j
mov @4, eax
mov esi, @4
mov eax, A[esi*4]
mov @5, eax
invoke scanf, addr digitGetFmt, addr @6
mov eax, @6
mov @5, eax
mov eax, @5
mov esi, @4
mov A[4*esi], eax
mov eax, 3
sub eax, j
mov @7, eax
mov eax, 0
mov @9, eax
mov eax, @7
imul eax, 4
mov @8, eax
mov eax, @8
add eax, @9
mov @9, eax
mov eax, @9
add eax, i
mov @9, eax
mov esi, @9
mov eax, B[esi*4]
mov @10, eax
mov eax, 0
mov @12, eax
mov eax, i
imul eax, 4
mov @11, eax
mov eax, @11
add eax, @12
mov @12, eax
mov eax, @12
add eax, j
mov @12, eax
mov esi, @12
mov eax, A[esi*4]
mov @13, eax
mov eax, @13
mov @10, eax
mov eax, @10
mov esi, @9
mov B[4*esi], eax
iterationGotoCond4:
mov eax, j
mov @14, eax
mov eax, j
inc eax
mov j, eax
jmp iterationCondInLabel2
iterationGotoEnd3:
iterationGotoCond5:
mov eax, i
mov @15, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel0
iterationGotoEnd1:
invoke printf, addr Mars_PrintStr1
mov eax, 0
mov i, eax
iterationCondInLabel6:
mov eax, i
cmp eax, 4
jge iterationGotoEnd7
mov eax, 0
mov j, eax
iterationCondInLabel8:
mov eax, j
cmp eax, 4
jge iterationGotoEnd9
mov eax, 0
mov @19, eax
mov eax, i
imul eax, 4
mov @18, eax
mov eax, @18
add eax, @19
mov @19, eax
mov eax, @19
add eax, j
mov @19, eax
mov esi, @19
mov eax, A[esi*4]
mov @20, eax
invoke printf, addr digitPrintFmt, @20
iterationGotoCond10:
mov eax, j
mov @21, eax
mov eax, j
inc eax
mov j, eax
jmp iterationCondInLabel8
iterationGotoEnd9:
invoke printf, addr Mars_PrintStr2
iterationGotoCond11:
mov eax, i
mov @22, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel6
iterationGotoEnd7:
invoke printf, addr Mars_PrintStr3
mov eax, 0
mov i, eax
iterationCondInLabel12:
mov eax, i
cmp eax, 4
jge iterationGotoEnd13
mov eax, 0
mov j, eax
iterationCondInLabel14:
mov eax, j
cmp eax, 4
jge iterationGotoEnd15
mov eax, 0
mov @26, eax
mov eax, i
imul eax, 4
mov @25, eax
mov eax, @25
add eax, @26
mov @26, eax
mov eax, @26
add eax, j
mov @26, eax
mov esi, @26
mov eax, B[esi*4]
mov @27, eax
invoke printf, addr digitPrintFmt, @27
iterationGotoCond16:
mov eax, j
mov @28, eax
mov eax, j
inc eax
mov j, eax
jmp iterationCondInLabel14
iterationGotoEnd15:
invoke printf, addr Mars_PrintStr4
iterationGotoCond17:
mov eax, i
mov @29, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel12
iterationGotoEnd13:
ret
array4_4 endp

main proc

invoke array4_4
mov eax, 0
ret
main endp
end main

