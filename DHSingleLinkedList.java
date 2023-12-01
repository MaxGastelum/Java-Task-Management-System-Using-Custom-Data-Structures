// This is a class implementing a custom Linked List data structure
// Created in part with Dr. Amlan Chatterjee in Data Structures course at CSUDH
public class DHSingleLinkedList <E> implements BareBonesLinkedList<E> {
	
	// We need to create nodes, so we create a Node class
	private class Node<F>{
		private F data;			// Data at the node
		private Node<F> next;	// The link to the next node
		
		// Constructors to create the Node
		// This will create a node with the data and the next value supplied by the user
		public Node(F data, Node<F> next) {
			this.data = data;
			this.next = next;
		}
		// When only the data is supplied, and the next is null
		public Node(F data) {
			this(data, null);	// This calls the other constructor to create the node
		}
	}

	// Data and Methods for the Linked List
	
	private Node<E> head;		// Reference to the head of the LL
	private int size;			// How many nodes are there in the LL
	
	// Constructors for the LL
	public DHSingleLinkedList() {
		this.head = new Node<E>(null);
		//The head has no data, or next value at the beginning
		this.size = 0;				// when the LL is create, there are no other nodes
	}
	
	@Override
	public void add(int index, E item) {
		// This method adds an element to the LL
		// Depending on the index, it adds to the first location using addFirst()
		// or adds a node using addAfter()
		
		if(index < 0 || index > size) {		// Checking for a valid index
			System.out.println("Invalid Index");
			return;
		}
		else if(index == 0) {
			// We are adding at the first location
			addFirst(item);
		}
		else {
			// We are going to add after some node
			// We need the reference to that node first
			Node<E> node = getNode(index);		// Get the reference to the previous node
			addAfter(node, item);
		}
		
	}

	private void addAfter(Node<E> node, E item) {
		// Add the item after the reference node
		Node<E> temp = new Node<E>(item, node.next);
		node.next = temp;
		size++;
		
		//node.next = new Node<E>(item, node.next);
		//size++;
		
	}

	private Node<E> getNode(int index) {
		if(index < 0 || index > size) {		// Checking for a valid index
			System.out.println("Invalid Index");
			return null;
		}
		// This method loops over the LL, and returns the references of Node at index
		Node<E> node = head;	// This is a reference copy of the head, Do not want to move the head
		for(int i = 0; i < index && node != null; i++) {
			node = node.next;
		}
		return node;		// return the reference
	}

	private void addFirst(E item) {
		// We create a node, with item as data, and head.next as the next
		// Then we update the head.next to point to this newly created node
		// Finally, we increment the size
		
		Node<E> temp = new Node<E>(item, head.next);
		head.next = temp;
		//head.next = new Node<E>(item, head.next);
		size++;
		
	}

	@Override
	public E remove(int index) {
		// This method removes an element from the LL at given index
		// Check if the index is valid
		if(index < 0 || index > size) {		// Checking for a valid index
			System.out.println("Invalid Index");
			return null;
		}
		else if (index == 0) {
			return removeFirst();
		}
		else {
			Node<E> node = getNode(index);
			return removeAfter(node);
		}
		
	}

	private E removeAfter(Node<E> node) {
		Node<E> temp = node.next;
		if(temp != null) {
			node.next = temp.next;
			size--;
			return temp.data;
		}
		return null;
	}

	private E removeFirst() {
		// Delete the first node
		Node<E> temp = head;
		if(temp != null) {
			head = head.next;
			size--;
			return temp.data;
		}
		return null;
	}

	@Override
	public E get(int index) {
		// This method returns the data of the node at the given index
		// Check if the index is valid
		if(index < 0 || index >= size) {		// Checking for a valid index
			System.out.println("Invalid Index");
			return null;
		}
		
		Node<E> node = getNode(index).next;		// Use getNode helper method to get the node at the given index
		return node.data;
	}

	@Override
	public E set(int index, E newValue) {
		// This method replaces a data of a node at a given index
		// Check if the index is valid
		if(index < 0 || index >= size) {		// Checking for a valid index
			System.out.println("Invalid Index");
			return null;
		}
		
		Node<E> node = getNode(index).next;		// Use getNode helper method to get the node at the given index
		E result = node.data;
		node.data = newValue;
		return result;
	}

	@Override
	public int size() {
		// This method returns the size of the LL
		return this.size;
	}
	
	// Implement the toString() to print the LL data
	public String toString() {
		String s = "[";
		Node<E> p = head;	// This reference will be used to iterate over the LL
		if(p != null) {		// When there is no head, check the error
			while(p.next != null) {
				// Iterate over the nodes one by one
				s += p.next.data + " -> ";		// Adds the data to the String
				p = p.next;						// Go to the next Node
			}
		}
		// End of the LL
		s += "]";
		return s;
	}

	// Print the linkedList in reverse using recursion
	// Idea is, print next node first, then print the current node	
	public void printReverse(Node<E> head) {
		if(head.next == null) {
			return;		// Base case
		}
		
		// First print the next node
		printReverse(head.next);		// Recursive call
		// Once I am done printing the next node, I will print the current node
		System.out.print(head.next.data + " <- ");
		
	}
	
	// Wrapper method to call printReverse
	public void printReverse() {
		this.printReverse(head);	// The head value is supplied here
	}
	
}
