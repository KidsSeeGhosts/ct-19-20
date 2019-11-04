.data


.text

j main
	bar:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
la $t8, 0($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $t8, -4($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $t8, 0($fp)
lw $t8, ($t8)
la $s7, -4($fp)
lw $s7, ($s7)
add $s6, $t8, $s7
move $t9, $s6
j barEnd
barEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
la $s6, 0($fp)
li $s7 5
sw $s7, ($s6)
la $s7, -4($fp)
li $t8 8
sw $t8, ($s7)
la $t8, -4($fp)
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
addi $sp,$sp,-4
sw $s6, ($sp)
addi $sp,$sp,-4
sw $s7, ($sp)
addi $sp,$sp,-4
sw $t8, ($sp)
la $s4, 0($fp)
lw $s4, ($s4)
addi $sp,$sp,-4
sw $s4, ($sp)
la $s3, -4($fp)
lw $s3, ($s3)
addi $sp,$sp,-4
sw $s3, ($sp)
add $sp, $sp, 4
jal bar
add $sp, $sp, 4
lw $t8 ,($sp)
addiu $sp,$sp,4
lw $s7 ,($sp)
addiu $sp,$sp,4
lw $s6 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
sw $t9, ($t8)
la $t9, -4($fp)
lw $t9, ($t9)
li $v0, 1
move $a0, $t9
syscall
mainEnd:
move $sp, $fp
li $v0, 10
syscall
