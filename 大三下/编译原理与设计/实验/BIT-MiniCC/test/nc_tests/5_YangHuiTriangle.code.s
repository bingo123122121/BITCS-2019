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
Mars_PrintStr0 db 0ah, 0

.code
YangHuiTriangle proc
local i:dword
local j:dword
local triangle[1000]:dword
local @1:dword
local @2:dword
local @4:dword
local @3:dword
local @5:dword
local @6:dword
local @7:dword
local @8:dword
local @9:dword
local @11:dword
local @10:dword
local @12:dword
local @13:dword
local @15:dword
local @14:dword
local @16:dword
local @17:dword
local @18:dword
local @20:dword
local @19:dword
local @21:dword
local @22:dword
local @23:dword
local @24:dword
local @25:dword
local @27:dword
local @26:dword
local @28:dword
local @29:dword
local @30:dword

mov eax, 0
mov i, eax
iterationCondInLabel0:
mov eax, i
cmp eax, 8
jge iterationGotoEnd1
mov eax, 0
mov j, eax
iterationCondInLabel2:
mov eax, j
cmp eax, 8
jge iterationGotoEnd3
mov eax, 0
mov @4, eax
mov eax, i
imul eax, 8
mov @3, eax
mov eax, @3
add eax, @4
mov @4, eax
mov eax, @4
add eax, j
mov @4, eax
mov esi, @4
mov eax, triangle[esi*4]
mov @5, eax
mov eax, 1
mov @5, eax
mov eax, @5
mov esi, @4
mov triangle[4*esi], eax
iterationGotoCond4:
mov eax, j
mov @6, eax
mov eax, j
inc eax
mov j, eax
jmp iterationCondInLabel2
iterationGotoEnd3:
iterationGotoCond5:
mov eax, i
mov @7, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel0
iterationGotoEnd1:
mov eax, 2
mov i, eax
iterationCondInLabel6:
mov eax, i
cmp eax, 8
jge iterationGotoEnd7
mov eax, 1
mov j, eax
iterationCondInLabel8:
mov eax, j
cmp eax, i
jge iterationGotoEnd9
mov eax, 0
mov @11, eax
mov eax, i
imul eax, 8
mov @10, eax
mov eax, @10
add eax, @11
mov @11, eax
mov eax, @11
add eax, j
mov @11, eax
mov esi, @11
mov eax, triangle[esi*4]
mov @12, eax
mov eax, i
sub eax, 1
mov @13, eax
mov eax, 0
mov @15, eax
mov eax, @13
imul eax, 8
mov @14, eax
mov eax, @14
add eax, @15
mov @15, eax
mov eax, @15
add eax, j
mov @15, eax
mov esi, @15
mov eax, triangle[esi*4]
mov @16, eax
mov eax, j
sub eax, 1
mov @17, eax
mov eax, i
sub eax, 1
mov @18, eax
mov eax, 0
mov @20, eax
mov eax, @18
imul eax, 8
mov @19, eax
mov eax, @19
add eax, @20
mov @20, eax
mov eax, @20
add eax, @17
mov @20, eax
mov esi, @20
mov eax, triangle[esi*4]
mov @21, eax
mov eax, @16
add eax, @21
mov @12, eax
mov eax, @12
mov esi, @11
mov triangle[4*esi], eax
iterationGotoCond10:
mov eax, j
mov @22, eax
mov eax, j
inc eax
mov j, eax
jmp iterationCondInLabel8
iterationGotoEnd9:
iterationGotoCond11:
mov eax, i
mov @23, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel6
iterationGotoEnd7:
mov eax, 0
mov i, eax
iterationCondInLabel12:
mov eax, i
cmp eax, 8
jge iterationGotoEnd13
mov eax, 0
mov j, eax
iterationCondInLabel14:
mov eax, j
cmp eax, i
jg iterationGotoEnd15
mov eax, 0
mov @27, eax
mov eax, i
imul eax, 8
mov @26, eax
mov eax, @26
add eax, @27
mov @27, eax
mov eax, @27
add eax, j
mov @27, eax
mov esi, @27
mov eax, triangle[esi*4]
mov @28, eax
invoke printf, addr digitPrintFmt, @28
iterationGotoCond16:
mov eax, j
mov @29, eax
mov eax, j
inc eax
mov j, eax
jmp iterationCondInLabel14
iterationGotoEnd15:
invoke printf, addr Mars_PrintStr0
iterationGotoCond17:
mov eax, i
mov @30, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel12
iterationGotoEnd13:
ret
YangHuiTriangle endp

main proc

invoke YangHuiTriangle
mov eax, 0
ret
main endp
end main

