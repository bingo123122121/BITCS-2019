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

    ; ���ƴ���
    hInstance HINSTANCE ?
    commandLine LPSTR ?
    
    mode db	'r', 0

    ; ������Ԫ��
    className db "�ı��ȶ�", 0
    szText db "������Ҫ�ȶԵ������ı���·������", 0

    edit db "edit", 0
    editContent1 db "C:\Users\bu123\Desktop\test1.txt", 0
    editContent2 db "C:\Users\bu123\Desktop\test2.txt", 0

    button db "button", 0
    buttonContent db "�ı��ȶ�", 0

    ; �洢
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
    notMatchBegin db "�ı����ݲ�ͬ���У�", 0
    notMatchLine db endl, "������", 0

    ; ��ʾ��Ϣ
    alert db "����", 0
    fileNotExist db "�ļ������ڣ�", 0

    note db "�ı��ȶԽ��", 0
    matched db "���ı�������ȫ��ͬ��", 0

    ; ��ʽ����
    scanfFmt db "%s", 0


.code

WinMain proc hInst: HINSTANCE, hPreInst: HINSTANCE, cmdLine: LPSTR, cmdShow: DWORD
    local wndClassEx: WNDCLASSEX    ; ������
    local msg: MSG          ; ��Ϣid
    local hwnd: HWND        ; ���ھ��

    ; wndClassEx������ʼ��
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

    ; ע�ᴰ��
    invoke RegisterClassEx, addr wndClassEx

    ; ��������
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
    
    ; ��ʾ����
    invoke ShowWindow, hwnd, cmdShow

    ; ���´���
    invoke UpdateWindow, hwnd

    ; ��Ϣ����
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
    
    ; ����ݻ�
    .if message == WM_DESTROY      
        invoke DestroyWindow, WinMain
        invoke PostQuitMessage, NULL

    ; �����Ӵ��ڣ�1���ύ��ť��2���ı�1·����3���ı�2·��
    .elseif message == WM_CREATE   

        ; ������ť
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

        ; �����ļ�·��1���ı���
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
		
        ; �����ļ�·��2���ı���
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

    ; ������Ӧ
    .elseif message == WM_COMMAND

        ; ��ʼ��������line��match�����notMatchMsg
        mov line, 0
        mov match, 1
        invoke memset, addr notMatchMsg, 0, 1000
        invoke strcat, addr notMatchMsg, addr notMatchBegin
        
        ; ����Ӧ������¼�
        .if wParam == MK_LBUTTON

            ; ȡ��������ļ�·��
            invoke GetDlgItemText, hWnd, 2, offset filePath1, 1000
            invoke GetDlgItemText, hWnd, 3, offset filePath2, 1000
        
            ; ���ļ����������洢�ļ�ָ��
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

                ; buffer���
                invoke memset, addr buffer1, 0, 1000
                invoke memset, addr buffer2, 0, 1000

                ; �ı������line�У�fgets�������eax����Ϊ0������ļ�β
                mov ebx, line
                inc ebx
                mov line, ebx

                invoke fgets, offset buffer1, 1000, file1
                push eax
                invoke fgets, offset buffer2, 1000, file2
                push eax

                ; �ı��ȶ�
                invoke strcmp, offset buffer1, offset buffer2

                ; �ı���ͬ���¼�ı���ͬ���к�
                .if eax != 0
                    mov match, 0
                    invoke strcat, offset notMatchMsg, offset notMatchLine
                    invoke _itoa, line, offset lineToStr, 10
                    invoke strcat, offset notMatchMsg, offset lineToStr
                .endif

                ; ��һ���ļ�δ���ļ�β������ȶ�
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