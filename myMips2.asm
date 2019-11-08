.data


.text

j main
	find:
move $fp $sp
addi $sp,$sp,-4
la $s7, 0($fp)
lw $s7, ($s7)
li $s6 0
seq $t8, $s7, $s6
beq $t8, 0, AfterIf1
li $s6 0
move $t9, $s6
j findEnd
j AfterIfElse1
AfterIf1:
la $s5, 0($fp)
lw $s5, ($s5)
li $s4 2
div $s5, $s4
mfhi $s7
li $s5 10
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#push to stack
addi $sp,$sp,-4
sw $s4, ($sp)
#push to stack
addi $sp,$sp,-4
sw $s5, ($sp)
#push to stack
addi $sp,$sp,-4
sw $s6, ($sp)
#push to stack
addi $sp,$sp,-4
sw $s7, ($sp)
#push to stack
addi $sp,$sp,-4
sw $t8, ($sp)
#Argument ast.BinOp@3f102e87
la $s7, 0($fp)
lw $s7, ($s7)
li $s6 2
div $s7, $s6
mflo $t8
addi $sp,$sp,-4
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
lw $s4 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
mul $s4, $s5, $t9
add $s6, $s7, $s4
move $t9, $s6
j findEnd
AfterIfElse1:
findEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
li $t8 10
la $s6, 0($fp)
sw $t8, ($s6)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.VarExpr@27abe2cd
la $s6, 0($fp)
lw $s6, ($s6)
addi $sp,$sp,-4
sw $s6, ($sp)
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
