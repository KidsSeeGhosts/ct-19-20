.data


.text

j main
	find:
move $fp $sp
addi $sp,$sp,-4
la $s7, 0($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $s6 0
#just accepted rhs of bin op
beq $s7, $s6, EqualTo1
li $t8, 0
j AfterEqualTo1
EqualTo1: 
li $t8, 1
AfterEqualTo1:
beq $t8, 0, AfterIf1
li $t8 0
move $t9, $t8
j findEnd
j AfterIfElse1
AfterIf1:
la $s7, 0($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $s5 2
#just accepted rhs of bin op
div $s7, $s5
mfhi $s6
#just accepted lhs of bin op
li $s7 10
#just accepted lhs of bin op
#pushing regs
addi $sp,$sp,-4 #hello
sw $fp, ($sp)
addi $sp,$sp,-4 #hello
sw $ra, ($sp)
#push to stack
addi $sp,$sp,-4 #hello
sw $s5, ($sp)
#push to stack
addi $sp,$sp,-4 #hello
sw $s6, ($sp)
#push to stack
addi $sp,$sp,-4 #hello
sw $s7, ($sp)
#push to stack
addi $sp,$sp,-4 #hello
sw $t8, ($sp)
#Argument ast.BinOp@3f102e87
la $s7, 0($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $s6 2
#just accepted rhs of bin op
div $s7, $s6
mflo $t8
addi $sp,$sp,-4 #hello
sw $t8, ($sp)
add $sp, $sp, 0
jal find
add $sp, $sp, 4
lw $t8 ,($sp)
addiu $sp,$sp,4
lw $s7 ,($sp)
addiu $sp,$sp,4
lw $s6 ,($sp)
addiu $sp,$sp,4
lw $s5 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
#just accepted rhs of bin op
mul $s5, $s7, $t9
#just accepted rhs of bin op
add $t8, $s6, $s5
move $t9, $t8
j findEnd
AfterIfElse1:
findEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
#about to do rhs in assign
li $t8 69
#about to do lhs in assign
la $s5, 0($fp)
sw $t8, ($s5)
#pushing regs
addi $sp,$sp,-4 #hello
sw $fp, ($sp)
addi $sp,$sp,-4 #hello
sw $ra, ($sp)
#Argument ast.VarExpr@27abe2cd
la $s5, 0($fp)
lw $s5, ($s5)
addi $sp,$sp,-4 #hello
sw $s5, ($sp)
add $sp, $sp, 0
jal find
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
li $v0, 1
move $a0, $t9
syscall
li $t9 0
move $t9, $t9
j mainEnd
mainEnd:
move $sp, $fp
li $v0, 10
syscall
