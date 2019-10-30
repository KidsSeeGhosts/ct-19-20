.data


.text

j main
	main:
li $t8 0
la $s7, 0($sp)
mul $t8, $t8, 4
add $s7, $s7, $t8
la $s6, ($s7)
li $s7 'a'
sw $s7, ($s6)
li $s7 1
la $t8, 0($sp)
mul $s7, $s7, 4
add $t8, $t8, $s7
la $s5, ($t8)
li $t8 'b'
sw $t8, ($s5)
la $t8, 0($sp)
li $v0, 4
move $a0, $t8
syscall
mainEnd:
li $v0, 10
syscall
