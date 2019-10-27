.data
foo:.asciiz "Hello\n"
la $a0,foo
li $v0, 4
syscall