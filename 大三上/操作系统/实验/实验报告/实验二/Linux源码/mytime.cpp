#include <iostream>
#include <sys/time.h>    
#include <unistd.h>
#include <sys/types.h>
#include <wait.h>

using namespace std;

int main(int argc, char * argv[]){
    struct timeval start_time, end_time;
    pid_t pid;
    if((pid = fork()) < 0){
        cout<<"Create process failed."<<endl;
        exit(0);
    }
    else if(pid == 0){
        gettimeofday(&start_time, NULL);
        printf("Child process start time: %ds %dus.\n", start_time.tv_sec, start_time.tv_usec);
        if(argc == 2){
            execlp(argv[1], argv[1], 0);
        }
        else if(argc == 3){
            execlp(argv[1], argv[1], argv[2], 0);
        }
    }
    else{
        gettimeofday(&start_time, NULL);
        printf("Parent process start time: %ds %dus.\n", start_time.tv_sec, start_time.tv_usec);
        wait(NULL);
        gettimeofday(&end_time, NULL);
        printf("Parent process end time: %ds %dus.\n", end_time.tv_sec, end_time.tv_usec);
        int s = end_time.tv_sec - start_time.tv_sec, us = end_time.tv_usec - start_time.tv_usec;
        int ms = us/1000; us %= 1000;
        int minute = s/60; s %= 60;
        int hour = minute/60; minute %= 60;
        printf("程序运行时间: %dh %dm %ds %dms %dus.\n", hour, minute, s, ms, us);
    }
    return 0;
}









