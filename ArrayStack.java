// This is the class for Stack Implementation using Arrays
// Created in part with Dr. Amlan Chatterjee in Data Structures course at CSUDH
public class ArrayStack <E> implements BareBonesStack<E>{
	
	// Storage for the Stack
	private E[] theData;			// This is only the reference holder
	private int topOfStack = -1;	// Variable to store the TOS, -1 indicates it is empty
	private static final int INITIAL_CAPACITY = 5;		// This will be the default capacity of the stack
	private static final int EMPTY = -1;
	
	// Constructors for the class
	public ArrayStack() {
		// The actual array needs to be created here, and reference saved in theData
		this.theData =  (E[]) new Object [this.INITIAL_CAPACITY];
	}
	
	// Overloaded constructor, to create a stack of user given capacity
	public ArrayStack(int capacity) {
		this.theData = (E[]) new Object [capacity];
	}
	
	@Override
	public void push(E obj) {
		// This method adds element to the Stack
		// First check, if there is space to add
		if(this.topOfStack == this.theData.length - 1){
			//Meaning, if the last element is at location capacity - 1, then it is full
			//System.out.println("Stack Overflow!");		//If you want, then use reallocate similar to AL
			reallocate();		// Call the reallocate method to create more space
			//return;
		}
		// Otherwise, there is space to insert
		/*this.topOfStack++;
		this.theData[topOfStack] = obj;*/
		this.theData[++topOfStack] = obj;
		return;
		
	}
	
	private void reallocate() {
		// This will double the size of the array holding the stack
		int newCapacity = this.theData.length * 2;
		E[] temp = (E[]) new Object[newCapacity];
		// Copy over the elements from the old array to the new array
		for(int i = 0; i < this.theData.length; i++) {
			// Inserting elements into new array
			temp[i] = this.theData[i];
		}
		// When copying is done, update the reference
		this.theData = temp;
	}
	
	public String toString() {
		String s = "Stack: ";
		//Iterate over the elements and add to the String
		for(int i = 0; i <= this.topOfStack; i++) {
			s += this.theData[i] + " | ";
		}
		return s;
	}

	@Override
	public E pop() {
		// This method deletes an element from the TOS
		if(isEmpty()) {
			// We can call the isEmpty method, to check if there is something to delete
			System.out.println("Stack Underflow!");
			return null;
		}
		// Otherwise, there is at least 1 element in the stack, and we can pop the TOS
		return this.theData[topOfStack--];
		/*E temp = this.theData[topOfStack];
		topOfStack --;
		return temp;*/
	}

	@Override
	public E peek() {
		// This method returns without deleting the element at the TOS
		if(isEmpty()) {
			// We can call the isEmpty method, to check if there is something to return
			System.out.println("Stack Underflow!");
			return null;
		}
		// Otherwise, there is at least 1 element in the stack, and we can return the TOS
		return this.theData[topOfStack];
	}

	@Override
	public boolean isEmpty() {
		/*if(topOfStack == -1)
			return true;
		else
			return false;*/
		return (topOfStack == this.EMPTY);
	}

}
