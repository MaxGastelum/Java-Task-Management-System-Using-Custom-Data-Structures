// This is the TaskScheduler class that will add, remove, undo, and sort task.
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
public class TaskScheduler {

	public DHSingleLinkedList<Task> taskLL = new DHSingleLinkedList<>();		// This LL will store Task, in a sorted manner
	public CircularQueue<Task> toDoList = new CircularQueue<>();				// This queue will function as the to-do list, remains in order inputed by user until they choose to sort
	public ArrayStack<Task> undoStack = new ArrayStack<>();						// This stack will store task removed to be added back with "undo" feature
	public CircularQueue<Task> completedQueue = new CircularQueue<>();			// This queue will hold completed task
	
	
	// Default Constructor
	public TaskScheduler() throws IOException{
		readFromFile();
	}
	
	
	// This method adds Task to the taskLL and toDoList Queue
	public boolean addTask(Task inTask) {				
		Task compareTask;		
		// Check to see if a task already in taskLL has the same task name
		// Loop through the taskLL
		for(int i = 0; i < taskLL.size(); i++) {
			compareTask = taskLL.get(i);	// compareTask reference so get method is only called once per iteration of the loop
			// if the task name of inTask and compareTask are the same
			if(inTask.getTaskName().equals(compareTask.getTaskName())) {
				return false;		// Return false, exit the method without adding the task to the taskLL, toDoList queue, or undoStack
			}
		}
		
		toDoList.offer(inTask);		// Add the task to the end of the toDoList queue
		undoStack.push(inTask);		// Add to the top of the undoStack		
		// Inserting the Task into the taskLL in a sorted manner, highest priority first
		// If taskLL is empty, insert at the front
		if(taskLL.size() == 0) {
			taskLL.add(0, inTask);		// Insert Task at the front of the taskLL
			return true;				// Return true, to indicate that the task was added
		}
		// If inFlight priority is less than the first Task, insert in front
		else if(inTask.getPriority() < taskLL.get(0).getPriority()) {
			taskLL.add(0, inTask);		// Insert Task at the front of the taskLL
			return true;				// Return true, to indicate that the task was added
		}
		else {
			// Loop through the LL to find appropriate place to insert the inTask
			for(int i = 0; i < taskLL.size(); i++) {								
				compareTask = taskLL.get(i);		// compareTask reference so the get method is only called once per iteration of the loop										
				// If the priority of inTask is higher than the compareTask
				if(inTask.getPriority() < compareTask.getPriority()) {
					taskLL.add(i, inTask);		// Insert inTask before compareTask
					return true;				// Return true, to indicate that the task was added					
				}
				// If the priorities are equal, compare the dueDate
				else if (inTask.getPriority() == compareTask.getPriority()) {
					// If the dueDate of inTask is less than compare task
					if(inTask.getIntDueDate() < compareTask.getIntDueDate()) {
						taskLL.add(i, inTask);		// Insert inTask before compareTask
						return true;				// Return true, to indicate that the task was added
					}else {
						// Loop through the taskLL starting at the current iteration of outer for loop
						for(int j = i+1; j < taskLL.size(); j++) {
							compareTask = taskLL.get(j);		// compareTask reference so the get method is only called once per iteration of the loop
							// If inTask priority is less than compareTask priority or inTask intDueDate value is less than compareTask intDueDate value
							if(inTask.getPriority() < compareTask.getPriority() || inTask.getIntDueDate() < compareTask.getIntDueDate()) {
								taskLL.add(j, inTask);		// Insert inTask before compareTask
								return true;				// Return true, to indicate that the task was added
							}
						}				
					}				
				}			
			}
			// If it was found that the priority of the inTask was not higher than any of the other task, insert it at the end
			taskLL.add(taskLL.size(), inTask);
			return true;	// Return true, to indicate that the task was added
		}
	}
	
	// This method removes task from the front of the toDoList queue and then from the taskLL. The task is then pushed onto undoStack
	public Task removeTask() {	
		Task removedFront = toDoList.poll();	// Remove the front of the toDoList queue and store it in removedFront	
		for(int i = 0; i < taskLL.size(); i++) {
			// If the taskName of the task at the current index matches the taskName of the removedFront
			if(taskLL.get(i).getTaskName().equals(removedFront.getTaskName())) {
				taskLL.remove(i);		// Remove the task at the current index
				break;					// Exit the loop
			}
		}
		// If removedFront is not null
		if(removedFront !=  null) {
			undoStack.push(removedFront);	// Push the removedFrom to the undoStack
		}
		return removedFront;		// Return the removedFront	
	}
	
	
	// This overloaded method removes a Task from the taskLL and toDoList and pushes it on the undoStack, based off of passed task name
	public Task removeTask(String taskName) {		
		Task removedTask = null;		// Used to reference the removedTask to push onto undoStack		
		// Removing the task from the taskLL
		// Loop through the taskLL
		for(int i = 0; i < taskLL.size(); i++) {
			// If the taskName of the task at the current index matches the passed taskName
			if(taskLL.get(i).getTaskName().equals(taskName)) {
				taskLL.remove(i);		// Remove the task at the current index
				break;					// Exit the loop
			}			
		}		
		// Removing the task from the toDoList
		CircularQueue<Task> newToDoList = new CircularQueue<>();	// Temporary queue used to hold the task that are not removed		
		// While the toDoList is not empty
		while (!toDoList.isEmpty()) {
			// If the first Task's taskName matches the passed task
			if(toDoList.peek().getTaskName().equals(taskName)){				
				removedTask = toDoList.poll();		// Poll the task and catch it in the removedTask variable			
			}else {				
				newToDoList.offer(toDoList.poll());	// Poll the task and add it to the newToDoList				
			}			
		}		
		toDoList = newToDoList;		// Set toDoList back to reference the filled queue with the target task removed		
		// If the removedTask is not null
		if(removedTask != null) {
			undoStack.push(removedTask);	// Push the removedTask to the undoStack
		}				
		return removedTask;		// Return the removedStack		
	}
	
	
	// This method marks the first Task in toDoList as complete, removes it from to-do list and places it in complete list
	public Task completeTask() {		
		Task completedFront  = removeTask();		// Call the default removeTask method to remove front of the to-do list and catch the returned Task
		// If completedFront is not null
		if(completedFront != null) {
			completedQueue.offer(completedFront);	// Added the completed task to the completedQueue
		}
		return completedFront;		// return the completed task
	}
	
	// This overloaded method marks a specific task as complete based off taskName, removes it from to-do list and places it in complete list
	public Task completeTask(String taskName) {		
		Task completedTask = null;		
		// Removing the completedTask from the taskLL
		// Loop through the taskLL
		for(int i = 0; i < taskLL.size(); i++) {
			// If the taskName of the task at the current index matches the passed taskName
			if(taskLL.get(i).getTaskName().equals(taskName)) {
				taskLL.remove(i);		// Remove the task at the current index
				break;
			}
		}		
		// Removing the completedTask from the toDoList
		CircularQueue<Task> newToDoList = new CircularQueue<>();	// Temporary queue used to hold the task that are not removed				
		// While the toDoList is not empty
		while (!toDoList.isEmpty()) {
			// If the first Task's taskName matches the passed task
			if(toDoList.peek().getTaskName().equals(taskName)){				
				completedTask = toDoList.poll();		// Poll the task and catch it in the completedTask variable			
			}else {				
				newToDoList.offer(toDoList.poll());		// Poll the task and add it to the newToDoList				
			}			
		}
		toDoList = newToDoList;				// Set toDoList to reference the newly created list with the completed Task removed
		// If the removedTask is not null
		if(completedTask != null) {
			completedQueue.offer(completedTask);	// Add the completedTask to the completedQueue
			undoStack.push(completedTask);			// Push the completedTask to the undoStack
		}		
		return completedTask;	// Return the completed task
	}
	
	
	// This method sorts the toDoList based on priority, then dueDate if priorities are equal
	public void prioritySorting() {		
		// Task are sorted as they are inserted into the taskLL, but the toDoList Queue stores them as they are inputed
		// Copy the order of the taskLL to the toDoList	
		CircularQueue<Task> sortedToDoList = new CircularQueue<>();	// Temporary queue used to hold the task in sorted order
		// Loop through the taskLL
		for(int i = 0; i < taskLL.size(); i++) {
			sortedToDoList.offer(taskLL.get(i));
		}
		toDoList = sortedToDoList;		// Set toDoList to reference the newly created sortedToDoList	
	}
	
	
	// This method undoes the last action(add, remove, or complete)
	public void undoAction () {		
		Task undoTask = undoStack.pop();	// Pop the Task from the top of the stack, last action
		// If last action was add, the undoTask will be found in the taskLL
		// Loop through the taskLL
		for(int i = 0; i < taskLL.size(); i++) {
			// if undoTask's taskName matches taskName of task at currentIndex
			if(undoTask.getTaskName().equals(taskLL.get(i).getTaskName())) {
				removeTask(undoTask.getTaskName());		// Remove the task from the taskLL and toDoList with removeTask method
				undoStack.pop();						// The removeTask method will re-push the undoTask onto the stack, undo this by popping once more
				return;									// Exit method
			}
		}
		// If the undoTask was not found in the taskLL, the completedQueue is searched and the undoTask is removed from completedQueue if found
		CircularQueue<Task> tempQueue = new CircularQueue<>();	// Temporary queue used to hold the task		
		// While the completedQueue is not empty
		while (!completedQueue.isEmpty()) {
			// If the first Task's taskName matches the passed task
			if(completedQueue.peek().getTaskName().equals(undoTask.getTaskName())){				
				completedQueue.poll();				// Poll the task and do not catch it
			}else {				
				tempQueue.offer(completedQueue.poll());		// Poll the task and add it to the tempQueue			
			}					
		}		
		completedQueue = tempQueue;		// Set completeQueue to reference the filled queue		
		addTask(undoTask);				// If the last task was remove or complete, the task is reinserted into the taskLL and the toDoList queue
		undoStack.pop();				// The addTask method will re-push the undoTask onto the stack, undo this by popping once more	
	}
	
	
	// This method reads task entries from an initialization file and inputs them into the taskLL and toDoList
	private void readFromFile() throws IOException{
		File inputFile = new File("initialTask.txt");		// This is a references to the file initialTask.txt
		Scanner inputScanner = new Scanner(inputFile);		// This scanner will read from the file
		Task temp;
		// While there are Task entries left in the file
		while(inputScanner.hasNext()) {
			temp = new Task(inputScanner.next(), inputScanner.nextInt(), inputScanner.next());		// Create an instance of Task with scanned properties
			addTask(temp);				// Calls addTask method to add the task to the taskLL, toDoList queue, and undoStack
		}
		inputScanner.close();		// Closing the Scanner
	}
	
	
	// This method will display the task from the toDoList, completedQueue, or both toDoList and completedQueue
	public void displayTask(int option) {
		CircularQueue<Task> tempQueue = new CircularQueue<>();		// Temporary queue used to store Task that are popped for printing			
		Task tempTask;		// Used reference the popped task
		CircularQueue<Task> tempCompletedQueue = new CircularQueue<>();		// Temporary queue used to store Task that are popped for printing
		// If passed option is equal to 0, print both
		if(option == 0) {			
			// Printing the task from the toDoList
			System.out.println("To-Do List:");
			System.out.printf("%-20s %-10s %-10s\n", "Task Name", "Priority", "Due Date");		
			// While toDoList is not empty
			while (!toDoList.isEmpty()) {			
				tempTask = toDoList.poll();			// Poll first element from toDoList and store it in tempTask
				tempQueue.offer(tempTask);			// Offer the tempTask to the tempQueue			
				System.out.println(tempTask);		// Print the tempTask			
			}		
			toDoList = tempQueue;					// Set toDoList back to reference the filled queue		
			// Printing the task from the Completed Task				
			System.out.println();
			System.out.println("Completed Tasks:");
			System.out.printf("%-20s %-10s %-10s\n", "Task Name", "Priority", "Due Date");
			while(!completedQueue.isEmpty()) {
				tempTask = completedQueue.poll();		// Poll first element from completedQueue and store it in tempTask
				tempCompletedQueue.offer(tempTask);		// Offer the tempTask to the tempCompletedQueue
				System.out.println(tempTask);			// Print the tempTask
			}		
			completedQueue = tempCompletedQueue; 		// Set completedQueue back to reference the filled queue
			return;	// Exit method
		}
		// If passed option is equal to 1, display toDoList only
		else if(option == 1) {
			// Printing the task from the toDoList
			System.out.println("To-Do List:");
			System.out.printf("%-20s %-10s %-10s\n", "Task Name", "Priority", "Due Date");		
			// While toDoList is not empty
			while (!toDoList.isEmpty()) {			
				tempTask = toDoList.poll();			// Poll first element from toDoList and store it in tempTask
				tempQueue.offer(tempTask);			// Offer the tempTask to the tempQueue			
				System.out.println(tempTask);		// Print the tempTask			
			}		
			toDoList = tempQueue;					// Set toDoList back to reference the filled queue
			return; // Exit method
		}
		// If passed option is equal to 2, display completedQueue only
		else if(option == 2) {
			// Printing the task from the Completed Task				
			System.out.println("Completed Tasks:");
			System.out.printf("%-20s %-10s %-10s\n", "Task Name", "Priority", "Due Date");
			while(!completedQueue.isEmpty()) {
				tempTask = completedQueue.poll();		// Poll first element from completedQueue and store it in tempTask
				tempCompletedQueue.offer(tempTask);		// Offer the tempTask to the tempCompletedQueue
				System.out.println(tempTask);			// Print the tempTask
			}		
			completedQueue = tempCompletedQueue; 		// Set completedQueue back to reference the filled queue
			return; // Exit method
		}
	}
	
	
}
