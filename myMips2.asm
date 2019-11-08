.data
stringLabel1: .asciiz "Move disk 1 from rod"
stringLabel2: .asciiz "to"
stringLabel3: .asciiz "yolo"
stringLabel4: .asciiz "Move disk"
stringLabel5: .asciiz " from "
stringLabel6: .asciiz "to "


.text

j main
	towerOfHanoi:
move $fp $sp
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
la $t8, 0($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $s7, 0($fp)
lw $s7, ($s7)
li $s6 1
seq $t8, $s7, $s6
beq $t8, 0, AfterIf1
la $t8, stringLabel1
li $v0, 4
move $a0, $t8
syscall
la $t8, -4($fp)
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel2
li $v0, 4
move $a0, $t8
syscall
la $t8, -8($fp)
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
j towerOfHanoiEnd
j AfterIfElse1
AfterIf1:
AfterIfElse1:
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
la $s6, 0($fp)
lw $s6, ($s6)
li $s7 1
sub $t8, $s6, $s7
#ast.BinOp@29444d75
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, -4($fp)
lw $t8, ($t8)
#ast.VarExpr@2280cdac
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, -12($fp)
lw $t8, ($t8)
#ast.VarExpr@1517365b
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, -8($fp)
lw $t8, ($t8)
#ast.VarExpr@4fccd51b
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 12
jal towerOfHanoi
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
la $t8, stringLabel3
li $v0, 4
move $a0, $t8
syscall
la $t8, stringLabel4
li $v0, 4
move $a0, $t8
syscall
la $t8, 0($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $t8, stringLabel5
li $v0, 4
move $a0, $t8
syscall
la $t8, -4($fp)
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
la $t8, stringLabel6
li $v0, 4
move $a0, $t8
syscall
la $t8, -8($fp)
lw $t8, ($t8)
li $v0, 11
move $a0, $t8
syscall
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
la $s7, 0($fp)
lw $s7, ($s7)
li $s6 1
sub $t8, $s7, $s6
#ast.BinOp@44e81672
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, -12($fp)
lw $t8, ($t8)
#ast.VarExpr@60215eee
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, -8($fp)
lw $t8, ($t8)
#ast.VarExpr@4ca8195f
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, -4($fp)
lw $t8, ($t8)
#ast.VarExpr@65e579dc
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 12
jal towerOfHanoi
add $sp, $sp, 4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
towerOfHanoiEnd:
move $sp, $fp
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
la $t8, 0($fp)
li $s6 4
sw $s6, ($t8)
#pushing regs
addi $sp,$sp,-4
sw $fp, ($sp)
addi $sp,$sp,-4
sw $ra, ($sp)
addi $sp,$sp,-4
sw $t8, ($sp)
la $t8, 0($fp)
lw $t8, ($t8)
#ast.VarExpr@61baa894
addi $sp,$sp,-4
sw $t8, ($sp)
li $t8 'A'
#ast.ChrLiteral@b065c63
addi $sp,$sp,-4
sw $t8, ($sp)
li $t8 'C'
#ast.ChrLiteral@768debd
addi $sp,$sp,-4
sw $t8, ($sp)
li $t8 'B'
#ast.ChrLiteral@490d6c15
addi $sp,$sp,-4
sw $t8, ($sp)
add $sp, $sp, 12
jal towerOfHanoi
add $sp, $sp, 4
lw $t8 ,($sp)
addiu $sp,$sp,4
lw $ra ,($sp)
addiu $sp,$sp,4
lw $fp ,($sp)
addiu $sp,$sp,4
li $t8 0
move $t9, $t8
j mainEnd
mainEnd:
move $sp, $fp
li $v0, 10
syscall
