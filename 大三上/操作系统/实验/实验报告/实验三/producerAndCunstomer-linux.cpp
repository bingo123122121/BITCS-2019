#include <iostream>
#include <time.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <unistd.h>
#include <wait.h>

using namespace std;

struct myBuffer{
    char str[4];
    int head;
    int tail;
};

void P(int semid, int num){
    struct sembuf mutex;
    mutex.sem_num = num;
    mutex.sem_op = -1;
    mutex.sem_flg = 0;
    semop(semid, &mutex, 1);
}

void V(int semid, int num){
    struct sembuf mutex;
    mutex.sem_num = num;
    mutex.sem_op = 1;
    mutex.sem_flg = 0;
    semop(semid, &mutex, 1);
}

int getRandomNum(int id, int type){
    srand((unsigned int)(id + time(NULL)));
    return (rand()%4 + type*2 - 1);
}

char getRandomLetter(int id){
    srand((unsigned int)(id + time(NULL)));
    return (char)(rand()%26 + 'A');
}

int main(int argc, char* argv[]){
    int producer_num = 0, customer_num = 0;
    int semid, shmid;
    pid_t producer, customer;
    void * shm = NULL;
    struct myBuffer* mybuffer;
    time_t  timer;
    struct tm *now;

    // 创建共享内存
    shmid = shmget(IPC_PRIVATE, sizeof(struct myBuffer), 0600);
    if(shmid < 0){
        printf("创建共享区域失败\n");
        exit(1);
    }

    // 创建信号量，并初始化
    semid = semget(1234, 3, IPC_CREAT | 0660);    // 信号量依次为full、empty、mutex
    if(semid < 0){
        printf("创建信号量失败\n");
        exit(1);
    }
    semctl(semid, 0, SETVAL, 0);
    semctl(semid, 1, SETVAL, 3);
    semctl(semid, 2, SETVAL, 1);

    // 将共享内存链接到当前进程
    shm = shmat(shmid, 0, 0);
    if(shm == (void *)-1){
        printf("共享内存链接到当前进程失败\n");
        exit(1);
    }
    mybuffer = (struct myBuffer* )shm;
    mybuffer->head = mybuffer->tail = 0;

    // 生产者进程
    while(producer_num < 2){
        producer_num++;
        producer = fork();
        if(producer < 0){
            printf("创建进程失败\n");
            exit(1);
        }
        if(producer == 0){
            shm = shmat(shmid, 0, 0);
            if(shm == (void *)-1){
                printf("共享内存链接到当前进程失败\n");
                exit(1);
            }
            mybuffer = (struct myBuffer* )shm;

            for(int i = 0; i < 6; i++){
                // 随机等待一段时间
                sleep(getRandomNum(producer_num, 1));

                // P(empty), P(mutex)
                P(semid, 1);
                P(semid, 2);

                 // 取出当前时间
                timer = time(NULL);
                now = localtime(&timer);
                
                // 向共享区放入一个随机字母
                mybuffer->str[mybuffer->tail] = getRandomLetter(producer_num);
                mybuffer->tail = (mybuffer->tail + 1) % 4;

                // 打印添加数据时间、缓冲区数据
                printf("生产者进程%d：%s缓冲区内容：", producer_num, asctime(now));
                int temp = mybuffer->head;
                while(temp != mybuffer->tail){
                    printf("%c", mybuffer->str[temp]);
                    temp = (temp + 1) % 4;
                }
                printf("\n");
                fflush(stdout);

                // V(full), V(mutex)
                V(semid, 2);
                V(semid, 0);
            }

            // 解除进程与共享区绑定
            shmdt(mybuffer);
            exit(0);
        }
    }

    // 消费者进程
    while(customer_num < 3){
        customer_num++;
        customer = fork();
        if(customer < 0){
            printf("创建进程失败\n");
            exit(1);
        }
        if(customer == 0){
            shm = shmat(shmid, 0, 0);
            if(shm == (void *)-1){
                printf("共享内存链接到当前进程失败\n");
                exit(1);
            }
            mybuffer = (struct myBuffer* )shm;
            
            for(int i = 0; i < 4; i++){
                // 随机等待一段时间
                sleep(getRandomNum(customer_num, 2));

                // P(full), P(mutex)
                P(semid, 0);
                P(semid, 2);

                // 取出当前时间
                timer = time(NULL);
                now = localtime(&timer);
                
                // 从共享区取出一个字母
                mybuffer->head = (mybuffer->head + 1) % 4;

                // 打印添加数据时间、缓冲区数据
                printf("消费者进程%d：%s缓冲区内容：", customer_num, asctime(now));
                int temp = mybuffer->head;
                while(temp != mybuffer->tail){
                    printf("%c", mybuffer->str[temp]);
                    temp = (temp + 1) % 4;
                }
                printf("\n");
                fflush(stdout);

                // V(empty), V(mutex)
                V(semid, 2);
                V(semid, 1);
            }

            // 解除进程与共享区绑定
            shmdt(mybuffer);
            exit(0);
        }
    }

    while (wait(0) != -1){
        continue;
    };
    shmdt(mybuffer);
    shmctl(shmid, IPC_RMID, 0);
    for(int i = 0; i < 3; i++){
        semctl(semid, i, IPC_RMID);
    }
    exit(0);

}












