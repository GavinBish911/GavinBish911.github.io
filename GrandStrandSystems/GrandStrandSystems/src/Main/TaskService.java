/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-320
 * Date: 	Sep 26, 2024
 * Description: This is the TaskService class. It maintains tasks and has capabilities
 * for adding, updating, and deleting tasks.
 *************************/

package Main;

import java.util.ArrayList;

public class TaskService {
	// Start with an ArrayList of contacts to hold the list of tasks
	public ArrayList<Task> taskList = new ArrayList<>();

	// Adds a new task using the Task constructor, then assign the new task to the
	// list.
	public void addTask(String taskName, String taskDesc) {
		// Create the new task
		Task task = new Task(taskName, taskDesc);
		taskList.add(task);
	}

	// Delete task.
	// Use the taskID to find the right task to delete from the list
	// If we get to the end of the list without finding a match for taskID report
	// that to the console.
	// This method of searching for taskID is the same for all update methods below.
	public void deleteTask(String taskID) {
		for (int counter = 0; counter < taskList.size(); counter++) {
			if (taskList.get(counter).getTaskID().equals(taskID)) {
				taskList.remove(counter);
				break;
			}
			if (counter == taskList.size() - 1) {
				System.out.println("Task ID: " + taskID + " not found.");
			}
		}
	}

	// Display the full list of contacts to the console for error checking.
	public void displayTaskList() {
		for (Task element : taskList) {
			System.out.println("\t Task ID: " + element.getTaskID());
			System.out.println("\t Task Name: " + element.getTaskName());
			System.out.println("\t Task Description: " + element.getTaskDesc());
		}
	}

	// Using Task ID, return a task object
	// If a matching Task ID is not found, return a task object with default values
	public Task getTask(String taskID) {
		Task task = new Task(null, null);
		for (Task element : taskList) {
			if (element.getTaskID().contentEquals(taskID)) {
				task = element;
			}
		}
		return task;
	}

	// Update the task description.
	public void updateTaskDesc(String updatedString, String taskID) {
		for (int counter = 0; counter < taskList.size(); counter++) {
			if (taskList.get(counter).getTaskID().equals(taskID)) {
				taskList.get(counter).setTaskDesc(updatedString);
				break;
			}
			if (counter == taskList.size() - 1) {
				System.out.println("Task ID: " + taskID + " not found.");
			}
		}
	}

	// Update the task name.
	public void updateTaskName(String updatedString, String taskID) {
		for (int counter = 0; counter < taskList.size(); counter++) {
			if (taskList.get(counter).getTaskID().equals(taskID)) {
				taskList.get(counter).setTaskName(updatedString);
				break;
			}
			if (counter == taskList.size() - 1) {
				System.out.println("Task ID: " + taskID + " not found.");
			}
		}
	}
}
