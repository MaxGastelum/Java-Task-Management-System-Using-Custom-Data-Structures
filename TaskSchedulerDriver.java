// This is the driver class for the TaskScheduler
import java.io.IOException;
import java.util.Scanner;
public class TaskSchedulerDriver {

	public static void main(String[] args) throws IOException{
		
		TaskScheduler taskScheduler = new TaskScheduler();		// Creating an instance of TaskScheduler, it is initialized with the task from the initialTask.txt
		Scanner KB = new Scanner(System.in);					// Creating a scanner to take input from the user
		
		// Printing out the initial to-do list and completed list
		System.out.println("The initial to-do list and completed tasks list initialized with the input file:");
		taskScheduler.displayTask(0);
		
		System.out.println();
		System.out.println();
		
		// This string holds the formatted selection menu
		String selectionMenu = "Please Make a Selection:\n"
							 + "Press 1:  To Add a Task.\n"
							 + "Press 2:  To Remove First Task From The To-Do List.\n"
							 + "Press 3:  To Remove Specific Task From The To-Do List.\n"
							 + "Press 4:  To Mark First Task in To-Do List as Complete.\n"
							 + "Press 5:  To Mark Specific Task in To-Do as Complete.\n"
							 + "Press 6:  To Display To-Do List.\n"
							 + "Press 7:  To Display Completed Tasks List.\n"
							 + "Press 8:  To Display To-Do List and Completed Tasks List.\n"
							 + "Press 9:  To Sort To-Do List by Priority.\n"
							 + "Press 10: To Undo Last Action(add, remove, or complete).\n"
							 + "Press 11: To Exit.";
		
		String stringKey;	// The key will be scanned as a string
		int key = 0;		// Initializing the integer reference that will be used to store the users selected option
		
		do {
			
			System.out.println(selectionMenu);		// Printing the selection menu
			
			for(;;) {
				
				System.out.print("Enter Selection: ");		// Prompt for selection				
				stringKey = KB.next();						// Reads the inputed option by user as a string
				// if string length is 1 and the character is a digit
				if(stringKey.length() <= 2 && Character.isDigit(stringKey.charAt(0))) {					
					key = Integer.parseInt(stringKey);		// Store stringKey as an integer value
					// if key is 1-11
					if (key >= 1 && key <= 11) {						
						break;		// valid entry, break						
					}					
				}			
				System.out.println("Invalid Selection. Please Try Again.\n");	// Error Message			
			}
			
			System.out.println();
			
			switch(key) {
				// If 1 entered, add a Task
				case(1):
					String taskName;
					int priority;
					String strPriority;
					boolean allDigits;
					String dueDate;
					String temp;
					boolean wasAdded;
					
					System.out.println("Adding a task to the to-do list:");					
					System.out.print("Please enter a task name (no spaces):");		// Prompt user for the taskName
					taskName = KB.next();
					
					System.out.print("Please enter a priority level (integer value):");		// Prompt user for priority
					// Infinite loop, breaks when valid entry for priority is entered
					for(;;) {
						allDigits = true;			// Flag to track if all the characters of the input for priority are digits
						strPriority = KB.next();	
						// Loop through the string holding the inputed priority
						for(int i = 0; i < strPriority.length(); i++) {
							// If the character at the current index is not a digit
							if(!Character.isDigit(strPriority.charAt(i))) {
								allDigits = false;		// Set the allDigits flag to false
								break;					// Break out of the loop
							}
						}
						// If the allDigits flag is true
						if(allDigits) {
							priority = Integer.parseInt(strPriority);		// Store the strPriority as an integer value
							break;		// Break out of the infinite loop
						}
						System.out.print("Invalid Entry! Please enter a priority level (integer value):");	// Error Message, Prompt user for priority again
					}									
					System.out.print("Please enter a due date in mm/dd/yyyy format (include leading 0 for month and day if necessary):");	// Prompt user for dueDate
					// Infinite loop, breaks when valid entry for due date is entered
					for(;;) {	
						temp = KB.next();
						// If the length is equal to 10
						if(temp.length() == 10) {
							// If characters at index 0,1,3,4 are digits, and character at 2 is '/'
							if(Character.isDigit(temp.charAt(0)) && Character.isDigit(temp.charAt(1)) && Character.isDigit(temp.charAt(3)) && Character.isDigit(temp.charAt(4)) && temp.charAt(2) == '/') {
								if(Character.isDigit(temp.charAt(6)) && Character.isDigit(temp.charAt(7)) && Character.isDigit(temp.charAt(8)) && Character.isDigit(temp.charAt(9)) && temp.charAt(5) == '/') {
									// If month is greater than 0 and less than or equal to 12 and day is greater than 0 and less than or equal to 31
									if(Integer.parseInt(temp.substring(0,2)) > 0 && Integer.parseInt(temp.substring(0,2)) <= 12 && Integer.parseInt(temp.substring(3,5)) > 0 && Integer.parseInt(temp.substring(3,5)) <= 31) {
										dueDate = temp;
										break;
									}
								}
							}
														
						}
						System.out.print("Invalid Entry! Please enter a due date in mm/dd/yyyy format (include leading 0 for month and day if necessary):");	// Error Message
					}					
					wasAdded = taskScheduler.addTask(new Task(taskName, priority, dueDate));	// Adding a new Task to the task scheduler with the inputed values for each property
					System.out.println();
					// If the boolean value of wasAdded is false
					if(!wasAdded) {
						System.out.println("A task with that name already exist in the to-do list. The task was not added.");
						break;
					}
					System.out.println(taskName + " has been added to the to-do list:");
					taskScheduler.displayTask(1);		// Call dispalyTask method to show to-do list with the included added task for confirmation
					break;
				// If 2 entered, remove first Task
				case(2):
					Task removedFront = taskScheduler.removeTask();		// Call the removeTask method to remove the first task from the toDoList and catch the removed task
					if(removedFront != null) {
						System.out.println(removedFront.getTaskName() + " has been removed from the to-do list:");
						taskScheduler.displayTask(1);	// Call dispalyTask method to show to-do list with the first task removed task for confirmation
					}
					break;
				// If 3 entered, remove a specific Task based on taskName
				case(3):
					System.out.println("Removing a task from the to-do list:");
					System.out.print("Enter the name of the task you wish to remove(case sensitive):");	// Prompt for taskName of task to remove
					temp = KB.next();
					Task removed = taskScheduler.removeTask(temp);		// Call the overloaded removeTask method with the taskName as the argument and catch the removed task
					// if removed is not null
					if(removed != null) {
						System.out.println();
						System.out.println(removed.getTaskName() + " has been removed from the to-do list:");
						taskScheduler.displayTask(1);	// Call dispalyTask method to show to-do list with the specified task removed task for confirmation
						break;
					}
					System.out.println();
					System.out.println(temp +  " was not found in the to-do list.");
					break;
				// If 4 entered, mark first task as complete
				case(4):
					Task completedFront = taskScheduler.completeTask(); 	// Call the completeTask method to mark the first task from the to-do list as complete
					if(completedFront != null) {
						System.out.println(completedFront.getTaskName() + " has been marked as completed:");
						taskScheduler.displayTask(0);	// Call dispalyTask method to show to-do list with the first task now in completed task list
					}
					break;
				// If 5 entered, mark specific as complete based on task name
				case(5):
					System.out.println("Marking a task from to-do list as completed:");
					System.out.print("Enter the task name of the task you wish to mark as completed(case sensitive):");	// Prompt for taskName of task to mark as complete
					temp = KB.next();
					Task completed = taskScheduler.completeTask(temp);		// Call the overloaded completeTask method with the taskName as the argument to mark the task as complete
					// If removed is not null
					if(completed != null) {
						System.out.println();
						System.out.println(completed.getTaskName() + " has been marked as completed:");
						taskScheduler.displayTask(0);	// Call dispalyTask method to show to-do list with the specified task now in completed list
						break;
					}
					System.out.println();
					System.out.println(temp +  " was not found in the to-do list.");
					break;
				// If 6 entered, display to-do list only
				case(6):
					taskScheduler.displayTask(1);	// Call the displayTask method to show to-do list				
					break;
				// If 7 entered, display completed list only
				case(7):
					taskScheduler.displayTask(2);	// Call the displayTask method to show completed tasks list			
					break;
				// If 8 entered, display to-do list and completed list
				case(8):
					taskScheduler.displayTask(0);	// Call the displayTask method to show to-do list and completed list
					break;
				// If 9 entered, to sort Task
				case(9):
					taskScheduler.prioritySorting();	// Call the prioritySorting method
					System.out.println("The to-do list has been sorted:");
					taskScheduler.displayTask(1);		// Call dispalyTask method to show sorted toDoList
					break;
				// If 10 entered, undo
				case(10):
					taskScheduler.undoAction();			// Call the undoAction method
					System.out.println("The last action has been undone:");
					taskScheduler.displayTask(0);		// Call dispalyTask method to show undo of last action
					break;
			}			
			System.out.println();			
		}while(key != 11);	// If key equals 11, end the loop
		
		// End of the program
		System.out.println("Thank You! Exiting Program.");
		KB.close();
		
	}

}
