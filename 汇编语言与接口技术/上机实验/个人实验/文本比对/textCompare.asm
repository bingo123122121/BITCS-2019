.386
.model flat,stdcall
option casemap:none

include windows.inc
include user32.inc
include kernel32.inc

includelib user32.lib
includelib kernel32.lib
includelib msvcrt.lib

fopen proto c :dword, :dword
fgets proto c :dword, :dword, :dword
fclose proto c :dword
strcmp proto c :dword, :dword
strcat proto c :dword, :dword
_itoa proto c :dword, :dword, :dword
memset proto c :dword, :dword, :dword
MessageBoxA proto :dword, :dword, :dword, :dword
WinMain proto :dword, :dword, :dword, :dword

endl equ <0DH,0AH>

.data

    ; 控制窗口
    hInstance HINSTANCE ?
    commandLine LPSTR ?
    
    mode db	'r', 0

    ; 窗口中元素
    className db "文本比对", 0
    szText db "请输入要比对的两个文本的路径名：", 0

    edit db "edit", 0
    editContent1 db "C:\Users\bu123\Desktop\test1.txt", 0
    editContent2 db "C:\Users\bu123\Desktop\test2.txt", 0

    button db "button", 0
    buttonContent db "文本比对", 0

    ; 存储
    filePath1 db 1000 dup(0)
    filePath2 db 1000 dup(0)

    file1 dd ?
    file2 dd ?

    line dd 0
    lineToStr db 10 dup(0)
    buffer1 db 1000 dup(0)
    buffer2 db 1000 dup(0)

    match db 1
    notMatchMsg db 1000 dup(0)
    notMatchBegin db "文本内容不同的行：", 0
    notMatchLine db endl, "行数：", 0

    ; 提示信息
    alert db "错误", 0
    fileNotExist db "文件不存在！", 0

    note db "文本比对结果", 0
    matched db "两文本内容完全相同！", 0

    ; 格式控制
    scanfFmt db "%s", 0


.code

WinMain proc hInst: HINSTANCE, hPreInst: HINSTANCE, cmdLine: LPSTR, cmdShow: DWORD
    local wndClassEx: WNDCLASSEX    ; 窗口类
    local msg: MSG          ; 消息id
    local hwnd: HWND        ; 窗口句柄

    ; wndClassEx参数初始化
    mov wndClassEx.cbSize, SIZEOF WNDCLASSEX
    mov wndClassEx.style, CS_HREDRAW or CS_VREDRAW
    mov wndClassEx.lpfnWndProc, offset WndProc
    mov wndClassEx.cbClsExtra, 0
    mov wndClassEx.cbWndExtra, 0
    push hInstance
    pop wndClassEx.hInstance
    invoke LoadIcon, NULL, IDI_APPLICATION
    mov wndClassEx.hIcon, eax
    mov wndClassEx.hIconSm, eax
    invoke LoadCursor, NULL, IDC_ARROW
    mov wndClassEx.hCursor, eax
    mov wndClassEx.hbrBackground, (COLOR_WINDOW + 1)
    mov wndClassEx.lpszMenuName, NULL
    mov wndClassEx.lpszClassName, offset className

    ; 注册窗口
    invoke RegisterClassEx, addr wndClassEx

    ; 创建窗口
    invoke CreateWindowEx,\
                NULL,\
                addr className,\
                addr className,\
                WS_OVERLAPPEDWINDOW,\
                100, 100,\
                450, 280,\
                NULL,\
                0,\
                hInst,\
                NULL
    
    mov hwnd, eax
    
    ; 显示窗口
    invoke ShowWindow, hwnd, cmdShow

    ; 更新窗口
    invoke UpdateWindow, hwnd

    ; 消息处理
    .while TRUE
        invoke GetMessage, addr msg, NULL, 0, 0
        .if eax == 0
            .break
        .endif
        invoke TranslateMessage, addr msg
        invoke DispatchMessage, addr msg
    .endw

    ret

WinMain endp

WndProc proc hWnd: HWND, message: UINT, wParam: WPARAM, lParam: LPARAM
    local paintStruct: PAINTSTRUCT
    local rect: RECT
    local hDc: HDC
    
    ; 窗体摧毁
    .if message == WM_DESTROY      
        invoke DestroyWindow, WinMain
        invoke PostQuitMessage, NULL

    ; 创建子窗口，1：提交按钮，2：文本1路径，3：文本2路径
    .elseif message == WM_CREATE   

        ; 创建按钮
        invoke CreateWindowEx,\
                    NULL,\
                    offset button,\
                    offset buttonContent,\
                    WS_CHILD or WS_VISIBLE,\
                    150, 150,\
                    100, 60,\
                    hWnd,\
                    1,\
                    hInstance,\
                    NULL

        ; 输入文件路径1的文本框
        invoke CreateWindowEx,\
                    NULL,\
                    offset edit,\
                    offset editContent1,\
                    WS_CHILD or WS_VISIBLE,\
                    50, 50,\
                    300, 30,\
                    hWnd,\
                    2,\
                    hInstance,\
                    NULL
		
        ; 输入文件路径2的文本框
        invoke CreateWindowEx,\
                    NULL,\
                    offset edit,\
                    offset editContent2,\
                    WS_CHILD or WS_VISIBLE,\
                    50, 100,\
                    300, 30,\
                    hWnd,\
                    3,\
                    hInstance,\
                    NULL

    ; 动作响应
    .elseif message == WM_COMMAND

        ; 初始化，重置line、match，清空notMatchMsg
        mov line, 0
        mov match, 1
        invoke memset, addr notMatchMsg, 0, 1000
        invoke strcat, addr notMatchMsg, addr notMatchBegin
        
        ; 仅响应鼠标点击事件
        .if wParam == MK_LBUTTON

            ; 取出输入的文件路径
            invoke GetDlgItemText, hWnd, 2, offset filePath1, 1000
            invoke GetDlgItemText, hWnd, 3, offset filePath2, 1000
        
            ; 打开文件，错误处理，存储文件指针
            invoke fopen, offset filePath1, offset mode
            .if eax == 0
                invoke MessageBoxA, hWnd, offset fileNotExist, offset alert, MB_OK
                ret
            .endif
            mov file1, eax

            invoke fopen, offset filePath2, offset mode
            .if eax == 0
                invoke MessageBoxA, hWnd, offset fileNotExist, offset alert, MB_OK
                ret
            .endif
            mov file2, eax

            l1:

                ; buffer清空
                invoke memset, addr buffer1, 0, 1000
                invoke memset, addr buffer2, 0, 1000

                ; 文本读入第line行，fgets结果返回eax，若为0则读到文件尾
                mov ebx, line
                inc ebx
                mov line, ebx

                invoke fgets, offset buffer1, 1000, file1
                push eax
                invoke fgets, offset buffer2, 1000, file2
                push eax

                ; 文本比对
                invoke strcmp, offset buffer1, offset buffer2

                ; 文本不同需记录文本不同的行号
                .if eax != 0
                    mov match, 0
                    invoke strcat, offset notMatchMsg, offset notMatchLine
                    invoke _itoa, line, offset lineToStr, 10
                    invoke strcat, offset notMatchMsg, offset lineToStr
                .endif

                ; 有一个文件未到文件尾则继续比对
                pop eax
                cmp eax, 0
                jnz l1
                pop eax
                cmp eax, 0
                jnz l1

            .if match == 1
                invoke MessageBoxA, hWnd, offset matched, offset note, MB_OK
            .else
                invoke MessageBoxA, hWnd, offset notMatchMsg, offset note, MB_OK
            .endif

            invoke fclose, file1
            invoke fclose, file2

        .endif

    .else
        invoke DefWindowProc, hWnd, message, wParam, lParam

    .endif

    ret

WndProc endp

start proc
	invoke GetModuleHandle, NULL
    mov hInstance, eax
    invoke GetCommandLine
    mov commandLine, eax
    invoke WinMain, hInstance, NULL, commandLine, SW_SHOWDEFAULT
    invoke ExitProcess, eax

start endp
end start