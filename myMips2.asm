.data
stringLabel1: .asciiz "Element is not present in array"
stringLabel2: .asciiz "Element is present at index "


.text

j main
	binarySearch:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
MyWhile1:
la $s7, -4($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
la $s6, -8($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
slt $t8, $s7, $s6
beq $t8, 1, LessThanOrEqualTo1
beq $s7, $s6, LessThanOrEqualTo1
j AfterLessThanOrEqualTo1
LessThanOrEqualTo1: 
li $t8, 1
AfterLessThanOrEqualTo1: 
beq $t8, 0, AfterWhile1
addi $sp,$sp,-4
la $s6, -4($fp)
#just accepted lhs of bin op
lw $s6, ($s6)
la $s4, -8($fp)
#just accepted lhs of bin op
lw $s4, ($s4)
la $s3, -4($fp)
#just accepted rhs of bin op
lw $s3, ($s3)
sub $s5, $s4, $s3
#just accepted lhs of bin op
li $s3 2
#just accepted rhs of bin op
div $s5, $s3
mflo $s7
#just accepted rhs of bin op
add $t8, $s6, $s7
la $s7, -16($fp)
sw $t8, ($s7)
la $t8, -16($fp)
la $s6, 0($fp)
mul $t8, $t8, 4
add $s6, $s6, $t8
la $t8, ($s6)
#just accepted lhs of bin op
lw $t8, ($t8)
la $s6, -12($fp)
#just accepted rhs of bin op
lw $s6, ($s6)
beq $t8, $s6, EqualTo5
li $s7, 0
j AfterEqualTo5
EqualTo5: 
li $s7, 1
AfterEqualTo5:
beq $s7, 0, AfterIf1
la $s7, -16($fp)
lw $s7, ($s7)
move $t9, $s7
j binarySearchEnd
j AfterIfElse1
AfterIf1:
AfterIfElse1:
la $s6, -16($fp)
la $t8, 0($fp)
mul $s6, $s6, 4
add $t8, $t8, $s6
la $s6, ($t8)
#just accepted lhs of bin op
lw $s6, ($s6)
la $t8, -12($fp)
#just accepted rhs of bin op
lw $t8, ($t8)
slt $s7, $s6, $t8
beq $s7, 0, AfterIf2
la $t8, -16($fp)
#just accepted lhs of bin op
lw $t8, ($t8)
li $s6 1
#just accepted rhs of bin op
add $s7, $t8, $s6
la $s6, -4($fp)
sw $s7, ($s6)
j AfterIfElse2
AfterIf2:
la $s7, -16($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $t8 1
#just accepted rhs of bin op
sub $s6, $s7, $t8
la $t8, -8($fp)
sw $s6, ($t8)
AfterIfElse2:
j MyWhile1
AfterWhile1: 
li $s6 0
#just accepted lhs of bin op
li $s7 1
#just accepted rhs of bin op
sub $t8, $s6, $s7
move $t9, $t8
j binarySearchEnd
binarySearchEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-20
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
li $t8 2
li $s7 0
la $s6, 0($fp)
mul $s7, $s7, 4
add $s6, $s6, $s7
la $s7, ($s6)
sw $t8, ($s7)
li $s7 3
li $t8 1
la $s6, 0($fp)
mul $t8, $t8, 4
add $s6, $s6, $t8
la $t8, ($s6)
sw $s7, ($t8)
li $t8 4
li $s7 2
la $s6, 0($fp)
mul $s7, $s7, 4
add $s6, $s6, $s7
la $s7, ($s6)
sw $t8, ($s7)
li $s7 10
li $t8 3
la $s6, 0($fp)
mul $t8, $t8, 4
add $s6, $s6, $t8
la $t8, ($s6)
sw $s7, ($t8)
li $t8 40
li $s7 4
la $s6, 0($fp)
mul $s7, $s7, 4
add $s6, $s6, $s7
la $s7, ($s6)
sw $t8, ($s7)
li $s7 10
la $t8, -24($fp)
sw $s7, ($t8)
li $s7 5
#just accepted lhs of bin op
li $s6 1
#just accepted rhs of bin op
div $s7, $s6
mflo $t8
la $s6, -20($fp)
sw $t8, ($s6)
la $s6, 0($fp)
la $t8, -32($fp)
sw $s6, ($t8)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@2280cdac
la $t8, -32($fp)
lw $t8, ($t8)
addi $sp,$sp,-4
sw $t8, ($sp)
#Argument ast.IntLiteral@1517365b
li $t8 0
#Argument ast.BinOp@4fccd51b
la $s7, -20($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $s3 1
#just accepted rhs of bin op
sub $s6, $s7, $s3
addi $sp,$sp,-4
sw $s6, ($sp)
#Argument ast.VarExpr@44e81672
la $s6, -24($fp)
lw $s6, ($s6)
add $sp, $sp, 4
jal binarySearch
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
la $s3, -28($fp)
sw $t9, ($s3)
la $s7, -28($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $s4 0
#just accepted lhs of bin op
li $s2 1
#just accepted rhs of bin op
sub $s5, $s4, $s2
#just accepted rhs of bin op
beq $s7, $s5, EqualTo13
li $s3, 0
j AfterEqualTo13
EqualTo13: 
li $s3, 1
AfterEqualTo13:
beq $s3, 0, AfterIf3
la $s3, stringLabel1
li $v0, 4
move $a0, $s3
syscall
j AfterIfElse3
AfterIf3:
la $s3, stringLabel2
li $v0, 4
move $a0, $s3
syscall
la $s3, -28($fp)
lw $s3, ($s3)
li $v0, 1
move $a0, $s3
syscall
la $s3 '\n'
li $v0, 11
move $a0, $s3
syscall
AfterIfElse3:
li $s3 0
move $t9, $s3
j mainEnd
mainEnd:
move $sp, $fp
li $v0, 10
syscall
