#include "minic-stdlib.h"

void main(){
    int x;
    int y;
    x=1;
    y=1;
    while (x<5){
        while (y<5){
            y=y+1;
            print_i(y);
        }
        x=x+1;
        print_i(x);
    }
}

