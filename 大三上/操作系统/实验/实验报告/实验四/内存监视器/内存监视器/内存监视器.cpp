#include <iostream>
#include <cstring>
#include <Windows.h>
#include <time.h>
#include <shlwapi.h>
#include <iomanip>
#include <tlhelp32.h>
#include <psapi.h>
#pragma comment(lib, "psapi.lib")
#pragma comment(lib, "Shlwapi.lib")
using namespace std;


// 获取系统内存信息 
void CheckSystemMemory() {
	MEMORYSTATUSEX systemMemoryInfo;
	systemMemoryInfo.dwLength = sizeof(MEMORYSTATUSEX);
	GlobalMemoryStatusEx(&systemMemoryInfo);

	cout << "系统内存信息: " << endl;
	cout << "物理内存使用率: " << systemMemoryInfo.dwMemoryLoad << endl;
	cout << "物理内存总大小: " << (float)systemMemoryInfo.ullTotalPhys / 1024 / 1024 / 1024 << "GB" << endl;
	cout << "物理内存可用大小: " << (float)systemMemoryInfo.ullAvailPhys / 1024 / 1024 / 1024 << "GB" << endl;
	cout << "总交换文件大小: " << (float)systemMemoryInfo.ullTotalPageFile / 1024 / 1024 / 1024 << "GB" << endl;
	cout << "可用交换文件大小: " << (float)systemMemoryInfo.ullAvailPageFile / 1024 / 1024 / 1024 << "GB" << endl;
	cout << "总虚拟内存大小: " << (float)systemMemoryInfo.ullTotalVirtual / 1024 / 1024 / 1024 << "GB" << endl;
	cout << "可用虚拟内存大小: " << (float)systemMemoryInfo.ullAvailVirtual / 1024 / 1024 / 1024 << "GB" << endl;
	cout << "扩展内存大小: " << (float)systemMemoryInfo.ullAvailExtendedVirtual << endl;
	cout << endl;

	return;
}

// 获取系统信息
void CheckSystemInfo() {
	SYSTEM_INFO si;
	ZeroMemory(&si, sizeof(SYSTEM_INFO));
	GetSystemInfo(&si);

	cout << "系统信息：" << endl;
	cout << "页面大小：" << (int)si.dwPageSize / 1024 << "KB" << endl;
	cout << "进程私有地址空间最小内存地址：" << si.lpMinimumApplicationAddress << endl;
	cout << "进程私有地址空间最大内存地址：" << si.lpMaximumApplicationAddress << endl;
	cout << "分配粒度：" << si.dwAllocationGranularity / 1024 << "KB" << endl;
	cout << endl;

	return;
}

// 获取系统性能信息 
void CheckSystemPerformance() {
	PERFORMANCE_INFORMATION pi;
	pi.cb = sizeof(PERFORMANCE_INFORMATION);
	GetPerformanceInfo(&pi, sizeof(pi));

	cout << "系统性能：" << endl;
	//cout << "结构体数据大小：" << pi.cb << endl;
	cout << "物理内存总大小：" << pi.PhysicalTotal << "页" << endl;
	cout << "物理内存可用大小：" << pi.PhysicalAvailable << "页" << endl;
	cout << "系统缓存：" << pi.SystemCache << "页" << endl;
	cout << "页大小：" << (int)pi.PageSize / 1024 << "KB" << endl;
	cout << "当前打开句柄数：" << pi.HandleCount << endl;
	cout << "当前进程数：" << pi.ProcessCount << endl;
	cout << "当前线程数：" << pi.ThreadCount << endl;
	cout << endl;

	return;
}

// 获取进程信息 
void CheckProcessInfo() {
	PROCESSENTRY32 pe;
	pe.dwSize = sizeof(pe);
	HANDLE hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	cout << "获取各个进程信息：" << endl;
	bool process = Process32First(hProcessSnap, &pe);
	while (process) {
		HANDLE hp = OpenProcess(PROCESS_ALL_ACCESS, false, pe.th32ProcessID);
		PROCESS_MEMORY_COUNTERS pmc;
		ZeroMemory(&pmc, sizeof(pmc));

		if (GetProcessMemoryInfo(hp, &pmc, sizeof(pmc)) == true) {
			cout << "进程id: ";
			wcout << pe.th32ProcessID;
			cout << ", 进程名称: ";
			printf("%ls", pe.szExeFile);
			//wcout << pe.szExeFile;
			cout << ", 已用内存大小: " << (float)pmc.WorkingSetSize / 1024 / 1024 << "MB" << endl;
		}
		process = Process32Next(hProcessSnap, &pe);
	}
	return;
}

// 查看进程虚拟地址空间
void WalkVM(HANDLE hp) {
	SYSTEM_INFO si;
	ZeroMemory(&si, sizeof(si));
	GetSystemInfo(&si);

	MEMORY_BASIC_INFORMATION mbi;
	ZeroMemory(&mbi, sizeof(mbi));

	// 从最小地址到最大地址，按块遍历整个应用程序地址空间
	LPCVOID pBlock = (LPVOID)si.lpMinimumApplicationAddress;
	while (pBlock < si.lpMaximumApplicationAddress) {
		// 获得虚拟内存块的信息
		VirtualQueryEx(hp, pBlock, &mbi, sizeof(mbi));
		// 计算块的结尾及其长度
		LPCVOID pEnd = (PBYTE)pBlock + mbi.RegionSize;
		// 将数字转换成字符串
		// TCHAR szSize[MAX_PATH];
		// StrFormatByteSize(mbi.RegionSize, szSize, MAX_PATH);

		//显示块地址和长度
		//if ((float)mbi.RegionSize > 1024 * 1024) {
		//	cout << "当前块长度" << (float)mbi.RegionSize / 1024 / 1024 << "MB, ";
		//}
		//else {
		//	cout << "当前块长度" << (float)mbi.RegionSize / 1024 << "KB, ";
		//}

		// 显示块地址范围
		cout << "当前块范围" << pBlock << " ~ " << pEnd;

		// cout.fill('0');
		// cout << hex << setw(8) << pBlock << "-" << hex << setw(8) << pEnd << (strlen(szSize) == 7 ? "(" : "(") << szSize << ")";

		//显示块的状态
		switch (mbi.State) {
		case MEM_COMMIT:
			printf(", 被提交");
			break;
		case MEM_FREE:
			printf(", 空闲  ");
			break;
		case MEM_RESERVE:
			printf(", 被保留");
			break;
		}

		//显示保护
		if (mbi.Protect == 0 && mbi.State != MEM_FREE) {
			mbi.Protect = PAGE_READONLY;
		}
		//		ShowProtection(mbi.Protect);

		//显示类型
		switch (mbi.Type) {
		case MEM_IMAGE:
			printf(", Image");
			break;
		case MEM_MAPPED:
			printf(", Mapped");
			break;
		case MEM_PRIVATE:
			printf(", Private");
			break;
		}

		//检验可执行的映像
		TCHAR szFilename[MAX_PATH];
		ZeroMemory(&szFilename, sizeof(szFilename));
		GetModuleFileName((HMODULE)pBlock, szFilename, MAX_PATH);
		//除去路径并显示
		PathStripPath(szFilename);
		printf(", Module:%ls", szFilename);
		cout << endl;

		//移动块指针以获得下一个块
		pBlock = pEnd;
	}
	return;
}

int main() {

	CheckSystemMemory();

	CheckSystemInfo();

	CheckSystemPerformance();

	CheckProcessInfo();

	cout << endl;
	cout << "查看进程虚拟地址空间: " << endl;
	cout << "输入要查询的进程id" << endl;
	int id;
	while (cin >> id) {
		HANDLE hp = OpenProcess(PROCESS_ALL_ACCESS, false, id);
		WalkVM(hp);
		cout << endl;
		cout << "查看进程虚拟地址空间: " << endl;
		cout << "输入要查询的进程id" << endl;
	}
}
