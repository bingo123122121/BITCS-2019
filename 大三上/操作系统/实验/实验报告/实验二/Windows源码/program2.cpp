#include <iostream>
#include <cstring>
#include <windows.h>
#include <time.h>
#include <unistd.h>
using namespace std;


int main(int argc, char * argv[]){
	if(argc <= 1){
		cout << "This is program2." << endl;
		exit(0);
	}
	time_t start;
	start = time(NULL);
	cout << argv[1] << endl;
	int t = 0;
	for(int i = 0; i < strlen(argv[1]); i++){
		t = t*10 + argv[1][i]-'0';
	}
	cout<<t<<endl;
	Sleep(t*1000 - time(NULL) + start);
	return 0;
}

