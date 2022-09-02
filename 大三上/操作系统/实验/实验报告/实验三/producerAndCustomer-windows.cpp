#include <iostream>
#include <cstring>
#include <windows.h>
#include <time.h>
#include <unistd.h>

#define TEMPFILE "myshm" 

using namespace std;

struct myBuffer{
	char str[4];
	int head;
	int tail;
};

struct shareBuffer{
	struct myBuffer str;
	HANDLE full;
	HANDLE empty;
	HANDLE mutex;
};

HANDLE subProcessHandle[6];


HANDLE CreateShareFile(){
	HANDLE shmid = CreateFileMapping(INVALID_HANDLE_VALUE, NULL, PAGE_READWRITE, 0, sizeof(struct shareBuffer), TEMPFILE);
	if(shmid == NULL){
		printf("创建共享文件失败\n");
		return NULL;
	}
	LPVOID mapShmid = MapViewOfFile(shmid, FILE_MAP_ALL_ACCESS, 0, 0, 0);
	if(mapShmid == NULL){
		printf("临时文件映射到主进程失败");
		exit(1);
	}
	ZeroMemory(mapShmid, sizeof(struct shareBuffer));
	UnmapViewOfFile(mapShmid);
	return shmid;
}

void startClone(int id){
	char szFileName[MAX_PATH];
	char szCmdLine[MAX_PATH];
	STARTUPINFO si;
	PROCESS_INFORMATION pi;
	
	memset(&si, 0, sizeof(STARTUPINFO));
	si.cb = sizeof(STARTUPINFO);
	
	GetModuleFileName(NULL, szFileName, MAX_PATH);
	
	sprintf(szCmdLine, "\"%s\" %d", szFileName, id);
	
	CreateProcess(szFileName, szCmdLine, NULL, NULL, FALSE, 0, NULL, NULL, &si, &pi);
	subProcessHandle[id] = pi.hProcess;
	return;
}

int getRandomTime(int id, int type){
	srand((unsigned)(id + time(NULL)));
	return (rand() % 4 + type * 2 - 1) * 1000;
}

char getRandomLetter(){
	srand((unsigned)time(NULL));
	return (char)(rand() % 26 + 'A');
}


int main(int argc, char *argv[]){
	
	int curProcessId = 0;
	SYSTEMTIME now;
	
	if(argc > 1){
		curProcessId = (int)(argv[1][0] - '0');
	}
	
//	cout<<curProcessId<<endl;
	
	if(curProcessId == 0){
		// 创建临时文件
		HANDLE shmid = CreateShareFile();
		
		// 打开临时文件
		HANDLE openShmid = OpenFileMapping(FILE_MAP_ALL_ACCESS, false, TEMPFILE);
		if(openShmid == NULL){
			printf("打开文件映射对象失败\n");
			exit(1);
		}
		
		// 将临时文件映射到主进程 
		LPVOID mapShmid = MapViewOfFile(openShmid, FILE_MAP_ALL_ACCESS, 0, 0, 0);
		if(mapShmid == NULL){
			printf("临时文件映射到主进程失败");
			exit(1);
		}
		struct shareBuffer* sharebuffer = (struct shareBuffer*)mapShmid;
		sharebuffer->str.head = sharebuffer->str.tail = 0;
		sharebuffer->empty = CreateSemaphore(NULL, 3, 3, "empty");
		sharebuffer->full = CreateSemaphore(NULL, 0, 3, "full");
		sharebuffer->mutex = CreateSemaphore(NULL, 1, 1, "mutex");
		
		UnmapViewOfFile(mapShmid);
		CloseHandle(openShmid);
		
		for(int i = 1; i <= 5; i++){
			startClone(i);
		}
		
		for(int i = 1; i <= 5; i++){
			WaitForSingleObject(subProcessHandle[i], INFINITE);
			
		}
		for(int i = 1; i <= 5; i++){
			CloseHandle(subProcessHandle[i]);
		}
		
		CloseHandle(shmid);
		
	}
	else if(curProcessId == 1 || curProcessId == 2){    // 生产者进程 
		// 打开临时文件
		HANDLE openShmid = OpenFileMapping(FILE_MAP_ALL_ACCESS, false, TEMPFILE);
		if(openShmid == NULL){
			printf("打开文件映射对象失败\n");
			exit(1);
		}
		
		// 将临时文件映射到主进程 
		LPVOID mapShmid = MapViewOfFile(openShmid, FILE_MAP_ALL_ACCESS, 0, 0, 0);
		if(mapShmid == NULL){
			printf("临时文件映射到主进程失败");
			exit(1);
		}
		struct shareBuffer* sharebuffer = (struct shareBuffer*)mapShmid;
		sharebuffer->empty = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, "empty");
		sharebuffer->full = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, "full");
		sharebuffer->mutex = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, "mutex");
		
		for(int i = 0; i < 6; i++){
			
			Sleep(getRandomTime(curProcessId, 1));
			// P(empty), P(mutex)
//			printf("生产者%d：P(empty)\n", curProcessId);
			WaitForSingleObject(sharebuffer->empty, INFINITE);
//			printf("生产者%d：P(mutex)\n", curProcessId);
			WaitForSingleObject(sharebuffer->mutex, INFINITE);
			
			
			GetSystemTime(&now);
			
			//生产者进程向共享区放入一个随机字母 
			sharebuffer->str.str[sharebuffer->str.tail] = getRandomLetter();
			sharebuffer->str.tail = (sharebuffer->str.tail + 1) % 4;
			
			fflush(stdout);
			printf("生产者进程%d：%04d-%02d-%02d %02d:%02d:%02d。缓冲区内容：", 
				curProcessId, now.wYear, now.wMonth, now.wDay, 
				now.wHour, now.wMinute, now.wSecond);
			
			int temp = sharebuffer->str.head;
			while(temp != sharebuffer->str.tail){
				printf("%c", sharebuffer->str.str[temp]);
				temp = (temp + 1) % 4;
			}
			printf("\n");
			fflush(stdout);
			
			
//			printf("生产者%d：V(mutex)\n", curProcessId);
			ReleaseSemaphore(sharebuffer->mutex, 1, NULL);
//			printf("生产者%d：V(full)\n", curProcessId);
			ReleaseSemaphore(sharebuffer->full, 1, NULL);
		}
		
		CloseHandle(sharebuffer->empty);
		CloseHandle(sharebuffer->full);
		CloseHandle(sharebuffer->mutex);
		UnmapViewOfFile(mapShmid);
		CloseHandle(openShmid);
	}
	else if(curProcessId >= 3 && curProcessId <= 5){
		// 打开临时文件
		HANDLE openShmid = OpenFileMapping(FILE_MAP_ALL_ACCESS, false, TEMPFILE);
		if(openShmid == NULL){
			printf("打开文件映射对象失败\n");
			exit(1);
		}
		
		// 将临时文件映射到主进程 
		LPVOID mapShmid = MapViewOfFile(openShmid, FILE_MAP_ALL_ACCESS, 0, 0, 0);
		if(mapShmid == NULL){
			printf("临时文件映射到主进程失败");
			exit(1);
		}
		struct shareBuffer* sharebuffer = (struct shareBuffer*)mapShmid;
		sharebuffer->empty = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, "empty");
		sharebuffer->full = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, "full");
		sharebuffer->mutex = OpenSemaphore(SEMAPHORE_ALL_ACCESS, FALSE, "mutex");
		
		for(int i = 0; i < 4; i++){
			
			Sleep(getRandomTime(curProcessId, 2));
			
			// P(full), P(mutex)
//			printf("消费者%d：P(full)\n", curProcessId);
			WaitForSingleObject(sharebuffer->full, INFINITE);
//			printf("消费者%d：P(mutex)\n", curProcessId);
			WaitForSingleObject(sharebuffer->mutex, INFINITE);
			
			
			
			GetSystemTime(&now);
			
			//消费者进程从共享区读出一个字母 
			sharebuffer->str.head = (sharebuffer->str.head + 1) % 4;
			
			fflush(stdout);
			printf("消费者进程%d：%04d-%02d-%02d %02d:%02d:%02d。缓冲区内容：", 
				curProcessId, now.wYear, now.wMonth, now.wDay, 
				now.wHour, now.wMinute, now.wSecond);
			
			int temp = sharebuffer->str.head;
			while(temp != sharebuffer->str.tail){
				printf("%c", sharebuffer->str.str[temp]);
				temp = (temp + 1) % 4;
			}
			printf("\n");
			fflush(stdout);
			
			
//			printf("消费者%d：V(mutex)\n", curProcessId);
			ReleaseSemaphore(sharebuffer->mutex, 1, NULL);
//			printf("消费者%d：V(empty)\n", curProcessId);
			ReleaseSemaphore(sharebuffer->empty, 1, NULL);

		}
		
		CloseHandle(sharebuffer->empty);
		CloseHandle(sharebuffer->full);
		CloseHandle(sharebuffer->mutex);
		UnmapViewOfFile(mapShmid);
		CloseHandle(openShmid);
	}
//	CloseHandle(shmid);
	return 0;
}











