#include "../minic-stdlib.h"

struct a{
    int a;
    int b;
};

struct hello{
    int a;
    char b;
    int c[5];
    struct a y;
};

void main(){
    struct hello a;
    print_i(sizeof(struct hello));
}
