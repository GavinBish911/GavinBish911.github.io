/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-320
 * Date: 	Sep 26, 2024
 * Description: This is the test class for TaskService.
 *************************/

package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import Main.Task;
import Main.TaskService;

class TaskServiceTest {

	/*
	 * The following tests exercise the TaskService class. In each test, a new
	 * service is created with default values for each field. Then the service is
	 * requested to make some change to the list of tasks and the result is tested
	 * to ensure the actual field matches what is expected.
	 *
	 * Because each task that gets created has a new automatically assigned taskID,
	 * and the tests are run based on that taskID, the order in which the tests are
	 * run depend on the value of each taskID. Therefore, the @Order annotation is
	 * used to keep the tests in a specific order.
	 *
	 * For evidence that the taskID is correct for each test, uncomment the
	 * service.displayTaskList(); prior to each assertion so that the records are
	 * displayed on the console for error checking.
	 */

	@Test
	@DisplayName("Test to ensure that service can add a task.")
	@Order(6)
	void testAddContact() {
		TaskService service = new TaskService();
		service.addTask("Task Name", "Description");
		service.displayTaskList();
		assertNotNull(service.getTask("0"), "Task was not added correctly.");
	}

	@Test
	@DisplayName("Test to ensure that service correctly deletes tasks.")
	@Order(5)
	void testDeleteContact() {
		TaskService service = new TaskService();
		service.addTask("Task Name", "Description");
		service.deleteTask("0");
		// Ensure that the contactList is now empty by creating a new empty contactList
		// to compare it with
		ArrayList<Task> taskListEmpty = new ArrayList<>();
		service.displayTaskList();
		assertEquals(service.taskList, taskListEmpty, "The contact was not deleted.");
	}

	@Test
	@DisplayName("Test to Update task description.")
	@Order(2)
	void testUpdateTaskDesc() {
		TaskService service = new TaskService();
		service.addTask("Task Name", "Description");
		service.updateTaskDesc("Updated Description", "1");
		service.displayTaskList();
		assertEquals("Updated Description", service.getTask("1").getTaskDesc(), "Task description was not updated.");
	}

	@Test
	@DisplayName("Test to Update task name")
	@Order(1)
	void testUpdateTaskName() {
		TaskService service = new TaskService();
		service.addTask("Task Name", "Description");
		service.updateTaskName("Updated Task Name", "3");
		service.displayTaskList();
		assertEquals("Updated Task Name", service.getTask("3").getTaskName(), "Task name was not updated.");
	}
}
