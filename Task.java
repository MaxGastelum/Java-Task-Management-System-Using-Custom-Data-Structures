// This is the Task class, will include a taskName, priority, and due date
public class Task {

	private String taskName;		// Unique identifier for the task
	private int priority;			// A lower value indicates higher priority
	private String dueDate;			// Date when the task is due
	private int intDueDate;
	
	// Constructor
	public Task(String taskName, int priority, String dueDate) {
		setTaskName(taskName);		// Set taskName using the setTaskName method
		setPriority(priority);		// Set priority using setPriority method
		setDueDate(dueDate);		// Set dueDate using the setDueDate method
		setIntDueDate();
	}
	
	// Used to set taskName
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	// Used to set taskName
	public void setPriority(int priority) {
		this.priority = priority;
	}

	// Used to set dueDate
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	// Used to set intDueDate
	public void setIntDueDate() {
		String month = this.dueDate.substring(0,2);			// Stores the first two values (the month)
		String day = this.dueDate.substring(3,5);			// Stores the middle two values (the day)
		String year = this.dueDate.substring(6);			// Stores the last four values (the year)
		this.intDueDate = (Integer.parseInt(month) * 31) + Integer.parseInt(day) + (Integer.parseInt(year) * 365);	// Combines the three to create an integer value version of the dueDate to use for comparison
	}
	
	// Used to get taskName
	public String getTaskName() {
		return this.taskName;
	}
	
	// Used to get priority
	public int getPriority() {
		return this.priority;
	}
	
	// Used to get dueDate
	public String getDueDate() {
		return this.dueDate;
	}
	
	public int getIntDueDate() {
		return this.intDueDate;
	}
	
	// Returns formatted String showing the Task and its properties.
	public String toString() {
		return String.format("%-20s %-10d %-10s", this.taskName, this.priority, this.dueDate);
	}
	
}
