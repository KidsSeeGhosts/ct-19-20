.data


.text

j main
	main:
la $t8, 4($sp)
li $s7 1
sw $s7, ($t8)
la $s7, 8($sp)
li $s6 1
sw $s6, ($s7)
la $s6, 4($sp)
lw $s6, ($s6)
li $s5 5
slt $s4, $s6, $s5
li $s5, 1
MyWhile1: bne $s4, $s5, AfterWhile1
la $s6, 8($sp)
lw $s6, ($s6)
li $s3 5
slt $s2, $s6, $s3
li $s3, 1
MyWhile2: bne $s2, $s3, AfterWhile2
la $s6, 8($sp)
la $s1, 8($sp)
lw $s1, ($s1)
li $s0 1
add $t7, $s1, $s0
sw $t7, ($s6)
la $t7, 8($sp)
lw $t7, ($t7)
li $v0, 1
move $a0, $t7
syscall
la $t7, 8($sp)
lw $t7, ($t7)
li $s0 5
slt $s1, $t7, $s0
move $s2, $s1
j MyWhile2
AfterWhile2: 
la $s2, 4($sp)
la $s3, 4($sp)
lw $s3, ($s3)
li $s0 1
add $t7, $s3, $s0
sw $t7, ($s2)
la $t7, 4($sp)
lw $t7, ($t7)
li $v0, 1
move $a0, $t7
syscall
la $t7, 4($sp)
lw $t7, ($t7)
li $s0 5
slt $s3, $t7, $s0
move $s4, $s3
j MyWhile1
AfterWhile1: 
mainEnd:
li $v0, 10
syscall
