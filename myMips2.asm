.data
stringLabel1: .asciiz "\n"
stringLabel2: .asciiz "     1   2   3\n"
stringLabel3: .asciiz "   +---+---+---+\n"
stringLabel4: .asciiz "a  | "
stringLabel5: .asciiz " | "
stringLabel6: .asciiz " | "
stringLabel7: .asciiz " |\n"
stringLabel8: .asciiz "   +---+---+---+\n"
stringLabel9: .asciiz "b  | "
stringLabel10: .asciiz " | "
stringLabel11: .asciiz " | "
stringLabel12: .asciiz " |\n"
stringLabel13: .asciiz "   +---+---+---+\n"
stringLabel14: .asciiz "c  | "
stringLabel15: .asciiz " | "
stringLabel16: .asciiz " | "
stringLabel17: .asciiz " |\n"
stringLabel18: .asciiz "   +---+---+---+\n"
stringLabel19: .asciiz "\n"
stringLabel20: .asciiz "Player "
stringLabel21: .asciiz " has won!\n"
stringLabel22: .asciiz "Player "
stringLabel23: .asciiz " select move (e.g. a2)>"
stringLabel24: .asciiz "That is not a valid move!\n"
stringLabel25: .asciiz "That move is not possible!\n"
stringLabel26: .asciiz "It's a draw!\n"
stringLabel27: .asciiz "Play again? (y/n)> "

a11: .word 0
a12: .word 0
a13: .word 0
a21: .word 0
a22: .word 0
a23: .word 0
a31: .word 0
a32: .word 0
a33: .word 0
empty: .word 0

.text

j main
	reset:
move $fp $sp
la $t8, a11
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
la $t8, a12
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
la $t8, a13
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
la $t8, a21
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
la $t8, a22
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
la $t8, a23
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
la $t8, a31
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
la $t8, a32
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
la $t8, a33
la $s7, empty
lw $s7, ($s7)
sw $s7, ($t8)
resetEnd:
move $sp, $fp
jr $ra
	full:
move $fp $sp
addi $sp,$sp,-4
la $t8, 0($fp)
li $s7 0
sw $s7, ($t8)
la $s7, a11
lw $s7, ($s7)
la $s6, empty
lw $s6, ($s6)
bne $s7, $s6, NotEqualTo1
li $t8, 0
j AfterNotEqualTo1
NotEqualTo1: 
li $t8, 1
AfterNotEqualTo1:
beq $t8, 0, AfterIf1
la $t8, 0($fp)
la $s7, 0($fp)
lw $s7, ($s7)
li $s5 1
add $s6, $s7, $s5
sw $s6, ($t8)
j AfterIfElse1
AfterIf1:
AfterIfElse1:
la $s6, a21
lw $s6, ($s6)
la $s5, empty
lw $s5, ($s5)
bne $s6, $s5, NotEqualTo3
li $t8, 0
j AfterNotEqualTo3
NotEqualTo3: 
li $t8, 1
AfterNotEqualTo3:
beq $t8, 0, AfterIf2
la $t8, 0($fp)
la $s6, 0($fp)
lw $s6, ($s6)
li $s7 1
add $s5, $s6, $s7
sw $s5, ($t8)
j AfterIfElse2
AfterIf2:
AfterIfElse2:
la $s5, a31
lw $s5, ($s5)
la $s7, empty
lw $s7, ($s7)
bne $s5, $s7, NotEqualTo5
li $t8, 0
j AfterNotEqualTo5
NotEqualTo5: 
li $t8, 1
AfterNotEqualTo5:
beq $t8, 0, AfterIf3
la $t8, 0($fp)
la $s5, 0($fp)
lw $s5, ($s5)
li $s6 1
add $s7, $s5, $s6
sw $s7, ($t8)
j AfterIfElse3
AfterIf3:
AfterIfElse3:
la $s7, a12
lw $s7, ($s7)
la $s6, empty
lw $s6, ($s6)
bne $s7, $s6, NotEqualTo7
li $t8, 0
j AfterNotEqualTo7
NotEqualTo7: 
li $t8, 1
AfterNotEqualTo7:
beq $t8, 0, AfterIf4
la $t8, 0($fp)
la $s7, 0($fp)
lw $s7, ($s7)
li $s5 1
add $s6, $s7, $s5
sw $s6, ($t8)
j AfterIfElse4
AfterIf4:
AfterIfElse4:
la $s6, a22
lw $s6, ($s6)
la $s5, empty
lw $s5, ($s5)
bne $s6, $s5, NotEqualTo9
li $t8, 0
j AfterNotEqualTo9
NotEqualTo9: 
li $t8, 1
AfterNotEqualTo9:
beq $t8, 0, AfterIf5
la $t8, 0($fp)
la $s6, 0($fp)
lw $s6, ($s6)
li $s7 1
add $s5, $s6, $s7
sw $s5, ($t8)
j AfterIfElse5
AfterIf5:
AfterIfElse5:
la $s5, a32
lw $s5, ($s5)
la $s7, empty
lw $s7, ($s7)
bne $s5, $s7, NotEqualTo11
li $t8, 0
j AfterNotEqualTo11
NotEqualTo11: 
li $t8, 1
AfterNotEqualTo11:
beq $t8, 0, AfterIf6
la $t8, 0($fp)
la $s5, 0($fp)
lw $s5, ($s5)
li $s6 1
add $s7, $s5, $s6
sw $s7, ($t8)
j AfterIfElse6
AfterIf6:
AfterIfElse6:
la $s7, a13
lw $s7, ($s7)
la $s6, empty
lw $s6, ($s6)
bne $s7, $s6, NotEqualTo13
li $t8, 0
j AfterNotEqualTo13
NotEqualTo13: 
li $t8, 1
AfterNotEqualTo13:
beq $t8, 0, AfterIf7
la $t8, 0($fp)
la $s7, 0($fp)
lw $s7, ($s7)
li $s5 1
add $s6, $s7, $s5
sw $s6, ($t8)
j AfterIfElse7
AfterIf7:
AfterIfElse7:
la $s6, a23
lw $s6, ($s6)
la $s5, empty
lw $s5, ($s5)
bne $s6, $s5, NotEqualTo15
li $t8, 0
j AfterNotEqualTo15
NotEqualTo15: 
li $t8, 1
AfterNotEqualTo15:
beq $t8, 0, AfterIf8
la $t8, 0($fp)
la $s6, 0($fp)
lw $s6, ($s6)
li $s7 1
add $s5, $s6, $s7
sw $s5, ($t8)
j AfterIfElse8
AfterIf8:
AfterIfElse8:
la $s5, a33
lw $s5, ($s5)
la $s7, empty
lw $s7, ($s7)
bne $s5, $s7, NotEqualTo17
li $t8, 0
j AfterNotEqualTo17
NotEqualTo17: 
li $t8, 1
AfterNotEqualTo17:
beq $t8, 0, AfterIf9
la $t8, 0($fp)
la $s5, 0($fp)
lw $s5, ($s5)
li $s6 1
add $s7, $s5, $s6
sw $s7, ($t8)
j AfterIfElse9
AfterIf9:
AfterIfElse9:
la $s7, 0($fp)
lw $s7, ($s7)
li $s6 9
seq $t8, $s7, $s6
beq $t8, 0, AfterIf10
li $t8 1
move $t9, $t8
j fullEnd
j AfterIfElse10
AfterIf10:
li $t8 0
move $t9, $t8
j fullEnd
AfterIfElse10:
fullEnd:
move $sp, $fp
jr $ra
	set:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
la $t8, -12($fp)
li $s6 1
sw $s6, ($t8)
la $s6, 0($fp)
lw $s6, ($s6)
la $s7 'a'
seq $t8, $s6, $s7
beq $t8, 0, AfterIf11
la $s7, -4($fp)
lw $s7, ($s7)
li $s6 1
seq $t8, $s7, $s6
beq $t8, 0, AfterIf12
la $s6, a11
lw $s6, ($s6)
la $s7, empty
lw $s7, ($s7)
seq $t8, $s6, $s7
beq $t8, 0, AfterIf13
la $t8, a11
la $s7, -8($fp)
lw $s7, ($s7)
sw $s7, ($t8)
j AfterIfElse13
AfterIf13:
la $t8, -12($fp)
li $s6 0
li $s5 1
sub $s7, $s6, $s5
sw $s7, ($t8)
AfterIfElse13:
j AfterIfElse12
AfterIf12:
la $s7, -4($fp)
lw $s7, ($s7)
li $s5 2
seq $t8, $s7, $s5
beq $t8, 0, AfterIf14
la $s5, a12
lw $s5, ($s5)
la $s7, empty
lw $s7, ($s7)
seq $t8, $s5, $s7
beq $t8, 0, AfterIf15
la $t8, a12
la $s7, -8($fp)
lw $s7, ($s7)
sw $s7, ($t8)
j AfterIfElse15
AfterIf15:
la $t8, -12($fp)
li $s5 0
li $s6 1
sub $s7, $s5, $s6
sw $s7, ($t8)
AfterIfElse15:
j AfterIfElse14
AfterIf14:
la $s7, -4($fp)
lw $s7, ($s7)
li $s6 3
seq $t8, $s7, $s6
beq $t8, 0, AfterIf16
la $s6, a13
lw $s6, ($s6)
la $s7, empty
lw $s7, ($s7)
seq $t8, $s6, $s7
beq $t8, 0, AfterIf17
la $t8, a13
la $s7, -8($fp)
lw $s7, ($s7)
sw $s7, ($t8)
j AfterIfElse17
AfterIf17:
la $t8, -12($fp)
li $s6 0
li $s5 1
sub $s7, $s6, $s5
sw $s7, ($t8)
AfterIfElse17:
j AfterIfElse16
AfterIf16:
la $t8, -12($fp)
li $s7 0
sw $s7, ($t8)
AfterIfElse16:
AfterIfElse14:
AfterIfElse12:
j AfterIfElse11
AfterIf11:
la $s7, 0($fp)
lw $s7, ($s7)
la $s5 'b'
seq $t8, $s7, $s5
beq $t8, 0, AfterIf18
la $s5, -4($fp)
lw $s5, ($s5)
li $s7 1
seq $t8, $s5, $s7
beq $t8, 0, AfterIf19
la $s7, a21
lw $s7, ($s7)
la $s5, empty
lw $s5, ($s5)
seq $t8, $s7, $s5
beq $t8, 0, AfterIf20
la $t8, a21
la $s5, -8($fp)
lw $s5, ($s5)
sw $s5, ($t8)
j AfterIfElse20
AfterIf20:
la $t8, -12($fp)
li $s7 0
li $s6 1
sub $s5, $s7, $s6
sw $s5, ($t8)
AfterIfElse20:
j AfterIfElse19
AfterIf19:
la $s5, -4($fp)
lw $s5, ($s5)
li $s6 2
seq $t8, $s5, $s6
beq $t8, 0, AfterIf21
la $s6, a22
lw $s6, ($s6)
la $s5, empty
lw $s5, ($s5)
seq $t8, $s6, $s5
beq $t8, 0, AfterIf22
la $t8, a22
la $s5, -8($fp)
lw $s5, ($s5)
sw $s5, ($t8)
j AfterIfElse22
AfterIf22:
la $t8, -12($fp)
li $s6 0
li $s7 1
sub $s5, $s6, $s7
sw $s5, ($t8)
AfterIfElse22:
j AfterIfElse21
AfterIf21:
la $s5, -4($fp)
lw $s5, ($s5)
li $s7 3
seq $t8, $s5, $s7
beq $t8, 0, AfterIf23
la $s7, a23
lw $s7, ($s7)
la $s5, empty
lw $s5, ($s5)
seq $t8, $s7, $s5
beq $t8, 0, AfterIf24
la $t8, a23
la $s5, -8($fp)
lw $s5, ($s5)
sw $s5, ($t8)
j AfterIfElse24
AfterIf24:
la $t8, -12($fp)
li $s7 0
li $s6 1
sub $s5, $s7, $s6
sw $s5, ($t8)
AfterIfElse24:
j AfterIfElse23
AfterIf23:
la $t8, -12($fp)
li $s5 0
sw $s5, ($t8)
AfterIfElse23:
AfterIfElse21:
AfterIfElse19:
j AfterIfElse18
AfterIf18:
la $s5, 0($fp)
lw $s5, ($s5)
la $s6 'c'
seq $t8, $s5, $s6
beq $t8, 0, AfterIf25
la $s6, -4($fp)
lw $s6, ($s6)
li $s5 1
seq $t8, $s6, $s5
beq $t8, 0, AfterIf26
la $s5, a31
lw $s5, ($s5)
la $s6, empty
lw $s6, ($s6)
seq $t8, $s5, $s6
beq $t8, 0, AfterIf27
la $t8, a31
la $s6, -8($fp)
lw $s6, ($s6)
sw $s6, ($t8)
j AfterIfElse27
AfterIf27:
la $t8, -12($fp)
li $s5 0
li $s7 1
sub $s6, $s5, $s7
sw $s6, ($t8)
AfterIfElse27:
j AfterIfElse26
AfterIf26:
la $s6, -4($fp)
lw $s6, ($s6)
li $s7 2
seq $t8, $s6, $s7
beq $t8, 0, AfterIf28
la $s7, a32
lw $s7, ($s7)
la $s6, empty
lw $s6, ($s6)
seq $t8, $s7, $s6
beq $t8, 0, AfterIf29
la $t8, a32
la $s6, -8($fp)
lw $s6, ($s6)
sw $s6, ($t8)
j AfterIfElse29
AfterIf29:
la $t8, -12($fp)
li $s7 0
li $s5 1
sub $s6, $s7, $s5
sw $s6, ($t8)
AfterIfElse29:
j AfterIfElse28
AfterIf28:
la $s6, -4($fp)
lw $s6, ($s6)
li $s5 3
seq $t8, $s6, $s5
beq $t8, 0, AfterIf30
la $s5, a33
lw $s5, ($s5)
la $s6, empty
lw $s6, ($s6)
seq $t8, $s5, $s6
beq $t8, 0, AfterIf31
la $t8, a33
la $s6, -8($fp)
lw $s6, ($s6)
sw $s6, ($t8)
j AfterIfElse31
AfterIf31:
la $t8, -12($fp)
li $s5 0
li $s7 1
sub $s6, $s5, $s7
sw $s6, ($t8)
AfterIfElse31:
j AfterIfElse30
AfterIf30:
la $t8, -12($fp)
li $s6 0
sw $s6, ($t8)
AfterIfElse30:
AfterIfElse28:
AfterIfElse26:
j AfterIfElse25
AfterIf25:
la $t8, -12($fp)
li $s6 0
sw $s6, ($t8)
AfterIfElse25:
AfterIfElse18:
AfterIfElse11:
la $t8, -12($fp)
lw $t8, ($t8)
move $t9, $t8
j setEnd
setEnd:
move $sp, $fp
jr $ra
	printGame:
move $fp $sp
la $t8, stringLabel1
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel2
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel3
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel4
li $v0, 4
move $a0, $t8
syscall
la $t8, a11
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel5
li $v0, 4
move $a0, $t8
syscall
la $t8, a12
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel6
li $v0, 4
move $a0, $t8
syscall
la $t8, a13
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel7
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel8
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel9
li $v0, 4
move $a0, $t8
syscall
la $t8, a21
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel10
li $v0, 4
move $a0, $t8
syscall
la $t8, a22
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel11
li $v0, 4
move $a0, $t8
syscall
la $t8, a23
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel12
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel13
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel14
li $v0, 4
move $a0, $t8
syscall
la $t8, a31
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel15
li $v0, 4
move $a0, $t8
syscall
la $t8, a32
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel16
li $v0, 4
move $a0, $t8
syscall
la $t8, a33
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel17
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel18
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel19
li $v0, 4
move $a0, $t8
syscall
printGameEnd:
move $sp, $fp
jr $ra
	printWinner:
move $fp $sp
addi $sp,$sp,-4
la $t8, stringLabel20
li $v0, 4
move $a0, $t8
syscall
la $t8, 0($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $t8, stringLabel21
li $v0, 4
move $a0, $t8
syscall
printWinnerEnd:
move $sp, $fp
jr $ra
	switchPlayer:
move $fp $sp
addi $sp,$sp,-4
la $s6, 0($fp)
lw $s6, ($s6)
li $s7 1
seq $t8, $s6, $s7
beq $t8, 0, AfterIf32
li $t8 2
move $t9, $t8
j switchPlayerEnd
j AfterIfElse32
AfterIf32:
li $t8 1
move $t9, $t8
j switchPlayerEnd
AfterIfElse32:
switchPlayerEnd:
move $sp, $fp
jr $ra
	get_mark:
move $fp $sp
addi $sp,$sp,-4
la $s7, 0($fp)
lw $s7, ($s7)
li $s6 1
seq $t8, $s7, $s6
beq $t8, 0, AfterIf33
la $t8 'X'
move $t9, $t8
j get_markEnd
j AfterIfElse33
AfterIf33:
la $t8 'O'
move $t9, $t8
j get_markEnd
AfterIfElse33:
get_markEnd:
move $sp, $fp
jr $ra
	selectmove:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
la $t8, -12($fp)
li $s6 1
sw $s6, ($t8)
la $t8, -12($fp)
MyWhile1: beq $t8, 0, AfterWhile1
la $s6, stringLabel22
li $v0, 4
move $a0, $s6
syscall
la $s6, 0($fp)
lw $s6, ($s6)
li $v0, 1
move $a0, $s6
syscall
la $s6, stringLabel23
li $v0, 4
move $a0, $s6
syscall
la $s6, -4($fp)
li $v0, 12
syscall
move $t9, $v0
sw $t9, ($s6)
la $s6, -8($fp)
li $v0, 5
syscall
move $t9, $v0
sw $t9, ($s6)
la $s6, -20($fp)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
addi $sp,$sp,-4
sw $s6, ($sp)
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, 0($fp)
lw $t8, ($t8)
#ast.VarExpr@29444d75
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 0
jal get_mark
add $sp, $sp, 4
lw $t8 ,($sp)
addiu $sp,$sp,4
lw $s6 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
sw $t9, ($s6)
la $s6, -16($fp)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
la $t9, -4($fp)
lw $t9, ($t9)
#ast.VarExpr@2280cdac
addi $sp,$sp,-4
sw $t9, ($sp)
la $t9, -8($fp)
lw $t9, ($t9)
#ast.VarExpr@1517365b
addi $sp,$sp,-4
sw $t9, ($sp)
la $t9, -20($fp)
lw $t9, ($t9)
#ast.VarExpr@4fccd51b
addi $sp,$sp,-4
sw $t9, ($sp)
add $sp, $sp, 8
jal set
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
sw $t9, ($s6)
la $t9, -16($fp)
lw $t9, ($t9)
li $t9 0
seq $s6, $t9, $t9
beq $s6, 0, AfterIf34
la $s6, stringLabel24
li $v0, 4
move $a0, $s6
syscall
j AfterIfElse34
AfterIf34:
la $t9, -16($fp)
lw $t9, ($t9)
li $t8 0
li $s6 1
sub $t9, $t8, $s6
seq $s6, $t9, $t9
beq $s6, 0, AfterIf35
la $s6, stringLabel25
li $v0, 4
move $a0, $s6
syscall
j AfterIfElse35
AfterIf35:
la $s6, -12($fp)
li $t9 0
sw $t9, ($s6)
AfterIfElse35:
AfterIfElse34:
la $s6, -12($fp)
move $t8, $s6
j MyWhile1
AfterWhile1: 
selectmoveEnd:
move $sp, $fp
jr $ra
	won:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
la $s6, -4($fp)
li $t8 0
sw $t8, ($s6)
la $t8, a11
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf36
la $t9, a21
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf37
la $t8, a31
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf38
la $s6, -4($fp)
li $t9 1
sw $t9, ($s6)
j AfterIfElse38
AfterIf38:
AfterIfElse38:
j AfterIfElse37
AfterIf37:
la $t9, a22
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf39
la $t8, a33
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf40
la $s6, -4($fp)
li $t9 1
sw $t9, ($s6)
j AfterIfElse40
AfterIf40:
AfterIfElse40:
j AfterIfElse39
AfterIf39:
la $t9, a12
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf41
la $t8, a13
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf42
la $s6, -4($fp)
li $t9 1
sw $t9, ($s6)
j AfterIfElse42
AfterIf42:
AfterIfElse42:
j AfterIfElse41
AfterIf41:
AfterIfElse41:
AfterIfElse39:
AfterIfElse37:
j AfterIfElse36
AfterIf36:
AfterIfElse36:
la $t9, a12
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf43
la $t8, a22
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf44
la $t9, a32
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf45
la $s6, -4($fp)
li $t8 1
sw $t8, ($s6)
j AfterIfElse45
AfterIf45:
AfterIfElse45:
j AfterIfElse44
AfterIf44:
AfterIfElse44:
j AfterIfElse43
AfterIf43:
AfterIfElse43:
la $t8, a13
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf46
la $t9, a23
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf47
la $t8, a33
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf48
la $s6, -4($fp)
li $t9 1
sw $t9, ($s6)
j AfterIfElse48
AfterIf48:
AfterIfElse48:
j AfterIfElse47
AfterIf47:
la $t9, a22
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf49
la $t8, a31
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf50
la $s6, -4($fp)
li $t9 1
sw $t9, ($s6)
j AfterIfElse50
AfterIf50:
AfterIfElse50:
j AfterIfElse49
AfterIf49:
AfterIfElse49:
AfterIfElse47:
j AfterIfElse46
AfterIf46:
AfterIfElse46:
la $t9, a21
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf51
la $t8, a22
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf52
la $t9, a23
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf53
la $s6, -4($fp)
li $t8 1
sw $t8, ($s6)
j AfterIfElse53
AfterIf53:
AfterIfElse53:
j AfterIfElse52
AfterIf52:
AfterIfElse52:
j AfterIfElse51
AfterIf51:
AfterIfElse51:
la $t8, a31
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf54
la $t9, a32
lw $t9, ($t9)
la $t8, 0($fp)
lw $t8, ($t8)
seq $s6, $t9, $t8
beq $s6, 0, AfterIf55
la $t8, a33
lw $t8, ($t8)
la $t9, 0($fp)
lw $t9, ($t9)
seq $s6, $t8, $t9
beq $s6, 0, AfterIf56
la $s6, -4($fp)
li $t9 1
sw $t9, ($s6)
j AfterIfElse56
AfterIf56:
AfterIfElse56:
j AfterIfElse55
AfterIf55:
AfterIfElse55:
j AfterIfElse54
AfterIf54:
AfterIfElse54:
la $s6, -4($fp)
lw $s6, ($s6)
move $t9, $s6
j wonEnd
wonEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
la $s6, empty
la $t9 ' '
sw $t9, ($s6)
la $s6, 0($fp)
li $t9 1
sw $t9, ($s6)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
add $sp, $sp, -4
jal reset
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
add $sp, $sp, -4
jal printGame
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
la $s6, -4($fp)
li $t9 1
sw $t9, ($s6)
la $s6, 0($fp)
MyWhile2: beq $s6, 0, AfterWhile2
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
la $t9, -4($fp)
lw $t9, ($t9)
#ast.VarExpr@44e81672
addi $sp,$sp,-4
sw $t9, ($sp)
add $sp, $sp, 0
jal selectmove
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
la $t9, -8($fp)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
la $t8, -4($fp)
lw $t8, ($t8)
#ast.VarExpr@60215eee
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 0
jal get_mark
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
sw $t9, ($t9)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
add $sp, $sp, -4
jal printGame
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
la $t9, -8($fp)
lw $t9, ($t9)
#ast.VarExpr@4ca8195f
addi $sp,$sp,-4
sw $t9, ($sp)
add $sp, $sp, 0
jal won
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
beq $t9, 0, AfterIf57
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
la $t9, -4($fp)
lw $t9, ($t9)
#ast.VarExpr@65e579dc
addi $sp,$sp,-4
sw $t9, ($sp)
add $sp, $sp, 0
jal printWinner
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
la $t9, 0($fp)
li $t9 0
sw $t9, ($t9)
j AfterIfElse57
AfterIf57:
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
add $sp, $sp, -4
jal full
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
li $t9 1
seq $t9, $t9, $t9
beq $t9, 0, AfterIf58
la $t9, stringLabel26
li $v0, 4
move $a0, $t9
syscall
la $t9, 0($fp)
li $t9 0
sw $t9, ($t9)
j AfterIfElse58
AfterIf58:
la $t9, -4($fp)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
la $t9, -4($fp)
lw $t9, ($t9)
#ast.VarExpr@61baa894
addi $sp,$sp,-4
sw $t9, ($sp)
add $sp, $sp, 0
jal switchPlayer
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
sw $t9, ($t9)
AfterIfElse58:
AfterIfElse57:
la $t9, 0($fp)
lw $t9, ($t9)
li $t9 0
seq $t9, $t9, $t9
beq $t9, 0, AfterIf59
la $t9, stringLabel27
li $v0, 4
move $a0, $t9
syscall
la $t9, -12($fp)
li $v0, 12
syscall
move $t9, $v0
sw $t9, ($t9)
la $t9, -12($fp)
lw $t9, ($t9)
la $t9 'y'
seq $t9, $t9, $t9
beq $t9, 0, AfterIf60
la $t9, 0($fp)
li $t9 1
sw $t9, ($t9)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
add $sp, $sp, -4
jal reset
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
j AfterIfElse60
AfterIf60:
la $t9, -12($fp)
lw $t9, ($t9)
la $t9 'Y'
seq $t9, $t9, $t9
beq $t9, 0, AfterIf61
la $t9, 0($fp)
li $t9 1
sw $t9, ($t9)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
add $sp, $sp, -4
jal reset
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
j AfterIfElse61
AfterIf61:
AfterIfElse61:
AfterIfElse60:
j AfterIfElse59
AfterIf59:
AfterIfElse59:
la $t9, 0($fp)
move $s6, $t9
j MyWhile2
AfterWhile2: 
mainEnd:
move $sp, $fp
li $v0, 10
syscall
