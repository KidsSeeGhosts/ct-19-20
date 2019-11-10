.data


.text

j main
	adder:
move $fp $sp
addi $sp,$sp,-4
#about to do rhs in assign
la $s7, 0($fp)
#just accepted lhs of bin op
lw $s7, ($s7)
li $s6 1
#just accepted rhs of bin op
add $t8, $s7, $s6
#about to do lhs in assign
la $s6, 0($fp)
sw $t8, ($s6)
la $s6, 0($fp)
lw $s6, ($s6)
move $t9, $s6
j adderEnd
adderEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#Argument ast.BinOp@5ecddf8f
li $t8 5
#just accepted lhs of bin op
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
#push to stack
addi $sp,$sp,-4
sw $s6, ($sp)
#push to stack
addi $sp,$sp,-4
sw $t8, ($sp)
#Argument ast.IntLiteral@3f102e87
li $t8 5
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 0
jal adder
add $sp, $sp, 4
lw $t8 ,($sp)
addiu $sp,$sp,4
lw $s6 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
#just accepted rhs of bin op
add $s6, $t8, $t9
addi $sp,$sp,-4
sw $s6, ($sp)
add $sp, $sp, 0
jal adder
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
#end of function call expression
la $s6, 0($fp)
sw $t9, ($s6)
la $s6, 0($fp)
lw $s6, ($s6)
li $v0, 1
move $a0, $s6
syscall
mainEnd:
move $sp, $fp
li $v0, 10
syscall
