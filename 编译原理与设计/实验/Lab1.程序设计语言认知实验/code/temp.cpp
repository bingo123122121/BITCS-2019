#include <iostream>
#include <cmath>
using namespace std;

int main(){
    double a, sum = 0;
    for (int i = 0; i < 10; i++){
        cin >> a;
        sum += -a * log2(a);
    }
    cout << sum << endl;
}