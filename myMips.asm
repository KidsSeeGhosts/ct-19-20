.data
stringLabel1: .asciiz "First "
stringLabel2: .asciiz " terms of Fibonacci series are : "
stringLabel3: .asciiz " "


.text

j main
	main:
la $t8, 20($sp)
li $v0, 5
syscall
move $t9, $v0
sw $t9, ($t8)
la $s7, 24($sp)
li $s6 0
sw $s6, ($s7)
la $s6, 28($sp)
li $s5 1
sw $s5, ($s6)
la $s5, stringLabel1
li $v0, 4
move $a0, $s5
syscall
la $s4, 20($sp)
lw $s4, ($s4)
li $v0, 1
move $a0, $s4
syscall
la $s4, stringLabel2
li $v0, 4
move $a0, $s4
syscall
la $s3, 36($sp)
li $s2 0
sw $s2, ($s3)
la $s2, 36($sp)
lw $s2, ($s2)
la $s1, 20($sp)
lw $s1, ($s1)
slt $s0, $s2, $s1
li $s1, 1
MyWhile1: bne $s0, $s1, AfterWhile1
la $s2, 36($sp)
lw $s2, ($s2)
li $t7 1
slt $t6, $s2, $t7
beq $t6, 1, LessThanOrEqualTo2
beq $s2, $t7, LessThanOrEqualTo2
j AfterLessThanOrEqualTo2
LessThanOrEqualTo2: 
li $t6, 1
AfterLessThanOrEqualTo2: 
li $t7, 1
bne $t6, $t7, AfterIf1
la $s2, 32($sp)
la $t5, 36($sp)
lw $t5, ($t5)
sw $t5, ($s2)
j AfterIfElse1
AfterIf1:
la $t5, 32($sp)
la $t4, 24($sp)
lw $t4, ($t4)
la $t3, 28($sp)
lw $t3, ($t3)
add $t2, $t4, $t3
sw $t2, ($t5)
la $t2, 24($sp)
la $t3, 28($sp)
lw $t3, ($t3)
sw $t3, ($t2)
la $t3, 28($sp)
la $t4, 32($sp)
lw $t4, ($t4)
sw $t4, ($t3)
AfterIfElse1:
la $t6, 32($sp)
lw $t6, ($t6)
li $v0, 1
move $a0, $t6
syscall
la $t6, stringLabel3
li $v0, 4
move $a0, $t6
syscall
la $t7, 36($sp)
la $t4, 36($sp)
lw $t4, ($t4)
li $t1 1
add $t0, $t4, $t1
sw $t0, ($t7)
la $t0, 36($sp)
lw $t0, ($t0)
la $t1, 20($sp)
lw $t1, ($t1)
slt $t4, $t0, $t1
move $s0, $t4
j MyWhile1
AfterWhile1: 
mainEnd:
li $v0, 10
syscall
