.data

.text

	main: 
	addi $s0, $zero, 5 #register containing 5
	sub $sp, $sp, 4 #stack goes down 4 as that's where I'm storing x
	sw $s0, 0($sp) #put 5 into x
	#free up s0 here
	addi $s0, $zero, 6 #register containing 6
	sub $sp, $sp, 4 #stack goes down 8 as that's where I'm storing y
	sw $s0, 0($sp) #put 6 into y
	#finished storing x and y
	lw $s0, 4($sp) #put x into s0
	lw $s1, 0($sp) #put y into s1
	#add the 2 things
	add $s0, $s0, $s1 #s0 is x+y
	#now i need to store s0 into x
	sw $s0, 4($sp) #storing into x
	
	
	
	
li $v0, 1
lw $a0, 4($sp) #print x
syscall
li $v0, 10
syscall
