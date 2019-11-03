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
move $t8, $ra
sw $t8, 0($fp)
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
addi $sp,$sp,-4
la $t8, -4($fp)
lw $t8, ($t8)
li $v0, 1
move $a0, $t8
syscall
la $t8, -4($fp)
lw $t8, ($t8)
li $s7 1
beq $t8, $s7, EqualTo1
li $s6, 0
j AfterEqualTo1
EqualTo1: 
li $s6, 1
AfterEqualTo1:
beq $s6, 0, AfterIf1
la $s7, stringLabel1
li $v0, 4
move $a0, $s7
syscall
la $s7, -8($fp)
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
la $s7, stringLabel2
li $v0, 4
move $a0, $s7
syscall
la $s7, -12($fp)
lw $s7, ($s7)
li $v0, 11
move $a0, $s7
syscall
j towerOfHanoiEnd
j AfterIfElse1
AfterIf1:
AfterIfElse1:
la $s7, -4($fp)
lw $s7, ($s7)
li $t8 1
sub $s5, $s7, $t8
addi $sp,$sp,-4
sw $s5, ($sp)
la $t8, -8($fp)
lw $t8, ($t8)
addi $sp,$sp,-4
sw $t8, ($sp)
la $s7, -16($fp)
lw $s7, ($s7)
addi $sp,$sp,-4
sw $s7, ($sp)
la $s4, -12($fp)
lw $s4, ($s4)
addi $sp,$sp,-4
sw $s4, ($sp)
add $sp, $sp, 16
jal towerOfHanoi
la $s4, stringLabel3
li $v0, 4
move $a0, $s4
syscall
la $s4, stringLabel4
li $v0, 4
move $a0, $s4
syscall
la $s4, -4($fp)
lw $s4, ($s4)
li $v0, 1
move $a0, $s4
syscall
la $s4, stringLabel5
li $v0, 4
move $a0, $s4
syscall
la $s4, -8($fp)
lw $s4, ($s4)
li $v0, 11
move $a0, $s4
syscall
la $s4, stringLabel6
li $v0, 4
move $a0, $s4
syscall
la $s4, -12($fp)
lw $s4, ($s4)
li $v0, 11
move $a0, $s4
syscall
la $s3, -4($fp)
lw $s3, ($s3)
li $s2 1
sub $s1, $s3, $s2
addi $sp,$sp,-4
sw $s1, ($sp)
la $s2, -16($fp)
lw $s2, ($s2)
addi $sp,$sp,-4
sw $s2, ($sp)
la $s3, -12($fp)
lw $s3, ($s3)
addi $sp,$sp,-4
sw $s3, ($sp)
la $s0, -8($fp)
lw $s0, ($s0)
addi $sp,$sp,-4
sw $s0, ($sp)
add $sp, $sp, 16
jal towerOfHanoi
towerOfHanoiEnd:
move $sp, $fp
lw $s0, 0($fp)
move $ra, $s0
jr $ra
	main:
move $fp $sp
addi $sp,$sp,-4
la $s0, -4($fp)
li $t7 4
sw $t7, ($s0)
la $t6, -4($fp)
lw $t6, ($t6)
addi $sp,$sp,-4
sw $t6, ($sp)
li $t5 'A'
addi $sp,$sp,-4
sw $t5, ($sp)
li $t4 'C'
addi $sp,$sp,-4
sw $t4, ($sp)
li $t3 'B'
addi $sp,$sp,-4
sw $t3, ($sp)
add $sp, $sp, 16
jal towerOfHanoi
li $t3 0
move $t9, $t3
j mainEnd
mainEnd:
move $sp, $fp
li $v0, 10
syscall
