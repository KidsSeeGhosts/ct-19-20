.data


.text

j main
	binarySearch:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-20
#about to do rhs in assign
li $t8 2
#about to do lhs in assign
li $s7 0
la $s6, -12($fp)
mul $s7, $s7, 4
sub $s6, $s6, $s7
la $s7, ($s6)
sw $t8, ($s7)
#about to do rhs in assign
li $s7 3
#about to do lhs in assign
li $t8 1
la $s6, -12($fp)
mul $t8, $t8, 4
sub $s6, $s6, $t8
la $t8, ($s6)
sw $s7, ($t8)
#about to do rhs in assign
li $t8 4
#about to do lhs in assign
li $s7 2
la $s6, -12($fp)
mul $s7, $s7, 4
sub $s6, $s6, $s7
la $s7, ($s6)
sw $t8, ($s7)
#about to do rhs in assign
li $s7 10
#about to do lhs in assign
li $t8 3
la $s6, -12($fp)
mul $t8, $t8, 4
sub $s6, $s6, $t8
la $t8, ($s6)
sw $s7, ($t8)
#about to do rhs in assign
li $t8 40
#about to do lhs in assign
li $s7 4
la $s6, -12($fp)
mul $s7, $s7, 4
sub $s6, $s6, $s7
la $s7, ($s6)
sw $t8, ($s7)
MyWhile1:
la $t8, 0($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, -4($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
slt $s7, $t8, $s6
beq $s7, 1, LessThanOrEqualTo1
beq $t8, $s6, LessThanOrEqualTo1
j AfterLessThanOrEqualTo1
LessThanOrEqualTo1: 
li $s7, 1
AfterLessThanOrEqualTo1: 
beq $s7, 0, AfterWhile1
addi $sp,$sp,-4
#about to do rhs in assign
la $s6, 0($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
la $s4, -4($fp)
#just accepted lhs of bin op
lw $s4, ($s4)
la $s3, 0($fp)
#just accepted rhs of bin op
lw $s3, ($s3)
sub $s5, $s4, $s3
#just accepted lhs of bin op
li $s3 2
#just accepted rhs of bin op
div $s5, $s3
mflo $t8
#just accepted rhs of bin op
add $s7, $s6, $t8
#about to do lhs in assign
la $t8, -32($fp)
sw $s7, ($t8)
la $s7, -32($fp)
lw $s7, ($s7)
la $s6, -12($fp)
mul $s7, $s7, 4
sub $s6, $s6, $s7
la $s7, ($s6)
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, -8($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $s7, $s6, EqualTo5
li $t8, 0
j AfterEqualTo5
EqualTo5: 
li $t8, 1
AfterEqualTo5:
beq $t8, 0, AfterIf1
la $t8, -32($fp)
lw $t8, ($t8)
move $t9, $t8
j binarySearchEnd
j AfterIfElse1
AfterIf1:
AfterIfElse1:
la $s6, -32($fp)
lw $s6, ($s6)
la $s7, -12($fp)
mul $s6, $s6, 4
sub $s7, $s7, $s6
la $s6, ($s7)
#just accepted lhs of bin op
lw $s6, ($s6)
la $s7, -8($fp)
#just accepted rhs of bin op
lw $s7, ($s7)
slt $t8, $s6, $s7
beq $t8, 0, AfterIf2
#about to do rhs in assign
la $s7, -32($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $s6 1
#just accepted rhs of bin op
add $t8, $s7, $s6
#about to do lhs in assign
la $s6, 0($fp)
sw $t8, ($s6)
j AfterIfElse2
AfterIf2:
#about to do rhs in assign
la $t8, -32($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s7 1
#just accepted rhs of bin op
sub $s6, $t8, $s7
#about to do lhs in assign
la $s7, -4($fp)
sw $s6, ($s7)
AfterIfElse2:
j MyWhile1
AfterWhile1: 
li $s6 0
#just accepted lhs of bin op
li $t8 1
#just accepted rhs of bin op
sub $s7, $s6, $t8
move $t9, $s7
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
li $s7 10
#about to do lhs in assign
la $t8, -4($fp)
sw $s7, ($t8)
#about to do rhs in assign
li $s7 5
#just accepted lhs of bin op
li $s6 1
#just accepted rhs of bin op
div $s7, $s6
mflo $t8
#about to do lhs in assign
la $s6, 0($fp)
sw $t8, ($s6)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.IntLiteral@6fdb1f78
li $s6 0
addi $sp,$sp,-4
sw $s6, ($sp)
#Argument ast.BinOp@51016012
la $t8, 0($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s7 1
#just accepted rhs of bin op
sub $s6, $t8, $s7
addi $sp,$sp,-4
sw $s6, ($sp)
#Argument ast.VarExpr@29444d75
la $s6, -4($fp)
lw $s6, ($s6)
addi $sp,$sp,-4
sw $s6, ($sp)
add $sp, $sp, 8
jal binarySearch
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
la $s6, -8($fp)
sw $t9, ($s6)
la $s6, -8($fp)
lw $s6, ($s6)
li $v0, 1
move $a0, $s6
syscall
li $s6 0
move $t9, $s6
j mainEnd
mainEnd:
move $sp, $fp
li $v0, 10
syscall
