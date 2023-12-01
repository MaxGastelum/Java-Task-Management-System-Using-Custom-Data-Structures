// Methods for the Queue
// Created in part with Dr. Amlan Chatterjee in Data Structures course at CSUDH
public interface BareBonesQueue <E> {
	public void offer (E obj);		// Adding the data
	public E poll();				// Removing data
	public boolean isEmpty();		// Checks if the Queue is empty
	public boolean isFull();		// Check if the Queue is full
	public E peek();				// Returns the first element without deleting
	
}
