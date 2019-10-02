#include "minic-stdlib.h"

struct yay { int n; char c;  } ; 

void main() {
    
    // read n from the standard input
    n = read_i();
    
    first = 0;
    second = 1;
    
    print_s((char*)"First ");
    print_i(n);
    print_s((char*)" terms of Fibonacci series are : ");
    
    c = 0;
    while (c < n) {
        if ( c <= 1 )
            next = c;
        else
        {
            next = first + second;
            first = second;
            second = next;
        }
        print_i(next);
        print_s((char*)" ");
        c = c+1;
    }
}

