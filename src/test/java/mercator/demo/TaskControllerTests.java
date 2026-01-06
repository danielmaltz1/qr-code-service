package mercator.demo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskControllerTests {

    @Test
    void getTasksTest() {
        TaskController taskController = new TaskController();
        List<Task> tasks = taskController.getTasks();
        List<Task> expectedTasks = List.of(
                new Task(1, "task1", "A first test task", false),
                new Task(2, "task2", "A second test task", true)
        );
        for (int i = 0; i < expectedTasks.size(); i++) {
            assertEquals(expectedTasks.get(i).getId(), tasks.get(i).getId());
            assertEquals(expectedTasks.get(i).getName(), tasks.get(i).getName());
            assertEquals(expectedTasks.get(i).getDescription(), tasks.get(i).getDescription());
            assertEquals(expectedTasks.get(i).isCompleted(), tasks.get(i).isCompleted());
        }
    }
}
