int adder(int x){
    x=x+1;
    return x;
}

void main(){
    int c;
    c = adder(adder(5));
    print_i(c);
}
