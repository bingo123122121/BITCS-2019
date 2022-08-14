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
Mars_PrintStr1 db "This number's fibonacci value is :", 0ah, 0

.code
fibonacci proc num:dword
local res:dword
local @1:dword
local @2:dword
local @3:dword
local @4:dword
local @5:dword
local @6:dword

mov eax, num
cmp eax, 1
jge ifCondFalseLabel0
mov eax, 0
mov res, eax
jmp gotoEndLabel1
ifCondFalseLabel0:
mov eax, num
cmp eax, 2
jg ifCondFalseLabel2
mov eax, 1
mov res, eax
jmp gotoEndLabel3
ifCondFalseLabel2:
mov eax, num
sub eax, 1
mov @3, eax
invoke fibonacci, @3
mov @4, eax
mov eax, num
sub eax, 2
mov @5, eax
invoke fibonacci, @5
mov @6, eax
mov eax, @4
add eax, @6
mov res, eax
gotoEndLabel3:
gotoEndLabel1:
mov eax, res
ret
fibonacci endp

main proc
local n:dword
local @7:dword
local res:dword
local @8:dword

invoke printf, addr Mars_PrintStr0
invoke scanf, addr digitGetFmt, addr @7
mov eax, @7
mov n, eax
invoke fibonacci, n
mov @8, eax
mov eax, @8
mov res, eax
invoke printf, addr Mars_PrintStr1
invoke printf, addr digitPrintFmt, res
mov eax, 0
ret
main endp
end main

