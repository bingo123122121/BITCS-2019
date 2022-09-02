#include <iostream>
#include <cstring>
#include <Windows.h>
#include <time.h>
#include <shlwapi.h>
#include <iomanip>
#include <tlhelp32.h>
#include <psapi.h>
#pragma comment(lib, "psapi.lib")
#pragma comment(lib, "Shlwapi.lib");
using namespace std;

// 复制文件 
void CopyFile(char * from, char * to){
	WIN32_FIND_DATA lpFindFileData;
	
	HANDLE fileHandle = FindFirstFile(from, &lpFindFileData);
	HANDLE fpFrom = CreateFile(from, GENERIC_READ|GENERIC_WRITE, FILE_SHARE_READ, 
								NULL, OPEN_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
	HANDLE fpTo = CreateFile(to, GENERIC_READ|GENERIC_WRITE, FILE_SHARE_READ, 
								NULL, CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
	
	int size = lpFindFileData.nFileSizeLow - lpFindFileData.nFileSizeHigh;
	int * buffer = new int[size];
	DWORD wordBit;
	
	ReadFile(fpFrom, buffer, size, &wordBit, NULL);
	WriteFile(fpTo, buffer, size, &wordBit, NULL);
	
	if(SetFileAttributes(to, lpFindFileData.dwFileAttributes)){
		cout << "yes" << endl;
	}
	
	// 修改文件时间 
	SetFileTime(fpTo, &lpFindFileData.ftCreationTime, &lpFindFileData.ftLastAccessTime, &lpFindFileData.ftLastWriteTime);
	
	CloseHandle(fpFrom);
	CloseHandle(fpTo);
	CloseHandle(fileHandle);
	
	return;
}

void MyCp(char * from, char * to){
	WIN32_FIND_DATA lpFindFileData;
	char bufFrom[5000];
	char bufTo[5000];
	lstrcpy(bufFrom, from);
	lstrcpy(bufTo, to);
	lstrcat(bufFrom, "\\*.*");
	lstrcat(bufTo, "\\");
	
	HANDLE fileHandle = FindFirstFile(bufFrom, &lpFindFileData);
	
	if(fileHandle != INVALID_HANDLE_VALUE){
		while(FindNextFile(fileHandle, &lpFindFileData) != 0){
			if(lpFindFileData.dwFileAttributes == FILE_ATTRIBUTE_DIRECTORY){
				// 打开的不是"."、".."文件夹 
				if ((strcmp(lpFindFileData.cFileName, ".") != 0) && (strcmp(lpFindFileData.cFileName, "..") != 0)){
					memset(bufFrom, '0', sizeof(bufFrom));
					lstrcpy(bufFrom, from);
					lstrcat(bufFrom, "\\");
					lstrcat(bufFrom, lpFindFileData.cFileName);
					lstrcat(bufTo, lpFindFileData.cFileName);
					
					CreateDirectory(bufTo, NULL);
					
					cout << bufFrom << " " << bufTo << endl;
					MyCp(bufFrom, bufTo);
					
					// 修改文件夹时间 
					HANDLE fpTo = CreateFile(bufTo, GENERIC_READ|GENERIC_WRITE, FILE_SHARE_READ, 
												NULL, OPEN_EXISTING, FILE_FLAG_BACKUP_SEMANTICS, NULL);
					SetFileTime(fpTo, &lpFindFileData.ftCreationTime, &lpFindFileData.ftLastAccessTime, &lpFindFileData.ftLastWriteTime);
					
					lstrcpy(bufFrom, from);
					lstrcat(bufFrom, "\\");
					lstrcpy(bufTo, to);
					lstrcat(bufTo, "\\");
				}
			}
			else{
				memset(bufFrom, '0', sizeof(bufFrom));
				lstrcpy(bufFrom, from);
				lstrcat(bufFrom, "\\");
				lstrcat(bufFrom, lpFindFileData.cFileName);
				lstrcat(bufTo, lpFindFileData.cFileName);
				
				cout << bufFrom << " " << bufTo << endl;
				CopyFile(bufFrom, bufTo);
				
				lstrcpy(bufFrom, from);
				lstrcat(bufFrom, "\\");
				lstrcpy(bufTo, to);
				lstrcat(bufTo, "\\");
			}
		}
	}
	
	CloseHandle(fileHandle);
	
	return;
}

int main(int argc, char * argv[]){
	WIN32_FIND_DATA lpFindFileData;
	cout << argc << endl;
	if(argc == 3){
		if((FindFirstFileA(argv[1], &lpFindFileData)) == INVALID_HANDLE_VALUE){
			cout << "查找源文件路径失败" << endl;
			return 0;
		}
		if((FindFirstFileA(argv[2], &lpFindFileData)) == INVALID_HANDLE_VALUE){
			cout << "创建文件夹开始" << endl;
			CreateDirectory(argv[2], NULL);
			cout << "创建文件夹成功" << endl;
		}
		cout << "开始复制" << endl;
		MyCp(argv[1], argv[2]);
		cout << "复制成功" << endl; 
		
		// 修改文件夹时间 
		HANDLE fpTo = CreateFile(argv[2], GENERIC_READ|GENERIC_WRITE, FILE_SHARE_READ, 
									NULL, OPEN_EXISTING, FILE_FLAG_BACKUP_SEMANTICS, NULL);
		SetFileTime(fpTo, &lpFindFileData.ftCreationTime, &lpFindFileData.ftLastAccessTime, &lpFindFileData.ftLastWriteTime);
	}
	return 0;
} 

