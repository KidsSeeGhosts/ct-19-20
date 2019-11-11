.data
stringLabel1: .asciiz " note"
stringLabel2: .asciiz ": "

notes: .align 8 
	.space 36

.text

j main
	countCash:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-36
addi $sp,$sp,-4
#about to do rhs in assign
li $t8 0
#about to do lhs in assign
la $s7, -40($fp)
sw $t8, ($s7)
MyWhile1:
la $t8, -40($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s6 9
#just accepted rhs of bin op
slt $s7, $t8, $s6
beq $s7, 0, AfterWhile1
la $s6, -40($fp)
lw $s6, ($s6)
la $t8, notes
#just got register for global var expr address
mul $s6, $s6, 4
sub $t8, $t8, $s6
la $s6, ($t8)
#just accepted lhs of bin op
lw $s6, ($s6)
la $t8, 0($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
slt $s7, $s6, $t8
beq $s7, 1, LessThanOrEqualTo2
beq $s6, $t8, LessThanOrEqualTo2
j AfterLessThanOrEqualTo2
LessThanOrEqualTo2: 
li $s7, 1
AfterLessThanOrEqualTo2: 
beq $s7, 0, AfterIf1
#about to do rhs in assign
la $t8, 0($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, -40($fp)
lw $s6, ($s6)
la $s5, notes
#just got register for global var expr address
mul $s6, $s6, 4
sub $s5, $s5, $s6
la $s6, ($s5)
#just accepted rhs of bin op
lw $s6, ($s6)
div $t8, $s6
mflo $s7
#about to do lhs in assign
la $s6, -40($fp)
lw $s6, ($s6)
la $t8, -4($fp)
mul $s6, $s6, 4
sub $t8, $t8, $s6
la $s6, ($t8)
sw $s7, ($s6)
#about to do rhs in assign
la $s7, 0($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
la $s5, -40($fp)
lw $s5, ($s5)
la $s4, -4($fp)
mul $s5, $s5, 4
sub $s4, $s4, $s5
la $s5, ($s4)
#just accepted lhs of bin op
lw $s5, ($s5)
la $s4, -40($fp)
lw $s4, ($s4)
la $s3, notes
#just got register for global var expr address
mul $s4, $s4, 4
sub $s3, $s3, $s4
la $s4, ($s3)
#just accepted rhs of bin op
lw $s4, ($s4)
mul $t8, $s5, $s4
#just accepted rhs of bin op
sub $s6, $s7, $t8
#about to do lhs in assign
la $t8, 0($fp)
sw $s6, ($t8)
j AfterIfElse1
AfterIf1:
#about to do rhs in assign
li $t8 0
#about to do lhs in assign
la $s6, -40($fp)
lw $s6, ($s6)
la $s7, -4($fp)
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
sw $t8, ($s6)
AfterIfElse1:
#about to do rhs in assign
la $t8, -40($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s7 1
#just accepted rhs of bin op
add $s6, $t8, $s7
#about to do lhs in assign
la $s7, -40($fp)
sw $s6, ($s7)
j MyWhile1
AfterWhile1: 
#about to do rhs in assign
li $s7 0
#about to do lhs in assign
la $s6, -40($fp)
sw $s7, ($s6)
MyWhile2:
la $s7, -40($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $t8 9
#just accepted rhs of bin op
slt $s6, $s7, $t8
beq $s6, 0, AfterWhile2
la $t8, -40($fp)
lw $t8, ($t8)
la $s7, -4($fp)
mul $t8, $t8, 4
sub $s7, $s7, $t8
la $t8, ($s7)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s7 0
#just accepted rhs of bin op
bne $t8, $s7, NotEqualTo8
li $s6, 0
j AfterNotEqualTo8
NotEqualTo8: 
li $s6, 1
AfterNotEqualTo8:
beq $s6, 0, AfterIf2
la $s6, -40($fp)
lw $s6, ($s6)
la $s7, notes
#just got register for global var expr address
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
lw $s6, ($s6)
li $v0, 1
move $a0, $s6
syscall
la $s6, stringLabel1
li $v0, 4
move $a0, $s6
syscall
la $s6, stringLabel2
li $v0, 4
move $a0, $s6
syscall
la $s6, -40($fp)
lw $s6, ($s6)
la $s7, -4($fp)
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
lw $s6, ($s6)
li $v0, 1
move $a0, $s6
syscall
la $s6 '\n'
li $v0, 11
move $a0, $s6
syscall
j AfterIfElse2
AfterIf2:
AfterIfElse2:
#about to do rhs in assign
la $s7, -40($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $t8 1
#just accepted rhs of bin op
add $s6, $s7, $t8
#about to do lhs in assign
la $t8, -40($fp)
sw $s6, ($t8)
j MyWhile2
AfterWhile2: 
countCashEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
#about to do rhs in assign
li $t8 2000
#about to do lhs in assign
li $s6 0
la $s7, notes
#just got register for global var expr address
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
sw $t8, ($s6)
#about to do rhs in assign
li $s6 500
#about to do lhs in assign
li $t8 1
la $s7, notes
#just got register for global var expr address
mul $t8, $t8, 4
sub $s7, $s7, $t8
la $t8, ($s7)
sw $s6, ($t8)
#about to do rhs in assign
li $t8 200
#about to do lhs in assign
li $s6 2
la $s7, notes
#just got register for global var expr address
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
sw $t8, ($s6)
#about to do rhs in assign
li $s6 100
#about to do lhs in assign
li $t8 3
la $s7, notes
#just got register for global var expr address
mul $t8, $t8, 4
sub $s7, $s7, $t8
la $t8, ($s7)
sw $s6, ($t8)
#about to do rhs in assign
li $t8 50
#about to do lhs in assign
li $s6 4
la $s7, notes
#just got register for global var expr address
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
sw $t8, ($s6)
#about to do rhs in assign
li $s6 20
#about to do lhs in assign
li $t8 5
la $s7, notes
#just got register for global var expr address
mul $t8, $t8, 4
sub $s7, $s7, $t8
la $t8, ($s7)
sw $s6, ($t8)
#about to do rhs in assign
li $t8 10
#about to do lhs in assign
li $s6 6
la $s7, notes
#just got register for global var expr address
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
sw $t8, ($s6)
#about to do rhs in assign
li $s6 5
#about to do lhs in assign
li $t8 7
la $s7, notes
#just got register for global var expr address
mul $t8, $t8, 4
sub $s7, $s7, $t8
la $t8, ($s7)
sw $s6, ($t8)
#about to do rhs in assign
li $t8 1
#about to do lhs in assign
li $s6 8
la $s7, notes
#just got register for global var expr address
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
sw $t8, ($s6)
#about to do rhs in assign
li $s6 2341
#about to do lhs in assign
la $t8, 0($fp)
sw $s6, ($t8)
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
jal countCash
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
mainEnd:
move $sp, $fp
li $v0, 10
syscall
