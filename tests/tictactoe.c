#include "../minic-stdlib.h"


struct hello{
    int arr[5];
};

struct hello a;

int binarySearch(int l, int r, int x){
    a.arr[0] = 2;
    a.arr[1] = 3;
    a.arr[2] = 4;
    a.arr[3] = 10;
    a.arr[4] = 40;
    while (l <= r){
        int m;
        m = l + (r - l) / 2;
        // Check if x is present at mid
        if (a.arr[m] == x)
            return m;
        
        // If x greater, ignore left half
        if (a.arr[m] < x)
            l = m + 1;
        
        // If x is smaller, ignore right half
        else
            r = m - 1;
    }
    // if we reach here, then element was
    // not present
    return -1;
}

int main(){
    int n;
    int x;
    int result;
    x = 10; //what we're looking for
    n = 5 / 1;
    result = binarySearch(0, n - 1, x);
    print_i(result);
    return 0;
}
