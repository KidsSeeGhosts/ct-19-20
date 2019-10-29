.data
stringLabel1: .asciiz "hello world"

.text

	main:
la $t8, 0($sp)
la $s7, stringLabel1
sw $s7, ($t8)
la $s6, 0($sp)
lw $s6, ($s6)
li $v0, 4
move $a0, $s6
syscall
li $v0, 10
syscall
