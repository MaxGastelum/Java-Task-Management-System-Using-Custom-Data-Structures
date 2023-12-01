// This is the Queue class using circular array concept
// Created in part with Dr. Amlan Chatterjee in Data Structures course at CSUDH
public class CircularQueue <E> implements BareBonesQueue<E> {
	
	// Data
	private E[] Q;							// Array reference for the actual Queue
	private int front;						// front of the queue
	private int rear;						// rear of the queue
	private int size;						// Number of elements in the queue
	private int capacity;					// Total number of spots in the queue
	private final int DEFAULT_CAPACITY = 5; // Default capacity
	
	// Default Constructor
	public CircularQueue() {
		this.Q = (E[]) new Object[this.DEFAULT_CAPACITY];		// Queue is created here
		this.front = 0;
		this.rear = 0;			// Front and rear 0, no element to begin with
		this.size = 0;
		this.capacity = this.DEFAULT_CAPACITY;
	}
	
	// Overloaded Constructor
	public CircularQueue(int capacity) {
		this.capacity = capacity;			// Capacity is given by the user
		this.Q = (E[]) new Object[this.capacity];
		this.front = 0;
		this.rear = 0;
		this.size = 0;
	}

	@Override
	public void offer(E obj) {
		// Adds elements at rear
		// First check if there is space to add
		if(isFull()) {
			//System.out.println("Queue out of space; calling reallocate");
			reallocate();
			this.offer(obj); 		//Once space is available this method is called again
		}
		else {
			// So we have space to add element at the rear of the queue
			Q[rear] = obj;						// Inserts the element
			rear = (rear + 1) % this.capacity;	// Update the rear
			size++;								// Update the number of elements in Q
		}
		
	}

	private void reallocate() {
		int newCapacity = 2 * capacity;
		E[] newData = (E[]) new Object[newCapacity];
		int j = front;
		for (int i = 0; i < size; i++) {
			newData[i] = Q[j];
			j = (j + 1) % capacity;
		}
		front = 0;
		rear = size;
		capacity = newCapacity;
		this.Q = newData;
	}

	@Override
	public E poll() {
		// Remove an element from the front of the queue, if there is one
		if(isEmpty()) {
			System.out.println("Queue underflow");
			return null;
		}
		// So the Q is not empty
		// We will remove the data from the front, and return it back
		E temp = Q[front];					// Store the data to be returned
		front = (front + 1 ) % this.capacity;
		size--;
		return temp;
	}

	@Override
	public boolean isEmpty() {
		return (this.size == 0);
	}

	@Override
	public boolean isFull() {
		return(this.size == this.capacity);
	}

	@Override
	public E peek() {
		if(isEmpty()) {
			System.out.println("Queue underflow");
			return null;
		}
		return Q[front];
	}
	
	// Implement the toString method to display the contents of the queue
	public String toString() {
		String s = "Q: ";
		// Loop over the elements in the Q, from the front to the rear
		for(int i = front; i < front + size; i++) {
			s += Q[i % this.capacity] + " | ";
		}
		return s;
	}

	
}
