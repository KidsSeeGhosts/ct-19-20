.data
stringLabel1: .asciiz "\n"
.align 8
stringLabel2: .asciiz "     1   2   3\n"
.align 8
stringLabel3: .asciiz "   +---+---+---+\n"
.align 8
stringLabel4: .asciiz "a  | "
.align 8
stringLabel5: .asciiz " | "
.align 8
stringLabel6: .asciiz " | "
.align 8
stringLabel7: .asciiz " |\n"
.align 8
stringLabel8: .asciiz "   +---+---+---+\n"
.align 8
stringLabel9: .asciiz "b  | "
.align 8
stringLabel10: .asciiz " | "
.align 8
stringLabel11: .asciiz " | "
.align 8
stringLabel12: .asciiz " |\n"
.align 8
stringLabel13: .asciiz "   +---+---+---+\n"
.align 8
stringLabel14: .asciiz "c  | "
.align 8
stringLabel15: .asciiz " | "
.align 8
stringLabel16: .asciiz " | "
.align 8
stringLabel17: .asciiz " |\n"
.align 8
stringLabel18: .asciiz "   +---+---+---+\n"
.align 8
stringLabel19: .asciiz "\n"
.align 8
stringLabel20: .asciiz "Player "
.align 8
stringLabel21: .asciiz " has won!\n"
.align 8
stringLabel22: .asciiz "Player "
.align 8
stringLabel23: .asciiz " select move (e.g. a2)>"
.align 8
stringLabel24: .asciiz "That is not a valid move!\n"
.align 8
stringLabel25: .asciiz "That move is not possible!\n"
.align 8
stringLabel26: .asciiz "It's a draw!\n"
.align 8
stringLabel27: .asciiz "Play again? (y/n)> "
.align 8

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
#about to do rhs in assign
la $t8, empty
#just got register for global var expr address
#about to do lhs in assign
la $s7, a11
#just got register for global var expr address
lw $t8, ($t8)
sw $t8, ($s7)
#about to do rhs in assign
la $s7, empty
#just got register for global var expr address
#about to do lhs in assign
la $t8, a12
#just got register for global var expr address
lw $s7, ($s7)
sw $s7, ($t8)
#about to do rhs in assign
la $t8, empty
#just got register for global var expr address
#about to do lhs in assign
la $s7, a13
#just got register for global var expr address
lw $t8, ($t8)
sw $t8, ($s7)
#about to do rhs in assign
la $s7, empty
#just got register for global var expr address
#about to do lhs in assign
la $t8, a21
#just got register for global var expr address
lw $s7, ($s7)
sw $s7, ($t8)
#about to do rhs in assign
la $t8, empty
#just got register for global var expr address
#about to do lhs in assign
la $s7, a22
#just got register for global var expr address
lw $t8, ($t8)
sw $t8, ($s7)
#about to do rhs in assign
la $s7, empty
#just got register for global var expr address
#about to do lhs in assign
la $t8, a23
#just got register for global var expr address
lw $s7, ($s7)
sw $s7, ($t8)
#about to do rhs in assign
la $t8, empty
#just got register for global var expr address
#about to do lhs in assign
la $s7, a31
#just got register for global var expr address
lw $t8, ($t8)
sw $t8, ($s7)
#about to do rhs in assign
la $s7, empty
#just got register for global var expr address
#about to do lhs in assign
la $t8, a32
#just got register for global var expr address
lw $s7, ($s7)
sw $s7, ($t8)
#about to do rhs in assign
la $t8, empty
#just got register for global var expr address
#about to do lhs in assign
la $s7, a33
#just got register for global var expr address
lw $t8, ($t8)
sw $t8, ($s7)
resetEnd:
move $sp, $fp
jr $ra
	full:
move $fp $sp
addi $sp,$sp,-4
#about to do rhs in assign
li $s7 0
#about to do lhs in assign
la $t8, 0($fp)
sw $s7, ($t8)
la $s7, a11
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $s7, $s6, NotEqualTo1
li $t8, 0
j AfterNotEqualTo1
NotEqualTo1: 
li $t8, 1
AfterNotEqualTo1:
beq $t8, 0, AfterIf1
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $s7 1
#just accepted rhs of bin op
add $t8, $s6, $s7
#about to do lhs in assign
la $s7, 0($fp)
sw $t8, ($s7)
j AfterIfElse1
AfterIf1:
AfterIfElse1:
la $t8, a21
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $t8, $s6, NotEqualTo3
li $s7, 0
j AfterNotEqualTo3
NotEqualTo3: 
li $s7, 1
AfterNotEqualTo3:
beq $s7, 0, AfterIf2
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $t8 1
#just accepted rhs of bin op
add $s7, $s6, $t8
#about to do lhs in assign
la $t8, 0($fp)
sw $s7, ($t8)
j AfterIfElse2
AfterIf2:
AfterIfElse2:
la $s7, a31
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $s7, $s6, NotEqualTo5
li $t8, 0
j AfterNotEqualTo5
NotEqualTo5: 
li $t8, 1
AfterNotEqualTo5:
beq $t8, 0, AfterIf3
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $s7 1
#just accepted rhs of bin op
add $t8, $s6, $s7
#about to do lhs in assign
la $s7, 0($fp)
sw $t8, ($s7)
j AfterIfElse3
AfterIf3:
AfterIfElse3:
la $t8, a12
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $t8, $s6, NotEqualTo7
li $s7, 0
j AfterNotEqualTo7
NotEqualTo7: 
li $s7, 1
AfterNotEqualTo7:
beq $s7, 0, AfterIf4
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $t8 1
#just accepted rhs of bin op
add $s7, $s6, $t8
#about to do lhs in assign
la $t8, 0($fp)
sw $s7, ($t8)
j AfterIfElse4
AfterIf4:
AfterIfElse4:
la $s7, a22
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $s7, $s6, NotEqualTo9
li $t8, 0
j AfterNotEqualTo9
NotEqualTo9: 
li $t8, 1
AfterNotEqualTo9:
beq $t8, 0, AfterIf5
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $s7 1
#just accepted rhs of bin op
add $t8, $s6, $s7
#about to do lhs in assign
la $s7, 0($fp)
sw $t8, ($s7)
j AfterIfElse5
AfterIf5:
AfterIfElse5:
la $t8, a32
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $t8, $s6, NotEqualTo11
li $s7, 0
j AfterNotEqualTo11
NotEqualTo11: 
li $s7, 1
AfterNotEqualTo11:
beq $s7, 0, AfterIf6
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $t8 1
#just accepted rhs of bin op
add $s7, $s6, $t8
#about to do lhs in assign
la $t8, 0($fp)
sw $s7, ($t8)
j AfterIfElse6
AfterIf6:
AfterIfElse6:
la $s7, a13
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $s7, $s6, NotEqualTo13
li $t8, 0
j AfterNotEqualTo13
NotEqualTo13: 
li $t8, 1
AfterNotEqualTo13:
beq $t8, 0, AfterIf7
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $s7 1
#just accepted rhs of bin op
add $t8, $s6, $s7
#about to do lhs in assign
la $s7, 0($fp)
sw $t8, ($s7)
j AfterIfElse7
AfterIf7:
AfterIfElse7:
la $t8, a23
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $t8, $s6, NotEqualTo15
li $s7, 0
j AfterNotEqualTo15
NotEqualTo15: 
li $s7, 1
AfterNotEqualTo15:
beq $s7, 0, AfterIf8
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $t8 1
#just accepted rhs of bin op
add $s7, $s6, $t8
#about to do lhs in assign
la $t8, 0($fp)
sw $s7, ($t8)
j AfterIfElse8
AfterIf8:
AfterIfElse8:
la $s7, a33
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
bne $s7, $s6, NotEqualTo17
li $t8, 0
j AfterNotEqualTo17
NotEqualTo17: 
li $t8, 1
AfterNotEqualTo17:
beq $t8, 0, AfterIf9
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $s7 1
#just accepted rhs of bin op
add $t8, $s6, $s7
#about to do lhs in assign
la $s7, 0($fp)
sw $t8, ($s7)
j AfterIfElse9
AfterIf9:
AfterIfElse9:
la $t8, 0($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s6 9
#just accepted rhs of bin op
beq $t8, $s6, EqualTo19
li $s7, 0
j AfterEqualTo19
EqualTo19: 
li $s7, 1
AfterEqualTo19:
beq $s7, 0, AfterIf10
li $s7 1
move $t9, $s7
j fullEnd
j AfterIfElse10
AfterIf10:
li $s7 0
move $t9, $s7
j fullEnd
j AfterIfElse10
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
#about to do rhs in assign
li $s7 1
#about to do lhs in assign
la $s6, -12($fp)
sw $s7, ($s6)
la $s7, 0($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8 'a'
#just accepted rhs of bin op
beq $s7, $t8, EqualTo20
li $s6, 0
j AfterEqualTo20
EqualTo20: 
li $s6, 1
AfterEqualTo20:
beq $s6, 0, AfterIf11
la $t8, -4($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s7 1
#just accepted rhs of bin op
beq $t8, $s7, EqualTo21
li $s6, 0
j AfterEqualTo21
EqualTo21: 
li $s6, 1
AfterEqualTo21:
beq $s6, 0, AfterIf12
la $s7, a11
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s7, $t8, EqualTo22
li $s6, 0
j AfterEqualTo22
EqualTo22: 
li $s6, 1
AfterEqualTo22:
beq $s6, 0, AfterIf13
#about to do rhs in assign
la $s6, -8($fp)
#about to do lhs in assign
la $t8, a11
#just got register for global var expr address
lw $s6, ($s6)
sw $s6, ($t8)
j AfterIfElse13
AfterIf13:
#about to do rhs in assign
li $s6 0
#just accepted lhs of bin op
li $s7 1
#just accepted rhs of bin op
sub $t8, $s6, $s7
#about to do lhs in assign
la $s7, -12($fp)
sw $t8, ($s7)
j AfterIfElse13
AfterIfElse13:
j AfterIfElse12
AfterIf12:
la $t8, -4($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s6 2
#just accepted rhs of bin op
beq $t8, $s6, EqualTo24
li $s7, 0
j AfterEqualTo24
EqualTo24: 
li $s7, 1
AfterEqualTo24:
beq $s7, 0, AfterIf14
la $s6, a12
#just got register for global var expr address
#just accepted lhs of bin op
lw $s6, ($s6)
la $t8, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s6, $t8, EqualTo25
li $s7, 0
j AfterEqualTo25
EqualTo25: 
li $s7, 1
AfterEqualTo25:
beq $s7, 0, AfterIf15
#about to do rhs in assign
la $s7, -8($fp)
#about to do lhs in assign
la $t8, a12
#just got register for global var expr address
lw $s7, ($s7)
sw $s7, ($t8)
j AfterIfElse15
AfterIf15:
#about to do rhs in assign
li $s7 0
#just accepted lhs of bin op
li $s6 1
#just accepted rhs of bin op
sub $t8, $s7, $s6
#about to do lhs in assign
la $s6, -12($fp)
sw $t8, ($s6)
j AfterIfElse15
AfterIfElse15:
j AfterIfElse14
AfterIf14:
la $t8, -4($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s7 3
#just accepted rhs of bin op
beq $t8, $s7, EqualTo27
li $s6, 0
j AfterEqualTo27
EqualTo27: 
li $s6, 1
AfterEqualTo27:
beq $s6, 0, AfterIf16
la $s7, a13
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s7, $t8, EqualTo28
li $s6, 0
j AfterEqualTo28
EqualTo28: 
li $s6, 1
AfterEqualTo28:
beq $s6, 0, AfterIf17
#about to do rhs in assign
la $s6, -8($fp)
#about to do lhs in assign
la $t8, a13
#just got register for global var expr address
lw $s6, ($s6)
sw $s6, ($t8)
j AfterIfElse17
AfterIf17:
#about to do rhs in assign
li $s6 0
#just accepted lhs of bin op
li $s7 1
#just accepted rhs of bin op
sub $t8, $s6, $s7
#about to do lhs in assign
la $s7, -12($fp)
sw $t8, ($s7)
j AfterIfElse17
AfterIfElse17:
j AfterIfElse16
AfterIf16:
#about to do rhs in assign
li $s7 0
#about to do lhs in assign
la $t8, -12($fp)
sw $s7, ($t8)
j AfterIfElse16
AfterIfElse16:
j AfterIfElse14
AfterIfElse14:
j AfterIfElse12
AfterIfElse12:
j AfterIfElse11
AfterIf11:
la $s7, 0($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6 'b'
#just accepted rhs of bin op
beq $s7, $s6, EqualTo30
li $t8, 0
j AfterEqualTo30
EqualTo30: 
li $t8, 1
AfterEqualTo30:
beq $t8, 0, AfterIf18
la $s6, -4($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $s7 1
#just accepted rhs of bin op
beq $s6, $s7, EqualTo31
li $t8, 0
j AfterEqualTo31
EqualTo31: 
li $t8, 1
AfterEqualTo31:
beq $t8, 0, AfterIf19
la $s7, a21
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
beq $s7, $s6, EqualTo32
li $t8, 0
j AfterEqualTo32
EqualTo32: 
li $t8, 1
AfterEqualTo32:
beq $t8, 0, AfterIf20
#about to do rhs in assign
la $t8, -8($fp)
#about to do lhs in assign
la $s6, a21
#just got register for global var expr address
lw $t8, ($t8)
sw $t8, ($s6)
j AfterIfElse20
AfterIf20:
#about to do rhs in assign
li $t8 0
#just accepted lhs of bin op
li $s7 1
#just accepted rhs of bin op
sub $s6, $t8, $s7
#about to do lhs in assign
la $s7, -12($fp)
sw $s6, ($s7)
j AfterIfElse20
AfterIfElse20:
j AfterIfElse19
AfterIf19:
la $s6, -4($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $t8 2
#just accepted rhs of bin op
beq $s6, $t8, EqualTo34
li $s7, 0
j AfterEqualTo34
EqualTo34: 
li $s7, 1
AfterEqualTo34:
beq $s7, 0, AfterIf21
la $t8, a22
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
beq $t8, $s6, EqualTo35
li $s7, 0
j AfterEqualTo35
EqualTo35: 
li $s7, 1
AfterEqualTo35:
beq $s7, 0, AfterIf22
#about to do rhs in assign
la $s7, -8($fp)
#about to do lhs in assign
la $s6, a22
#just got register for global var expr address
lw $s7, ($s7)
sw $s7, ($s6)
j AfterIfElse22
AfterIf22:
#about to do rhs in assign
li $s7 0
#just accepted lhs of bin op
li $t8 1
#just accepted rhs of bin op
sub $s6, $s7, $t8
#about to do lhs in assign
la $t8, -12($fp)
sw $s6, ($t8)
j AfterIfElse22
AfterIfElse22:
j AfterIfElse21
AfterIf21:
la $s6, -4($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $s7 3
#just accepted rhs of bin op
beq $s6, $s7, EqualTo37
li $t8, 0
j AfterEqualTo37
EqualTo37: 
li $t8, 1
AfterEqualTo37:
beq $t8, 0, AfterIf23
la $s7, a23
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $s6, ($s6)
beq $s7, $s6, EqualTo38
li $t8, 0
j AfterEqualTo38
EqualTo38: 
li $t8, 1
AfterEqualTo38:
beq $t8, 0, AfterIf24
#about to do rhs in assign
la $t8, -8($fp)
#about to do lhs in assign
la $s6, a23
#just got register for global var expr address
lw $t8, ($t8)
sw $t8, ($s6)
j AfterIfElse24
AfterIf24:
#about to do rhs in assign
li $t8 0
#just accepted lhs of bin op
li $s7 1
#just accepted rhs of bin op
sub $s6, $t8, $s7
#about to do lhs in assign
la $s7, -12($fp)
sw $s6, ($s7)
j AfterIfElse24
AfterIfElse24:
j AfterIfElse23
AfterIf23:
#about to do rhs in assign
li $s7 0
#about to do lhs in assign
la $s6, -12($fp)
sw $s7, ($s6)
j AfterIfElse23
AfterIfElse23:
j AfterIfElse21
AfterIfElse21:
j AfterIfElse19
AfterIfElse19:
j AfterIfElse18
AfterIf18:
la $s7, 0($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8 'c'
#just accepted rhs of bin op
beq $s7, $t8, EqualTo40
li $s6, 0
j AfterEqualTo40
EqualTo40: 
li $s6, 1
AfterEqualTo40:
beq $s6, 0, AfterIf25
la $t8, -4($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s7 1
#just accepted rhs of bin op
beq $t8, $s7, EqualTo41
li $s6, 0
j AfterEqualTo41
EqualTo41: 
li $s6, 1
AfterEqualTo41:
beq $s6, 0, AfterIf26
la $s7, a31
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s7, $t8, EqualTo42
li $s6, 0
j AfterEqualTo42
EqualTo42: 
li $s6, 1
AfterEqualTo42:
beq $s6, 0, AfterIf27
#about to do rhs in assign
la $s6, -8($fp)
#about to do lhs in assign
la $t8, a31
#just got register for global var expr address
lw $s6, ($s6)
sw $s6, ($t8)
j AfterIfElse27
AfterIf27:
#about to do rhs in assign
li $s6 0
#just accepted lhs of bin op
li $s7 1
#just accepted rhs of bin op
sub $t8, $s6, $s7
#about to do lhs in assign
la $s7, -12($fp)
sw $t8, ($s7)
j AfterIfElse27
AfterIfElse27:
j AfterIfElse26
AfterIf26:
la $t8, -4($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s6 2
#just accepted rhs of bin op
beq $t8, $s6, EqualTo44
li $s7, 0
j AfterEqualTo44
EqualTo44: 
li $s7, 1
AfterEqualTo44:
beq $s7, 0, AfterIf28
la $s6, a32
#just got register for global var expr address
#just accepted lhs of bin op
lw $s6, ($s6)
la $t8, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s6, $t8, EqualTo45
li $s7, 0
j AfterEqualTo45
EqualTo45: 
li $s7, 1
AfterEqualTo45:
beq $s7, 0, AfterIf29
#about to do rhs in assign
la $s7, -8($fp)
#about to do lhs in assign
la $t8, a32
#just got register for global var expr address
lw $s7, ($s7)
sw $s7, ($t8)
j AfterIfElse29
AfterIf29:
#about to do rhs in assign
li $s7 0
#just accepted lhs of bin op
li $s6 1
#just accepted rhs of bin op
sub $t8, $s7, $s6
#about to do lhs in assign
la $s6, -12($fp)
sw $t8, ($s6)
j AfterIfElse29
AfterIfElse29:
j AfterIfElse28
AfterIf28:
la $t8, -4($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s7 3
#just accepted rhs of bin op
beq $t8, $s7, EqualTo47
li $s6, 0
j AfterEqualTo47
EqualTo47: 
li $s6, 1
AfterEqualTo47:
beq $s6, 0, AfterIf30
la $s7, a33
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8, empty
#just got register for global var expr address
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s7, $t8, EqualTo48
li $s6, 0
j AfterEqualTo48
EqualTo48: 
li $s6, 1
AfterEqualTo48:
beq $s6, 0, AfterIf31
#about to do rhs in assign
la $s6, -8($fp)
#about to do lhs in assign
la $t8, a33
#just got register for global var expr address
lw $s6, ($s6)
sw $s6, ($t8)
j AfterIfElse31
AfterIf31:
#about to do rhs in assign
li $s6 0
#just accepted lhs of bin op
li $s7 1
#just accepted rhs of bin op
sub $t8, $s6, $s7
#about to do lhs in assign
la $s7, -12($fp)
sw $t8, ($s7)
j AfterIfElse31
AfterIfElse31:
j AfterIfElse30
AfterIf30:
#about to do rhs in assign
li $s7 0
#about to do lhs in assign
la $t8, -12($fp)
sw $s7, ($t8)
j AfterIfElse30
AfterIfElse30:
j AfterIfElse28
AfterIfElse28:
j AfterIfElse26
AfterIfElse26:
j AfterIfElse25
AfterIf25:
#about to do rhs in assign
li $t8 0
#about to do lhs in assign
la $s7, -12($fp)
sw $t8, ($s7)
j AfterIfElse25
AfterIfElse25:
j AfterIfElse18
AfterIfElse18:
j AfterIfElse11
AfterIfElse11:
la $s7, -12($fp)
lw $s7, ($s7)
move $t9, $s7
j setEnd
setEnd:
move $sp, $fp
jr $ra
	printGame:
move $fp $sp
la $s7, stringLabel1
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel2
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel3
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel4
li $v0, 4
move $a0, $s7
syscall
la $s7, a11
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel5
li $v0, 4
move $a0, $s7
syscall
la $s7, a12
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel6
li $v0, 4
move $a0, $s7
syscall
la $s7, a13
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel7
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel8
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel9
li $v0, 4
move $a0, $s7
syscall
la $s7, a21
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel10
li $v0, 4
move $a0, $s7
syscall
la $s7, a22
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel11
li $v0, 4
move $a0, $s7
syscall
la $s7, a23
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel12
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel13
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel14
li $v0, 4
move $a0, $s7
syscall
la $s7, a31
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel15
li $v0, 4
move $a0, $s7
syscall
la $s7, a32
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel16
li $v0, 4
move $a0, $s7
syscall
la $s7, a33
#just got register for global var expr address
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel17
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel18
li $v0, 4
move $a0, $s7
syscall
la $s7, stringLabel19
li $v0, 4
move $a0, $s7
syscall
printGameEnd:
move $sp, $fp
jr $ra
	printWinner:
move $fp $sp
addi $sp,$sp,-4
la $s7, stringLabel20
li $v0, 4
move $a0, $s7
syscall
la $s7, 0($fp)
lw $s7, ($s7)
li $v0, 1
move $a0, $s7
syscall
la $s7, stringLabel21
li $v0, 4
move $a0, $s7
syscall
printWinnerEnd:
move $sp, $fp
jr $ra
	switchPlayer:
move $fp $sp
addi $sp,$sp,-4
la $t8, 0($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s6 1
#just accepted rhs of bin op
beq $t8, $s6, EqualTo50
li $s7, 0
j AfterEqualTo50
EqualTo50: 
li $s7, 1
AfterEqualTo50:
beq $s7, 0, AfterIf32
li $s7 2
move $t9, $s7
j switchPlayerEnd
j AfterIfElse32
AfterIf32:
li $s7 1
move $t9, $s7
j switchPlayerEnd
j AfterIfElse32
AfterIfElse32:
switchPlayerEnd:
move $sp, $fp
jr $ra
	get_mark:
move $fp $sp
addi $sp,$sp,-4
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $t8 1
#just accepted rhs of bin op
beq $s6, $t8, EqualTo51
li $s7, 0
j AfterEqualTo51
EqualTo51: 
li $s7, 1
AfterEqualTo51:
beq $s7, 0, AfterIf33
la $s7 'X'
move $t9, $s7
j get_markEnd
j AfterIfElse33
AfterIf33:
la $s7 'O'
move $t9, $s7
j get_markEnd
j AfterIfElse33
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
#about to do rhs in assign
li $s7 1
#about to do lhs in assign
la $t8, -12($fp)
sw $s7, ($t8)
MyWhile1:
la $t8, -12($fp)
lw $t8, ($t8)
beq $t8, 0, AfterWhile1
MyWhileStatement1:
la $t8, stringLabel22
li $v0, 4
move $a0, $t8
syscall
la $t8, 0($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $t8, stringLabel23
li $v0, 4
move $a0, $t8
syscall
li $v0, 12
syscall
move $t9, $v0
la $t8, -4($fp)
sw $t9, ($t8)
li $v0, 5
syscall
move $t9, $v0
la $t8, -8($fp)
sw $t9, ($t8)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@29444d75
la $t8, 0($fp)
lw $t8, ($t8)
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 0
jal get_mark
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
la $t8, -20($fp)
sw $t9, ($t8)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@2280cdac
la $t8, -4($fp)
lw $t8, ($t8)
addi $sp,$sp,-4
sw $t8, ($sp)
#Argument ast.VarExpr@1517365b
la $t8, -8($fp)
lw $t8, ($t8)
addi $sp,$sp,-4
sw $t8, ($sp)
#Argument ast.VarExpr@4fccd51b
la $t8, -20($fp)
lw $t8, ($t8)
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 8
jal set
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
la $t8, -16($fp)
sw $t9, ($t8)
la $s7, -16($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $s6 0
#just accepted rhs of bin op
beq $s7, $s6, EqualTo52
li $t8, 0
j AfterEqualTo52
EqualTo52: 
li $t8, 1
AfterEqualTo52:
beq $t8, 0, AfterIf34
la $t8, stringLabel24
li $v0, 4
move $a0, $t8
syscall
j AfterIfElse34
AfterIf34:
la $s6, -16($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $s5 0
#just accepted lhs of bin op
li $s4 1
#just accepted rhs of bin op
sub $s7, $s5, $s4
#just accepted rhs of bin op
beq $s6, $s7, EqualTo54
li $t8, 0
j AfterEqualTo54
EqualTo54: 
li $t8, 1
AfterEqualTo54:
beq $t8, 0, AfterIf35
la $t8, stringLabel25
li $v0, 4
move $a0, $t8
syscall
j AfterIfElse35
AfterIf35:
#about to do rhs in assign
li $t8 0
#about to do lhs in assign
la $s7, -12($fp)
sw $t8, ($s7)
j AfterIfElse35
AfterIfElse35:
j AfterIfElse34
AfterIfElse34:
la $s7, -12($fp)
lw $s7, ($s7)
bne $s7, 0, MyWhileStatement1
AfterWhile1: 
selectmoveEnd:
move $sp, $fp
jr $ra
	won:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
#about to do rhs in assign
li $s7 0
#about to do lhs in assign
la $t8, -4($fp)
sw $s7, ($t8)
la $t8, -4($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $s7, a11
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, 0($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $s7, $s6, EqualTo55
li $t8, 0
j AfterEqualTo55
EqualTo55: 
li $t8, 1
AfterEqualTo55:
beq $t8, 0, AfterIf36
la $s6, a21
#just got register for global var expr address
#just accepted lhs of bin op
lw $s6, ($s6)
la $s7, 0($fp)
#just accepted rhs of bin op
lw $s7, ($s7)
beq $s6, $s7, EqualTo56
li $t8, 0
j AfterEqualTo56
EqualTo56: 
li $t8, 1
AfterEqualTo56:
beq $t8, 0, AfterIf37
la $s7, a31
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, 0($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $s7, $s6, EqualTo57
li $t8, 0
j AfterEqualTo57
EqualTo57: 
li $t8, 1
AfterEqualTo57:
beq $t8, 0, AfterIf38
#about to do rhs in assign
li $t8 1
#about to do lhs in assign
la $s6, -4($fp)
sw $t8, ($s6)
j AfterIfElse38
AfterIf38:
AfterIfElse38:
j AfterIfElse37
AfterIf37:
la $t8, a22
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s7, 0($fp)
#just accepted rhs of bin op
lw $s7, ($s7)
beq $t8, $s7, EqualTo58
li $s6, 0
j AfterEqualTo58
EqualTo58: 
li $s6, 1
AfterEqualTo58:
beq $s6, 0, AfterIf39
la $s7, a33
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8, 0($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s7, $t8, EqualTo59
li $s6, 0
j AfterEqualTo59
EqualTo59: 
li $s6, 1
AfterEqualTo59:
beq $s6, 0, AfterIf40
#about to do rhs in assign
li $s6 1
#about to do lhs in assign
la $t8, -4($fp)
sw $s6, ($t8)
j AfterIfElse40
AfterIf40:
AfterIfElse40:
j AfterIfElse39
AfterIf39:
la $s6, a12
#just got register for global var expr address
#just accepted lhs of bin op
lw $s6, ($s6)
la $s7, 0($fp)
#just accepted rhs of bin op
lw $s7, ($s7)
beq $s6, $s7, EqualTo60
li $t8, 0
j AfterEqualTo60
EqualTo60: 
li $t8, 1
AfterEqualTo60:
beq $t8, 0, AfterIf41
la $s7, a13
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, 0($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $s7, $s6, EqualTo61
li $t8, 0
j AfterEqualTo61
EqualTo61: 
li $t8, 1
AfterEqualTo61:
beq $t8, 0, AfterIf42
#about to do rhs in assign
li $t8 1
#about to do lhs in assign
la $s6, -4($fp)
sw $t8, ($s6)
j AfterIfElse42
AfterIf42:
AfterIfElse42:
j AfterIfElse41
AfterIf41:
AfterIfElse41:
j AfterIfElse39
AfterIfElse39:
j AfterIfElse37
AfterIfElse37:
j AfterIfElse36
AfterIf36:
AfterIfElse36:
la $t8, a12
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s7, 0($fp)
#just accepted rhs of bin op
lw $s7, ($s7)
beq $t8, $s7, EqualTo62
li $s6, 0
j AfterEqualTo62
EqualTo62: 
li $s6, 1
AfterEqualTo62:
beq $s6, 0, AfterIf43
la $s7, a22
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8, 0($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s7, $t8, EqualTo63
li $s6, 0
j AfterEqualTo63
EqualTo63: 
li $s6, 1
AfterEqualTo63:
beq $s6, 0, AfterIf44
la $t8, a32
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s7, 0($fp)
#just accepted rhs of bin op
lw $s7, ($s7)
beq $t8, $s7, EqualTo64
li $s6, 0
j AfterEqualTo64
EqualTo64: 
li $s6, 1
AfterEqualTo64:
beq $s6, 0, AfterIf45
#about to do rhs in assign
li $s6 1
#about to do lhs in assign
la $s7, -4($fp)
sw $s6, ($s7)
j AfterIfElse45
AfterIf45:
AfterIfElse45:
j AfterIfElse44
AfterIf44:
AfterIfElse44:
j AfterIfElse43
AfterIf43:
AfterIfElse43:
la $s6, a13
#just got register for global var expr address
#just accepted lhs of bin op
lw $s6, ($s6)
la $t8, 0($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s6, $t8, EqualTo65
li $s7, 0
j AfterEqualTo65
EqualTo65: 
li $s7, 1
AfterEqualTo65:
beq $s7, 0, AfterIf46
la $t8, a23
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, 0($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $t8, $s6, EqualTo66
li $s7, 0
j AfterEqualTo66
EqualTo66: 
li $s7, 1
AfterEqualTo66:
beq $s7, 0, AfterIf47
la $s6, a33
#just got register for global var expr address
#just accepted lhs of bin op
lw $s6, ($s6)
la $t8, 0($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s6, $t8, EqualTo67
li $s7, 0
j AfterEqualTo67
EqualTo67: 
li $s7, 1
AfterEqualTo67:
beq $s7, 0, AfterIf48
#about to do rhs in assign
li $s7 1
#about to do lhs in assign
la $t8, -4($fp)
sw $s7, ($t8)
j AfterIfElse48
AfterIf48:
AfterIfElse48:
j AfterIfElse47
AfterIf47:
la $s7, a22
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, 0($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $s7, $s6, EqualTo68
li $t8, 0
j AfterEqualTo68
EqualTo68: 
li $t8, 1
AfterEqualTo68:
beq $t8, 0, AfterIf49
la $s6, a31
#just got register for global var expr address
#just accepted lhs of bin op
lw $s6, ($s6)
la $s7, 0($fp)
#just accepted rhs of bin op
lw $s7, ($s7)
beq $s6, $s7, EqualTo69
li $t8, 0
j AfterEqualTo69
EqualTo69: 
li $t8, 1
AfterEqualTo69:
beq $t8, 0, AfterIf50
#about to do rhs in assign
li $t8 1
#about to do lhs in assign
la $s7, -4($fp)
sw $t8, ($s7)
j AfterIfElse50
AfterIf50:
AfterIfElse50:
j AfterIfElse49
AfterIf49:
AfterIfElse49:
j AfterIfElse47
AfterIfElse47:
j AfterIfElse46
AfterIf46:
AfterIfElse46:
la $t8, a21
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, 0($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $t8, $s6, EqualTo70
li $s7, 0
j AfterEqualTo70
EqualTo70: 
li $s7, 1
AfterEqualTo70:
beq $s7, 0, AfterIf51
la $s6, a22
#just got register for global var expr address
#just accepted lhs of bin op
lw $s6, ($s6)
la $t8, 0($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s6, $t8, EqualTo71
li $s7, 0
j AfterEqualTo71
EqualTo71: 
li $s7, 1
AfterEqualTo71:
beq $s7, 0, AfterIf52
la $t8, a23
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, 0($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $t8, $s6, EqualTo72
li $s7, 0
j AfterEqualTo72
EqualTo72: 
li $s7, 1
AfterEqualTo72:
beq $s7, 0, AfterIf53
#about to do rhs in assign
li $s7 1
#about to do lhs in assign
la $s6, -4($fp)
sw $s7, ($s6)
j AfterIfElse53
AfterIf53:
AfterIfElse53:
j AfterIfElse52
AfterIf52:
AfterIfElse52:
j AfterIfElse51
AfterIf51:
AfterIfElse51:
la $s7, a31
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8, 0($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s7, $t8, EqualTo73
li $s6, 0
j AfterEqualTo73
EqualTo73: 
li $s6, 1
AfterEqualTo73:
beq $s6, 0, AfterIf54
la $t8, a32
#just got register for global var expr address
#just accepted lhs of bin op
lw $t8, ($t8)
la $s7, 0($fp)
#just accepted rhs of bin op
lw $s7, ($s7)
beq $t8, $s7, EqualTo74
li $s6, 0
j AfterEqualTo74
EqualTo74: 
li $s6, 1
AfterEqualTo74:
beq $s6, 0, AfterIf55
la $s7, a33
#just got register for global var expr address
#just accepted lhs of bin op
lw $s7, ($s7)
la $t8, 0($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
beq $s7, $t8, EqualTo75
li $s6, 0
j AfterEqualTo75
EqualTo75: 
li $s6, 1
AfterEqualTo75:
beq $s6, 0, AfterIf56
#about to do rhs in assign
li $s6 1
#about to do lhs in assign
la $t8, -4($fp)
sw $s6, ($t8)
j AfterIfElse56
AfterIf56:
AfterIfElse56:
j AfterIfElse55
AfterIf55:
AfterIfElse55:
j AfterIfElse54
AfterIf54:
AfterIfElse54:
la $t8, -4($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $t8, -4($fp)
lw $t8, ($t8)
move $t9, $t8
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
#about to do rhs in assign
la $t8 ' '
#about to do lhs in assign
la $s6, empty
#just got register for global var expr address
sw $t8, ($s6)
#about to do rhs in assign
li $s6 1
#about to do lhs in assign
la $t8, 0($fp)
sw $s6, ($t8)
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
#end of function call expression
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
#end of function call expression
#about to do rhs in assign
li $t8 1
#about to do lhs in assign
la $s6, -4($fp)
sw $t8, ($s6)
MyWhile2:
la $s6, 0($fp)
lw $s6, ($s6)
beq $s6, 0, AfterWhile2
MyWhileStatement2:
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@44e81672
la $s6, -4($fp)
lw $s6, ($s6)
addi $sp,$sp,-4
sw $s6, ($sp)
add $sp, $sp, 0
jal selectmove
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@60215eee
la $s6, -4($fp)
lw $s6, ($s6)
addi $sp,$sp,-4
sw $s6, ($sp)
add $sp, $sp, 0
jal get_mark
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
la $s6, -8($fp)
sw $t9, ($s6)
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
#end of function call expression
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@4ca8195f
la $s6, -8($fp)
lw $s6, ($s6)
addi $sp,$sp,-4
sw $s6, ($sp)
add $sp, $sp, 0
jal won
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
beq $t9, 0, AfterIf57
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@65e579dc
la $t9, -4($fp)
lw $t9, ($t9)
addi $sp,$sp,-4
sw $t9, ($sp)
add $sp, $sp, 0
jal printWinner
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
#about to do rhs in assign
li $t9 0
#about to do lhs in assign
la $s6, 0($fp)
sw $t9, ($s6)
j AfterIfElse57
AfterIf57:
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#push to stack
addi $sp,$sp,-4
sw $s6, ($sp)
add $sp, $sp, -4
jal full
add $sp, $sp, 4
lw $s6 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
#just accepted lhs of bin op
li $s6 1
#just accepted rhs of bin op
beq $t9, $s6, EqualTo76
li $s6, 0
j AfterEqualTo76
EqualTo76: 
li $s6, 1
AfterEqualTo76:
beq $s6, 0, AfterIf58
la $s6, stringLabel26
li $v0, 4
move $a0, $s6
syscall
#about to do rhs in assign
li $s6 0
#about to do lhs in assign
la $s6, 0($fp)
sw $s6, ($s6)
j AfterIfElse58
AfterIf58:
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@61baa894
la $s6, -4($fp)
lw $s6, ($s6)
addi $sp,$sp,-4
sw $s6, ($sp)
add $sp, $sp, 0
jal switchPlayer
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
la $s6, -4($fp)
sw $t9, ($s6)
j AfterIfElse58
AfterIfElse58:
j AfterIfElse57
AfterIfElse57:
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
li $t9 0
#just accepted rhs of bin op
beq $s6, $t9, EqualTo77
li $s6, 0
j AfterEqualTo77
EqualTo77: 
li $s6, 1
AfterEqualTo77:
beq $s6, 0, AfterIf59
la $s6, stringLabel27
li $v0, 4
move $a0, $s6
syscall
li $v0, 12
syscall
move $t9, $v0
la $s6, -12($fp)
sw $t9, ($s6)
la $t9, -12($fp)
#just accepted lhs of bin op
lw $t9, ($t9)
la $s6 'y'
#just accepted rhs of bin op
beq $t9, $s6, EqualTo78
li $s6, 0
j AfterEqualTo78
EqualTo78: 
li $s6, 1
AfterEqualTo78:
beq $s6, 0, AfterIf60
#about to do rhs in assign
li $s6 1
#about to do lhs in assign
la $s6, 0($fp)
sw $s6, ($s6)
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
#end of function call expression
j AfterIfElse60
AfterIf60:
la $s6, -12($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
la $t9 'Y'
#just accepted rhs of bin op
beq $s6, $t9, EqualTo79
li $s6, 0
j AfterEqualTo79
EqualTo79: 
li $s6, 1
AfterEqualTo79:
beq $s6, 0, AfterIf61
#about to do rhs in assign
li $s6 1
#about to do lhs in assign
la $t9, 0($fp)
sw $s6, ($t9)
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
#end of function call expression
j AfterIfElse61
AfterIf61:
AfterIfElse61:
j AfterIfElse60
AfterIfElse60:
j AfterIfElse59
AfterIf59:
AfterIfElse59:
la $t9, 0($fp)
lw $t9, ($t9)
bne $t9, 0, MyWhileStatement2
AfterWhile2: 
mainEnd:
move $sp, $fp
li $v0, 10
syscall
