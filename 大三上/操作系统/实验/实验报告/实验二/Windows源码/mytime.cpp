#include <iostream>
#include <cstring>
#include <windows.h>
#include <typeinfo>
using namespace std;


int main(int argc, char * argv[]){
	
	// 创建变量 
	SYSTEMTIME time_start, time_end;
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	
	// 初始化 
	memset(&si, 0, sizeof(STARTUPINFO));
	si.cb = sizeof(STARTUPINFO);	//应用程序必须将cb初始化为sizeof(STARTUPINFO)
	si.dwFlags = STARTF_USESHOWWINDOW;	//窗口标志
	si.wShowWindow = SW_SHOW;
	
	if(argc <= 1){
		exit(0);
	}
	
	// 创建进程 
	char subprocess[100] = "\0";
	strcat(subprocess, argv[1]);
	if(argc == 3){
		strcat(subprocess, " ");
		strcat(subprocess, argv[2]);
	}
	if(!CreateProcess(NULL, (LPSTR)subprocess, NULL, NULL, FALSE,
		CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi)){
		cout<<"Create process failed."<<endl;
		exit(1);
	}
	else{
		// 记录进程开始时间 
		GetSystemTime(&time_start);
		cout<<"Create process succeed."<<endl;
		printf("Process start time: %dh %dm %ds %dms.\n",
			time_start.wHour, time_start.wMinute, time_start.wSecond, time_start.wMilliseconds);	
	}
	
	// 进程同步，等待进程关闭 
	WaitForSingleObject(pi.hProcess, INFINITE);
	
	// 记录进程结束时间 
	GetSystemTime(&time_end);
	printf("Process end time: %dh %dm %ds %dms.\n",
			time_end.wHour, time_end.wMinute, time_end.wSecond, time_end.wMilliseconds);	
	
	// 显示运行时间 
	int hour = time_end.wHour - time_start.wHour,
		minute = time_end.wMinute - time_start.wMinute,
		second = time_end.wSecond - time_start.wSecond,
		millisecond = time_end.wMilliseconds - time_start.wMilliseconds;
	if(millisecond < 0){
		millisecond += 1000;
		second -= 1;
	}
	if(second < 0){
		second += 60;
		minute -= 1;
	}
	if(minute < 0){
		minute += 60;
		hour -= 1;
	}
	if(hour < 0){
		hour += 24;
	}
	printf("This program running time: %dh %dm %ds %dms.\n",
		hour, minute, second, millisecond); 
	
	return 0;
}

