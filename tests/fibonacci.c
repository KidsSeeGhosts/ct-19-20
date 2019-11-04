int bar(int a, int b){
    print_i(a);
    print_i(b);
    return a+b;
    
}

void main() {
    int x;
    int y;
    x=5;
    y=8;
    y=bar(x,y);
    print_i(y);
}
