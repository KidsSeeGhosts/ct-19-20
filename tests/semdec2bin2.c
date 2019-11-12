void decToBinary(int n)
{
    // array to store binary number
    int binaryNum[4];
    
    // counter for binary array
    int i;
    int j;
    i=0;
    j=0;
    while (n > 0) {
        
        // storing remainder in binary array
        binaryNum[i] = n % 2;
        n = n / 2;
        i=i+1;
    }
    while (j<4){
        printf("%d",binaryNum[j]);
        j=j+1;
    }
    
}

// Driver program to test above function
int main()
{
    int n;
    
    n = 9;
    decToBinary(n);
    return 0;
}

