void towerOfHanoi(int n, char from_rod, char to_rod, char aux_rod){
    print_i(n);
    if (n == 1){
        print_s((char*) "Move disk 1 from rod");
        print_c(from_rod);
        print_s((char*) "to");
        print_c(to_rod);
        return;
    }
    towerOfHanoi(n-1, from_rod, aux_rod, to_rod);
    print_s("yolo");
                print_s((char*) "Move disk");
                print_i(n);
                print_s((char*) " from ");
                print_c(from_rod);
                print_s((char*) "to ");
                print_c(to_rod);
    towerOfHanoi(n-1, aux_rod, to_rod, from_rod);
}

int main(){
    int n; // Number of disks
    n=4;
    towerOfHanoi(n, 'A', 'C', 'B');  // A, B and C are names of rods
    return 0;
}
