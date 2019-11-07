.data


.text

j main
	find:
move $fp $sp
addi $sp,$sp,-4
la $s7, 0($fp)
lw $s7, ($s7)
li $s6 0
beq $s7, $s6, EqualTo1
li $t8, 0
j AfterEqualTo1
EqualTo1: 
li $t8, 1
AfterEqualTo1:
beq $t8, 0, AfterIf1
li $s7 0
move $t9, $s7
j findEnd
j AfterIfElse1
AfterIf1:
la $s3, 0($fp)
lw $s3, ($s3)
li $s2 2
div $s3, $s2
mfhi $s4
li $s3 10
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
addi $sp,$sp,-4
sw $s2, ($sp)
addi $sp,$sp,-4
sw $s3, ($sp)
addi $sp,$sp,-4
sw $s4, ($sp)
addi $sp,$sp,-4
sw $s5, ($sp)
addi $sp,$sp,-4
sw $s6, ($sp)
addi $sp,$sp,-4
sw $s7, ($sp)
addi $sp,$sp,-4
sw $t8, ($sp)
la $s6, 0($fp)
lw $s6, ($s6)
li $s5 2
div $s6, $s5
mflo $s7
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
lw $s4 ,($sp)
addiu $sp,$sp,4
lw $s3 ,($sp)
addiu $sp,$sp,4
lw $s2 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
mul $s2, $s3, $t9
add $s5, $s4, $s2
move $t9, $s5
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
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
addi $sp,$sp,-4
sw $s7, ($sp)
addi $sp,$sp,-4
sw $t8, ($sp)
la $s7, 0($fp)
lw $s7, ($s7)
addi $sp,$sp,-4
sw $s7, ($sp)
add $sp, $sp, 0
jal find
add $sp, $sp, 4
lw $t8 ,($sp)
addiu $sp,$sp,4
lw $s7 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
li $v0, 1
move $a0, $t9
syscall
li $s7 0
move $t9, $s7
j mainEnd
mainEnd:
move $sp, $fp
li $v0, 10
syscall
