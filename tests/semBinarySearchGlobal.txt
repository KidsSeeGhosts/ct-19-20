#include "../minic-stdlib.h"

int arr[5];
int x;

int binarySearch(int l, int r, int x){
    while (l <= r){
        int m;
        m = l + (r - l) / 2;
        // Check if x is present at mid
        if (arr[m] == x)
            return m;
        
        // If x greater, ignore left half
        if (arr[m] < x)
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
    x = 40; //what we're looking for
    n = 5 / 1;
    arr[0] = 2;
    arr[1] = 3;
    arr[2] = 4;
    arr[3] = 10;
    arr[4] = 40;
    result = binarySearch(0, n - 1, x);
    print_i(result);
    return 0;
}
