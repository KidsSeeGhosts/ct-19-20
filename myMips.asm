.data


.text

j main
	hello:
li $s7 4
move $t9, $s7
j helloEnd
helloEnd:
jr $ra
	main:
la $s6, 0($sp)
jal hello
sw $t9, ($s6)
la $s5, 0($sp)
lw $s5, ($s5)
li $v0, 1
move $a0, $s5
syscall
mainEnd:
li $v0, 10
syscall
