// This is the interface for the stack
// Created in part with Dr. Amlan Chatterjee in Data Structures course at CSUDH
public interface BareBonesStack <E>{
	void push(E obj);		// This is to add elements to the stack
	E pop();				// This is to remove the elements from the stack
	E peek();				// This returns the top element without deleting it
	boolean isEmpty();		// Returns true if the stack is empty

}
