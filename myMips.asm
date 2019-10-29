.data


.text

	main:
la $t8, 0($sp)
li $s7 4
sw $s7, ($t8)
la $s6, 0($sp)
lw $s6, ($s6)
li $s5 5
slt $s4, $s6, $s5
li $s5, 1
bne $s4, $s5, AfterIf1
la $s6, 0($sp)
lw $s6, ($s6)
li $v0, 1
move $a0, $s6
syscall
AfterIf1: 
mainEnd:
li $v0, 10
syscall
