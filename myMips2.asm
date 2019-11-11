.data
a: .space 8
hello: .space 28


.text

j main
	main:
move $fp $sp
addi $sp,$sp,-28
li $t8, 28
li $v0, 1
move $a0, $t8
syscall
mainEnd:
move $sp, $fp
li $v0, 10
syscall
