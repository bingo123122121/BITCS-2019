#include <iostream>
#include <cstring>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include<sys/time.h>
#include <fcntl.h>
#include <dirent.h>
#include <utime.h>

using namespace std;

void copyFile(char * from, char * to){
    int fp1 = open(from, O_RDONLY);
    if(fp1 != -1){
        struct stat fileState;
        struct utimbuf fileTime;
        struct timeval  tv[2];
        char buffer[5000];
        int bits;

        lstat(from, &fileState);
        cout << fileState.st_mode << " " << from << endl;

        if(S_ISLNK(fileState.st_mode)){
            cout << "link:" << from << endl;
            char absPath[5000];
            char * ret = realpath(from, absPath);
            if(ret != NULL){
                symlink(absPath, to);

                tv[0].tv_sec = fileState.st_atime;
                tv[1].tv_sec = fileState.st_mtime;
                lutimes(to, tv);
            }
        }
        else{
            int fp2 = creat(to, fileState.st_mode);
            if(fp2 != -1){
                while((bits = read(fp1, buffer, 5000)) > 0){
                    write(fp2, buffer, bits);
                }
                fileTime.actime = fileState.st_atime;
                fileTime.modtime = fileState.st_mtime;
                utime(to, &fileTime);
            }
            close(fp2);
        }
        close(fp1);
    }
}

void mycp(char * from, char * to){
    char source[5000];
    char target[5000];
    struct stat fileState;
    struct utimbuf fileTime;
    struct dirent * entry;
    DIR * dir;

    strcpy(source, from);
    strcpy(target, to);

    dir = opendir(source);
    while(entry = readdir(dir)){
        if(strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0){
            continue;
        }
        if(entry->d_type == DT_DIR){
            strcat(source, "/");
            strcat(source, entry->d_name);
            strcat(target, "/");
            strcat(target, entry->d_name);

            stat(source, &fileState);

            mkdir(target, fileState.st_mode);

            mycp(source, target);

            fileTime.actime = fileState.st_atime;
            fileTime.modtime = fileState.st_mtime;
            utime(target, &fileTime);

            strcpy(source, from);
            strcpy(target, to);
        }
        else{
            strcat(source, "/");
            strcat(source, entry->d_name);
            strcat(target, "/");
            strcat(target, entry->d_name);

            copyFile(source, target);

            strcpy(source, from);
            strcpy(target, to);
        }
    }
    closedir(dir);
}


int main(int argc, char * argv[]){
    struct stat fileState;
    struct utimbuf fileTime;

    DIR * dir;

    if(argc == 3){
        dir = opendir(argv[1]);
        if(dir == NULL){
            cout << "源文件无法打开" << endl;
            return 0;
        }
        dir = opendir(argv[2]);
        if(dir == NULL){
            stat(argv[1], &fileState);
            mkdir(argv[2], fileState.st_mode);

            fileTime.actime = fileState.st_atime;
            fileTime.modtime = fileState.st_mtime;

            utime(argv[2], &fileTime);
        }
        mycp(argv[1], argv[2]);
    }
    closedir(dir);
    cout << "复制完成" << endl;
    return 0;
}