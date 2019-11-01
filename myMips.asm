.data
yolo: .space 28

x: .space 28

.text

j main
	main:
la $s7, x
la $s6, 4($s7)
li $s7 5
sw $s7, ($s6)
la $s5, x
la $s4, 4($s5)
lw $s4, ($s4)
li $v0, 1
move $a0, $s4
syscall
mainEnd:
li $v0, 10
syscall
