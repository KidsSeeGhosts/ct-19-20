.data


.text

j main
	main:
la $t8, 12($sp)
li $s7 5
sw $s7, ($t8)
la $s7, 16($sp)
li $s6 1
sw $s6, ($s7)
la $s6, 12($sp)
lw $s6, ($s6)
li $s5 5
beq $s6, $s5, EqualTo1
li $s4, 0
j AfterEqualTo1
EqualTo1: 
li $s4, 1
AfterEqualTo1:
beq $s4, 1 TrueOR2
la $s6, 12($sp)
lw $s6, ($s6)
li $s3 5
beq $s6, $s3, EqualTo2
li $s2, 0
j AfterEqualTo2
EqualTo2: 
li $s2, 1
AfterEqualTo2:
beq $s2,1 TrueOR3
li $s5, 0
j AfterOR3
TrueOR3: 
li $s5, 1
AfterOR3: 
li $s2, 1
bne $s5, $s2, AfterIf1
la $s4, 12($sp)
lw $s4, ($s4)
li $v0, 1
move $a0, $s4
syscall
j AfterIfElse1
AfterIf1:
AfterIfElse1:
mainEnd:
li $v0, 10
syscall
