#include <iostream>
#include <cstring>
#include <sys/time.h>
#include <unistd.h>

using namespace std;

int main(int argc, char * argv[]){
    time_t start_time = time(NULL);
    struct timeval start;
    gettimeofday(&start, NULL);
    int t = 0;
    if(argc <= 1){
        exit(0);
    }
    for(int i = 0; i < strlen(argv[1]); i++){
        t = t*10 + argv[1][i] - '0';
    }
    sleep(t - time(NULL) + start_time);
}