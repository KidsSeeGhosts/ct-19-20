#include "../minic-stdlib.h"

int binarySearch(int *arr, int l, int r, int x){
    print_c('y');
    print_i(l);
    print_c('x');
    print_i(r);
    print_i(x);

    while (l <= r)
    {
        int m;
        
        m = l + (r - l) / 2;
        
        // Check if x is present at mid
        if (arr[m] == x)
            print_c('y');
        
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

int main()
{
    
    int arr[5];
    int n;
    int x;
    int result;
    int *arrPoint;
    
    arr[0] = 2;
    arr[1] = 3;
    arr[2] = 4;
    arr[3] = 10;
    arr[4] = 40;
    x = 10;
    n = 5 / 1;
    arrPoint = (int *) arr;
    
    print_i(arrPoint[1]);
    result = binarySearch(arrPoint, 0, n - 1, x);
    if (result == -1)
        print_s((char *)"Element is not present in array");
    else
    {
        print_s((char *)"Element is present at index ");
        print_i(result);
        print_c('\n');
    }
    return 0;
}
