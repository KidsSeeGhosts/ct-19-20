.data
stringLabel1: .asciiz "Something!"
stringLabel2: .asciiz "Back at main again!"
stringLabel3: .asciiz "Main!"


.text

j main
	newline:
li $t8 '\n'
move $t9, $t8
j newlineEnd
newlineEnd:
jr $ra
	print_something:
la $t8, stringLabel1
li $v0, 4
move $a0, $t8
syscall
jal newline
li $v0, 11
move $a0, $t9
syscall
li $t9 52709
move $t9, $t9
j print_somethingEnd
print_somethingEnd:
jr $ra
	back:
la $t9, stringLabel2
li $v0, 4
move $a0, $t9
syscall
j backEnd
backEnd:
jr $ra
	main:
la $t9, stringLabel3
li $v0, 4
move $a0, $t9
syscall
jal print_something
li $v0, 1
move $a0, $t9
syscall
jal newline
li $v0, 11
move $a0, $t9
syscall
jal back
li $t9 255
move $t9, $t9
j mainEnd
mainEnd:
li $v0, 10
syscall
