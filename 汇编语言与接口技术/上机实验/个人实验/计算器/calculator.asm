.386
.model flat,stdcall
option casemap:none

include msvcrt.inc
includelib msvcrt.lib
include user32.inc
includelib user32.lib
include kernel32.inc
includelib kernel32.lib
include windows.inc
includelib gdi32.lib
include gdi32.inc

strcat			PROTO C :DWORD, :DWORD
strcpy			PROTO C :DWORD, :DWORD
memset			PROTO C :DWORD, :DWORD, :DWORD
strlen			PROTO C :DWORD
sprintf			PROTO C :DWORD, :DWORD, :VARARG

WinMain			PROTO :DWORD, :DWORD, :DWORD, :DWORD
WndProc			PROTO :DWORD, :DWORD, :DWORD, :DWORD
AddString		PROTO :ptr BYTE
Clear			PROTO
BackSpace		PROTO
CalBinary		PROTO :REAL8, :REAL8, :BYTE
Calculate		PROTO
Cal				PROTO :BYTE

.const
divNum				dd	10
szWindowClass		db	"汇编计算器", 0
szError				db	"Input Error!", 0
szStatic			db	"EDIT", 0
szButton			db	"BUTTON", 0

floatFormat			db	"%.6f", 0

szInitExpr			db	"此处输入算式:", 0
szInitResult		db	"此处输出结果:", 0
szDoubleZero		db	"00", 0
szZero				db	"0", 0
szOne				db	"1", 0
szTwo				db	"2", 0
szThree				db	"3", 0
szFour				db	"4", 0
szFive				db	"5", 0
szSix				db	"6", 0
szSeven				db	"7", 0
szEight				db	"8", 0
szNine				db	"9", 0
szEqual				db	"=", 0
szAdd				db	"+", 0
szSub				db	"-", 0
szMult				db	"*", 0
szDiv				db	"/", 0
szPoint				db	".", 0
szSin				db	"sin", 0
szCos				db	"cos", 0
szTan				db	"tan", 0
szLeftBracket		db	"(", 0
szRightBracket		db	")", 0
szClear				db	"CE", 0
szBack				db	"BS", 0
szSinCacl			db	"#", 0
szCosCacl			db	"$", 0
szTanCacl			db	"&", 0

floatZero			real8	0.0
floatTen			real8	10.0
floatOne			real8	1.0
epsilon				real8	1.0e-12

.data
hInst				dd	0
hWnd				dd	0
hwndStatic			dd	0
hwndResult			dd	0
hwndButton			dd	24 dup(0)

expr				db	205 dup(0)
exprToCalc			db	205 dup(0)
strTemp				db	205 dup(0)
resultStr			db	205 dup(0)

state				dd		-2
len					dd		0
point				dd		0
opNow				db		0
numNow				real8	0.0
afterPoint			real8	1.0
numLeft				real8	0.0
numRight			real8	0.0
result				real8	0.0
intNum				dd		0

stackNum			real8	105 dup(0.0)
stackNumTop			dd		0
stackOp				db		105 dup(0)
stackOpTop			dd		0


.code
WinMain proc hInstance:HINSTANCE, hPrevInstance:HINSTANCE, lpCmdLine:LPSTR, nCmdShow:DWORD
		LOCAL	wcex:WNDCLASSEX
		LOCAL	msg:MSG

		mov		wcex.cbSize, sizeof WNDCLASSEX
		mov		wcex.style, CS_HREDRAW or CS_VREDRAW
		mov		wcex.lpfnWndProc, offset WndProc
		mov		wcex.cbClsExtra, 0
		mov		wcex.cbWndExtra, 0
		push	hInstance
		pop		wcex.hInstance
		invoke	LoadIcon, hInstance, IDI_APPLICATION
		mov		wcex.hIcon, eax
		mov		wcex.hIconSm, eax
		invoke	LoadCursor, NULL, IDC_ARROW
		mov		wcex.hCursor, eax
		mov		wcex.hbrBackground, COLOR_WINDOW+1
		mov		wcex.lpszMenuName, NULL
		mov		wcex.lpszClassName, offset szWindowClass
		
		invoke	RegisterClassEx, addr wcex              ;注册窗口
		invoke	CreateWindowEx, NULL,\					;创建窗口
			addr szWindowClass, \
			addr szWindowClass, \
			WS_CAPTION or WS_SYSMENU or WS_MINIMIZEBOX, \
			CW_USEDEFAULT, \
			CW_USEDEFAULT, \
			242, \
			420, \
			NULL, \
			NULL, \
			hInstance, \
			NULL
		mov		hWnd, eax

		invoke	ShowWindow, hWnd, nCmdShow				;显示窗口
		invoke	UpdateWindow, hWnd						;更新窗口
		.while	TRUE                                    ;处理消息
				invoke GetMessage, addr msg, NULL, 0, 0	;获取消息
				.if eax == 0
					.break
				.endif
				invoke TranslateMessage, addr msg		;转换消息
				invoke DispatchMessage, addr msg		;分发消息
		.endw

		mov		eax, msg.wParam
		ret
WinMain endp

WndProc proc hWin:HWND, msg:UINT, wParam:WPARAM, lParam:LPARAM
		LOCAL	paintStruct:PAINTSTRUCT
		LOCAL	stRect:RECT
		LOCAL	hdc:HDC
		.if	msg == WM_DESTROY					        ;关闭窗口
				invoke  DestroyWindow, WinMain
				invoke	PostQuitMessage, 0

		.elseif msg == WM_PAINT
				invoke  BeginPaint, hWin, addr paintStruct
				mov     hWin, eax
				invoke  GetClientRect, hWin, addr stRect
				invoke  EndPaint, hWin, addr paintStruct

		.elseif msg == WM_CREATE						;创建按钮
				invoke	CreateWindowEx, NULL, addr szStatic, addr szInitExpr, WS_VISIBLE or WS_CHILD or WS_BORDER or ES_MULTILINE or ES_READONLY, \
					5, 5, 215, 40, hWin, NULL, NULL, NULL
				mov		hwndStatic, eax
				invoke	CreateWindowEx, NULL, addr szStatic, addr szInitResult, WS_VISIBLE or WS_CHILD or WS_BORDER or ES_MULTILINE or ES_READONLY, \
					5, 55, 215, 40, hWin, NULL, NULL, NULL
				mov		hwndResult, eax
				mov		esi, 0
				invoke	CreateWindowEx, NULL, addr szButton, addr szDoubleZero, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					5, 335, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szZero, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					60, 335, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szPoint, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					115, 335, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szEqual, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					170, 335, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szOne, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					5, 290, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szTwo, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					60, 290, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szThree, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					115, 290, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szAdd, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					170, 290, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szFour, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					5, 245, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szFive, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					60, 245, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szSix, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					115, 245, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szSub, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					170, 245, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szSeven, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					5, 200, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szEight, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					60, 200, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szNine, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					115, 200, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szMult, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					170, 200, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szSin, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					5, 155, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szCos, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					60, 155, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szTan, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					115, 155, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szDiv, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					170, 155, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szLeftBracket, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					5, 110, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szRightBracket, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					60, 110, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szClear, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					115, 110, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax
				inc		esi
				invoke	CreateWindowEx, NULL, addr szButton, addr szBack, WS_TABSTOP or WS_VISIBLE or WS_CHILD or BS_DEFPUSHBUTTON, \
					170, 110, 50, 40, hWin, NULL, NULL, NULL
				mov		hwndButton[esi*4], eax

		.elseif msg == WM_COMMAND					;操作获取
				mov		eax,lParam
				.if eax == hwndButton[0*4]				;00
						invoke	AddString, addr szDoubleZero
				.elseif eax == hwndButton[1*4]			;0
						invoke	AddString, addr szZero
				.elseif eax == hwndButton[2*4]			;.
						invoke	AddString, addr szPoint
				.elseif eax == hwndButton[3*4]			;=
						invoke	Calculate
				.elseif eax == hwndButton[4*4]			;1
						invoke	AddString, addr szOne
				.elseif eax == hwndButton[5*4]			;2
						invoke	AddString, addr szTwo
				.elseif eax == hwndButton[6*4]			;3
						invoke	AddString, addr szThree
				.elseif eax == hwndButton[7*4]			;+
						invoke	AddString, addr szAdd
				.elseif eax == hwndButton[8*4]			;4
						invoke	AddString, addr szFour
				.elseif eax == hwndButton[9*4]			;5
						invoke	AddString, addr szFive
				.elseif eax == hwndButton[10*4]			;6
						invoke	AddString, addr szSix
				.elseif eax == hwndButton[11*4]			;-
						invoke	AddString, addr szSub
				.elseif eax == hwndButton[12*4]			;7
						invoke	AddString, addr szSeven
				.elseif eax == hwndButton[13*4]			;8
						invoke	AddString, addr szEight
				.elseif eax == hwndButton[14*4]			;9
						invoke	AddString, addr szNine
				.elseif eax == hwndButton[15*4]			;*
						invoke	AddString, addr szMult
				.elseif eax == hwndButton[16*4]			;sin
						invoke	AddString, addr szSin
				.elseif eax == hwndButton[17*4]			;cos
						invoke	AddString, addr szCos
				.elseif eax == hwndButton[18*4]			;tan
						invoke	AddString, addr szTan
				.elseif eax == hwndButton[19*4]			;/
						invoke	AddString, addr szDiv
				.elseif eax == hwndButton[20*4]			;(
						invoke	AddString, addr szLeftBracket
				.elseif eax == hwndButton[21*4]			;)
						invoke	AddString, addr szRightBracket
				.elseif eax == hwndButton[22*4]			;CE
						invoke	Clear
				.elseif eax == hwndButton[23*4]			;BS
						invoke	BackSpace
				.endif

		.else
				invoke	DefWindowProc, hWin, msg, wParam, lParam
				ret
		.endif
		xor		eax, eax
		ret
WndProc endp

AddString proc string:ptr byte
		invoke	strcat, addr expr, string					;当前输入的表达式
		invoke	SetWindowTextA, hwndStatic, addr expr
		invoke	SetWindowTextA, hwndResult, addr resultStr
		
		mov		ebx, string
		movzx	eax, byte ptr[ebx]

		.if	eax == "s"										;当前按下的按键值，将sin、cos、tan替换为对应助记符
				invoke	strcpy, addr strTemp, addr szSinCacl
		.elseif eax == "c"
				invoke	strcpy, addr strTemp, addr szCosCacl
		.elseif eax == "t"
				invoke	strcpy, addr strTemp, addr szTanCacl
		.else
				invoke	strcpy, addr strTemp, string
		.endif

		invoke	strcat, addr exprToCalc, addr strTemp
		
		ret
AddString endp

Clear proc
		invoke	memset, addr expr, 0, sizeof expr
		invoke	memset, addr exprToCalc, 0, sizeof exprToCalc
		invoke	SetWindowTextA, hwndStatic, addr expr
		invoke	SetWindowTextA, hwndResult, addr resultStr
		ret
Clear endp

BackSpace proc
		invoke	strlen, addr exprToCalc
		mov		ebx, eax
		invoke	strlen, addr expr
		
		.if ebx == 0
				ret
		.endif

		dec		ebx
		dec		eax
		mov		exprToCalc[ebx], 0
		mov		expr[eax], 0
		.if exprToCalc[ebx] == "#"
				mov		expr[eax-1], 0
				mov		expr[eax-2], 0
		.elseif exprToCalc[ebx] == "$"
				mov		expr[eax-1], 0
				mov		expr[eax-2], 0
		.elseif exprToCalc[ebx] == "&"
				mov		expr[eax-1], 0
				mov		expr[eax-2], 0
		.endif

		invoke	SetWindowTextA, hwndStatic, addr expr
		invoke	SetWindowTextA, hwndResult, addr resultStr
		ret
BackSpace endp

CalBinary proc numLeft1:real8, numRight1:real8, op:byte
		finit
		.if	op == "/"		;判断除数是否为0
				fld		epsilon
				fld		numRight1
				fabs
				fcomi	st(0), st(1)
				ja		NormalDivide
				mov		state, -1
				ret
NormalDivide:
		.endif

		fld		numRight1
		fld		numLeft1
		.if op == "+"
				fadd	st(0), st(1)
		.elseif op == "-"
				fsub	st(0), st(1)
		.elseif op == "*"
				fmul	st(0), st(1)
		.elseif op == "/"
				fdiv	st(0), st(1)
		.endif
		ret
CalBinary endp

Cal proc op:byte

		.if op == "#" || op == "&" || op == "$"
				mov		eax, 1
		.else
				mov		eax, 0
		.endif

		.if	eax															;三角运算
				dec		stackNumTop
				mov		edx, stackNumTop
				finit
				fld		stackNum[edx*8]
				
				.if op == "#"
						fsin
				.elseif op == "$"
						fcos
				.elseif op == "&"
						fptan
						fmul
				.endif

				fstp	stackNum[edx*8]
				inc		stackNumTop
		
		.else															;双目运算
				dec		stackNumTop
				mov		edx, stackNumTop
				finit
				fld		stackNum[edx*8]
				fstp	numRight
				dec		stackNumTop
				mov		edx, stackNumTop
				fld		stackNum[edx*8]
				fstp	numLeft
				invoke	CalBinary, numLeft, numRight, op
				fstp	stackNum[edx*8]
				inc		stackNumTop
		.endif
		ret
Cal endp

Calculate proc
		invoke	strlen, addr exprToCalc
		mov		len, eax
		mov		state, -2
		finit
		fld		floatZero
		fst		numNow
		fst		numLeft
		fst		numRight
		fst		result
		fld		floatOne
		fst		afterPoint
		mov		point, 0
		mov		stackNumTop, 0
		mov		stackOpTop, 0
		mov		ecx, 0
		.while	ecx < len
				mov		bl, exprToCalc[ecx]

				.if bl >= "0" && bl <= "9"											;判断数字，结果存入eax
						mov		eax, 1
				.else
						mov		eax, 0
				.endif

				.if eax == 1														;当前是数字
						finit
						fld		numNow
						fmul	floatTen
						mov		eax, 0
						mov		al, exprToCalc[ecx]
						sub		eax, 30H
						mov		intNum, eax
						fild	intNum
						fadd
						fstp	numNow
						.if point
								fld		afterPoint
								fmul	floatTen
								fstp	afterPoint
						.endif
						mov		state, 0
				.elseif	bl == "."													;当前是小数点
						.if point == 1
								mov		state, -1
								.break
						.endif
						mov		point, 1
						mov		state, 0
				.else																;当前是运算符
						mov		point, 0
						mov		edx, stackOpTop

						.if bl == "#" || bl == "&" || bl == "$"						;三角运算
								mov		eax, 1
						.else
								mov		eax, 0
						.endif

						.if	eax == 1
								.if state == 0 || state == 4						;符号前非数字
										mov		state, -1
										.break
								.endif
								mov		stackOp[edx], bl							;入符号栈
								inc		stackOpTop
								mov		state, 1
						.elseif	bl == "("
								.if state == 0 || state == 4						;符号前非数字
										mov		state, -1
										.break
								.endif
								mov		stackOp[edx], bl
								inc		stackOpTop
								mov		state, 3
						.elseif bl == ")"
								.if	(state != 0 && state != 4) || stackOpTop == 0	;符号前必须为数字
										mov		state, -1
										.break
								.endif

								.if	state == 0
										finit
										fld		numNow
										fdiv	afterPoint
										mov		edx, stackNumTop
										inc		stackNumTop
										fstp	stackNum[edx*8]
								.endif

								.while TRUE
										dec		stackOpTop
										mov		edx, stackOpTop
										mov		dl, stackOp[edx]
										mov		opNow, dl

										.if	opNow == "("
												.break
										.endif

										.if	edx == 0									;无左括号
												mov		state, -1
												.break
										.endif

										invoke	Cal, opNow

										.if	state == -1
												.break
										.endif
								.endw

								.if state == -1
										.break
								.endif

								mov		state, 4
						.else															;+ - * /运算符
								.if	state != 0 && state != 4	;符号前必须为数字
										mov		state, -1
										.break
								.endif

								.if	state == 0
										finit
										fld		numNow
										fdiv	afterPoint
										mov		edx, stackNumTop
										inc		stackNumTop
										fstp	stackNum[edx*8]
								.endif

								mov		bl, exprToCalc[ecx]
								.while	stackOpTop
										mov		edx, stackOpTop
										mov		dl, stackOp[edx-1]
										mov		opNow, dl

										.if	opNow == "("								;根据前后符号优先级设置eax
												mov		eax, 1
										.elseif (opNow == "+" || opNow == "-") && (bl == "*" || bl == "/")
												mov		eax, 1
										.else
												mov		eax, 0
										.endif

										.if	eax == 0											;当前符号优先级高
												invoke	Cal, opNow
												dec		stackOpTop
										.else
											.break
										.endif
	
								.endw

								mov		bl, exprToCalc[ecx]
								mov		edx, stackOpTop
								mov		stackOp[edx], bl
								inc		stackOpTop
								mov		state, 2
						.endif

						finit
						fld		floatZero
						fstp	numNow
						fld		floatOne
						fstp	afterPoint
				.endif

				inc		ecx
		.endw

		.if	state == 0 || state == 4
				.if state == 0
						finit
						fld		numNow
						fdiv	afterPoint
						mov		edx, stackNumTop
						inc		stackNumTop
						fstp	stackNum[edx*8]
				.endif
				.while stackOpTop
						dec		stackOpTop
						mov		edx, stackOpTop
						mov		dl, stackOp[edx]
						mov		opNow, dl
						.if	opNow == "("					;左括号已处理完毕
								mov		state, -1
								.break
						.endif

						invoke	Cal, opNow
						.if	state == -1
								.break
						.endif
				.endw

				.if	stackNumTop == 1
						finit
						fld		stackNum
						fstp	result
				.else
						mov		state, -1
				.endif
		.elseif state == -2
				finit
				fld		floatZero
				fstp	result
		.else
				mov		state, -1
		.endif

		.if	state == -1										;出错
				invoke	strcpy,addr resultStr, addr szError
		.else
				invoke	sprintf, addr resultStr, addr floatFormat, result
		.endif

		invoke	SetWindowTextA, hwndResult, addr resultStr
		invoke	memset, addr expr, 0, sizeof expr
		invoke	memset, addr exprToCalc, 0, sizeof exprToCalc
		invoke	memset, addr resultStr, 0, sizeof resultStr
		ret
Calculate endp

start:
		invoke	GetModuleHandle,NULL
		mov		hInst,eax
		invoke	WinMain, hInst, NULL, NULL, SW_SHOWDEFAULT
		invoke	ExitProcess, eax
end start