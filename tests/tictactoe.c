#include "../minic-stdlib.h"

int notes[9];

void countCash(int amount){
    int noteCounter[9];
    int i;
    i=0;
    while(i<9){
        if (notes[i]<=amount){
            noteCounter[i]=amount/notes[i];
            amount=amount-noteCounter[i]*notes[i];
        }else
            noteCounter[i]=0;
        i=i+1;
    }
    
    i=0;
    while(i<9){
        if (noteCounter[i]!=0){
            print_i(notes[i]);
            print_s((char*)" note");
            print_s((char*)": ");
            print_i(noteCounter[i]);
            print_c('\n');
        }
        i=i+1;
    }
    
}

void main(){
    int amount;
    notes[0]=2000;
    notes[1]=500;
    notes[2]=200;
    notes[3]=100;
    notes[4]=50;
    notes[5]=20;
    notes[6]=10;
    notes[7]=5;
    notes[8]=1;
    amount = 2341;
    countCash(amount);
}


