.data
hello: .space 20

a: .space 20

.text

j main
	binarySearch:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
#about to do rhs in assign
li $t8 2
#about to do lhs in assign
li $s7 0
la $s6, a
#just got register for global var expr address
add $s6, $s6, 0
la $s5, ($s6)
mul $s7, $s7, 4
add $s5, $s5, $s7
la $s7, ($s5)
sw $t8, ($s7)
#about to do rhs in assign
li $s7 3
#about to do lhs in assign
li $t8 1
la $s5, a
#just got register for global var expr address
add $s5, $s5, 0
la $s6, ($s5)
mul $t8, $t8, 4
add $s6, $s6, $t8
la $t8, ($s6)
sw $s7, ($t8)
#about to do rhs in assign
li $t8 4
#about to do lhs in assign
li $s7 2
la $s6, a
#just got register for global var expr address
add $s6, $s6, 0
la $s5, ($s6)
mul $s7, $s7, 4
add $s5, $s5, $s7
la $s7, ($s5)
sw $t8, ($s7)
#about to do rhs in assign
li $s7 10
#about to do lhs in assign
li $t8 3
la $s5, a
#just got register for global var expr address
add $s5, $s5, 0
la $s6, ($s5)
mul $t8, $t8, 4
add $s6, $s6, $t8
la $t8, ($s6)
sw $s7, ($t8)
#about to do rhs in assign
li $t8 40
#about to do lhs in assign
li $s7 4
la $s6, a
#just got register for global var expr address
add $s6, $s6, 0
la $s5, ($s6)
mul $s7, $s7, 4
add $s5, $s5, $s7
la $s7, ($s5)
sw $t8, ($s7)
MyWhile1:
la $t8, 0($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
la $s5, -4($fp)
#just accepted rhs of bin op
lw $s5, ($s5)
slt $s7, $t8, $s5
beq $s7, 1, LessThanOrEqualTo1
beq $t8, $s5, LessThanOrEqualTo1
j AfterLessThanOrEqualTo1
LessThanOrEqualTo1: 
li $s7, 1
AfterLessThanOrEqualTo1: 
beq $s7, 0, AfterWhile1
MyWhileStatement1:
addi $sp,$sp,-4
#about to do rhs in assign
la $s5, 0($fp)
#just accepted lhs of bin op
lw $s5, ($s5)
la $s4, -4($fp)
#just accepted lhs of bin op
lw $s4, ($s4)
la $s3, 0($fp)
#just accepted rhs of bin op
lw $s3, ($s3)
sub $s6, $s4, $s3
#just accepted lhs of bin op
li $s3 2
#just accepted rhs of bin op
div $s6, $s3
mflo $t8
#just accepted rhs of bin op
add $s7, $s5, $t8
#about to do lhs in assign
la $t8, -12($fp)
sw $s7, ($t8)
la $s7, -12($fp)
lw $s7, ($s7)
la $s5, a
#just got register for global var expr address
add $s5, $s5, 0
la $s3, ($s5)
mul $s7, $s7, 4
add $s3, $s3, $s7
la $s7, ($s3)
#just accepted lhs of bin op
lw $s7, ($s7)
la $s3, -8($fp)
#just accepted rhs of bin op
lw $s3, ($s3)
beq $s7, $s3, EqualTo5
li $t8, 0
j AfterEqualTo5
EqualTo5: 
li $t8, 1
AfterEqualTo5:
beq $t8, 0, AfterIf1
la $t8, -12($fp)
lw $t8, ($t8)
move $t9, $t8
j binarySearchEnd
j AfterIfElse1
AfterIf1:
AfterIfElse1:
la $s3, -12($fp)
lw $s3, ($s3)
la $s7, a
#just got register for global var expr address
add $s7, $s7, 0
la $s5, ($s7)
mul $s3, $s3, 4
add $s5, $s5, $s3
la $s3, ($s5)
#just accepted lhs of bin op
lw $s3, ($s3)
la $s5, -8($fp)
#just accepted rhs of bin op
lw $s5, ($s5)
slt $t8, $s3, $s5
beq $t8, 0, AfterIf2
#about to do rhs in assign
la $s5, -12($fp)
#just accepted lhs of bin op
lw $s5, ($s5)
li $s3 1
#just accepted rhs of bin op
add $t8, $s5, $s3
#about to do lhs in assign
la $s3, 0($fp)
sw $t8, ($s3)
j AfterIfElse2
AfterIf2:
#about to do rhs in assign
la $t8, -12($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s5 1
#just accepted rhs of bin op
sub $s3, $t8, $s5
#about to do lhs in assign
la $s5, -4($fp)
sw $s3, ($s5)
j AfterIfElse2
AfterIfElse2:
la $s3, 0($fp)
#just accepted lhs of bin op
lw $s3, ($s3)
la $t8, -4($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
slt $s5, $s3, $t8
beq $s5, 1, LessThanOrEqualTo9
beq $s3, $t8, LessThanOrEqualTo9
j AfterLessThanOrEqualTo9
LessThanOrEqualTo9: 
li $s5, 1
AfterLessThanOrEqualTo9: 
bne $s5, 0, MyWhileStatement1
AfterWhile1: 
li $t8 0
#just accepted lhs of bin op
li $s3 1
#just accepted rhs of bin op
sub $s5, $t8, $s3
move $t9, $s5
j binarySearchEnd
binarySearchEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
#about to do rhs in assign
li $s5 10
#about to do lhs in assign
la $s3, -4($fp)
sw $s5, ($s3)
#about to do rhs in assign
li $s5 5
#just accepted lhs of bin op
li $t8 1
#just accepted rhs of bin op
div $s5, $t8
mflo $s3
#about to do lhs in assign
la $t8, 0($fp)
sw $s3, ($t8)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.IntLiteral@1517365b
li $t8 0
addi $sp,$sp,-4
sw $t8, ($sp)
#Argument ast.BinOp@4fccd51b
la $s3, 0($fp)
#just accepted lhs of bin op
lw $s3, ($s3)
li $s5 1
#just accepted rhs of bin op
sub $t8, $s3, $s5
addi $sp,$sp,-4
sw $t8, ($sp)
#Argument ast.VarExpr@44e81672
la $t8, -4($fp)
lw $t8, ($t8)
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 8
jal binarySearch
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
la $t8, -8($fp)
sw $t9, ($t8)
la $t8, -8($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
li $t8 0
move $t9, $t8
j mainEnd
mainEnd:
move $sp, $fp
li $v0, 10
syscall
