.data


.text

j main
	bar:
move $fp $sp
move $t8, $ra
sw $t8, 0($fp)
addi $sp,$sp,-4
li $t8 3
la $s7, -4($fp)
lw $s7, ($s7)
add $s6, $t8, $s7
move $t9, $s6
j barEnd
barEnd:
move $sp, $fp
lw $s6, 0($fp)
move $ra, $s6
jr $ra
	foo:
move $fp $sp
move $s6, $ra
sw $s6, 0($fp)
addi $sp,$sp,-4
la $s6, -4($fp)
sub $sp, $sp, 4
sw $fp ($sp)
li $t8 4
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 4
jal bar
lw $fp ($sp)
add $sp, $sp, 4
sw $t9, ($s6)
la $t9, -4($fp)
lw $t9, ($t9)
li $v0, 1
move $a0, $t9
syscall
fooEnd:
move $sp, $fp
lw $t9, 0($fp)
move $ra, $t9
jr $ra
li $v0, 10
syscall
