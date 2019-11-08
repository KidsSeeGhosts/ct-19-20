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
li $t8 0
move $t9, $t8
j findEnd
j AfterIfElse1
AfterIf1:
la $s7, 0($fp)
lw $s7, ($s7)
li $s5 2
div $s7, $s5
mfhi $s6
li $s7 10
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
addi $sp,$sp,-4
sw $s5, ($sp)
addi $sp,$sp,-4
sw $s6, ($sp)
addi $sp,$sp,-4
sw $s7, ($sp)
addi $sp,$sp,-4
sw $t8, ($sp)
la $s7, 0($fp)
lw $s7, ($s7)
li $s6 2
div $s7, $s6
mflo $t8
add $sp, $sp, -4
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
mul $s5, $s7, $t9
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
la $t8, 0($fp)
li $s5 10
sw $s5, ($t8)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, 0($fp)
lw $t8, ($t8)
#basetype
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 0
jal find
add $sp, $sp, 4
lw $t8 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
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
