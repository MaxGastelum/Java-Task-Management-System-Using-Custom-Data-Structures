// Interface for the Linked List
// Created in part with Dr. Amlan Chatterjee in Data Structures course at CSUDH
public interface BareBonesLinkedList <E> {
	// Add element to the LL at specific index
	public void add(int index, E item);
	//Remove an element from the LL at specific index
	public E remove(int index);
	// Getters and Setters
	public E get(int index);
	public E set(int index, E newValue);
	// Get the current size of the LL
	public int size();
		
}
