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
Mars_PrintStr0 db "please input ten int number for bubble sort:", 0ah, 0
Mars_PrintStr1 db "before bubble sort:", 0ah, 0
Mars_PrintStr2 db 0ah, 0
Mars_PrintStr3 db "after bubble sort:", 0ah, 0

.code
main proc
local a[1000]:dword
local i:dword
local @1:dword
local @3:dword
local @4:dword
local @5:dword
local @6:dword
local @7:dword
local @9:dword
local @10:dword
local @11:dword
local @12:dword
local j:dword
local @15:dword
local @14:dword
local @13:dword
local @18:dword
local @19:dword
local @20:dword
local @22:dword
local @23:dword
local @16:dword
local tmp:dword
local @25:dword
local @26:dword
local @28:dword
local @29:dword
local @30:dword
local @32:dword
local @33:dword
local @34:dword
local @36:dword
local @37:dword
local @38:dword
local @39:dword
local @40:dword
local @42:dword
local @43:dword
local @44:dword

invoke printf, addr Mars_PrintStr0
mov eax, 0
mov i, eax
iterationCondInLabel0:
mov eax, i
cmp eax, 10
jge iterationGotoEnd1
mov eax, 0
mov @3, eax
mov eax, @3
add eax, i
mov @3, eax
mov esi, @3
mov eax, a[esi*4]
mov @4, eax
invoke scanf, addr digitGetFmt, addr @5
mov eax, @5
mov @4, eax
mov eax, @4
mov esi, @3
mov a[4*esi], eax
iterationGotoCond2:
mov eax, i
mov @6, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel0
iterationGotoEnd1:
invoke printf, addr Mars_PrintStr1
mov eax, 0
mov i, eax
iterationCondInLabel3:
mov eax, i
cmp eax, 10
jge iterationGotoEnd4
mov eax, 0
mov @9, eax
mov eax, @9
add eax, i
mov @9, eax
mov esi, @9
mov eax, a[esi*4]
mov @10, eax
invoke printf, addr digitPrintFmt, @10
iterationGotoCond5:
mov eax, i
mov @11, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel3
iterationGotoEnd4:
invoke printf, addr Mars_PrintStr2
mov eax, 0
mov i, eax
iterationCondInLabel6:
mov eax, i
cmp eax, 10
jge iterationGotoEnd7
mov eax, 0
mov j, eax
iterationCondInLabel8:
mov eax, 10
sub eax, i
mov @15, eax
mov eax, @15
sub eax, 1
mov @14, eax
mov eax, j
cmp eax, @14
jge iterationGotoEnd9
mov eax, 0
mov @18, eax
mov eax, @18
add eax, j
mov @18, eax
mov esi, @18
mov eax, a[esi*4]
mov @19, eax
mov eax, j
add eax, 1
mov @20, eax
mov eax, 0
mov @22, eax
mov eax, @22
add eax, @20
mov @22, eax
mov esi, @22
mov eax, a[esi*4]
mov @23, eax
mov eax, @19
cmp eax, @23
jle ifCondFalseLabel10
mov eax, 0
mov @25, eax
mov eax, @25
add eax, j
mov @25, eax
mov esi, @25
mov eax, a[esi*4]
mov @26, eax
mov eax, @26
mov tmp, eax
mov eax, 0
mov @28, eax
mov eax, @28
add eax, j
mov @28, eax
mov esi, @28
mov eax, a[esi*4]
mov @29, eax
mov eax, j
add eax, 1
mov @30, eax
mov eax, 0
mov @32, eax
mov eax, @32
add eax, @30
mov @32, eax
mov esi, @32
mov eax, a[esi*4]
mov @33, eax
mov eax, @33
mov @29, eax
mov eax, @29
mov esi, @28
mov a[4*esi], eax
mov eax, j
add eax, 1
mov @34, eax
mov eax, 0
mov @36, eax
mov eax, @36
add eax, @34
mov @36, eax
mov esi, @36
mov eax, a[esi*4]
mov @37, eax
mov eax, tmp
mov @37, eax
mov eax, @37
mov esi, @36
mov a[4*esi], eax
jmp gotoEndLabel11
ifCondFalseLabel10:
gotoEndLabel11:
iterationGotoCond12:
mov eax, j
mov @38, eax
mov eax, j
inc eax
mov j, eax
jmp iterationCondInLabel8
iterationGotoEnd9:
iterationGotoCond13:
mov eax, i
mov @39, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel6
iterationGotoEnd7:
invoke printf, addr Mars_PrintStr3
mov eax, 0
mov i, eax
iterationCondInLabel14:
mov eax, i
cmp eax, 10
jge iterationGotoEnd15
mov eax, 0
mov @42, eax
mov eax, @42
add eax, i
mov @42, eax
mov esi, @42
mov eax, a[esi*4]
mov @43, eax
invoke printf, addr digitPrintFmt, @43
iterationGotoCond16:
mov eax, i
mov @44, eax
mov eax, i
inc eax
mov i, eax
jmp iterationCondInLabel14
iterationGotoEnd15:
mov eax, 0
ret
main endp
end main

