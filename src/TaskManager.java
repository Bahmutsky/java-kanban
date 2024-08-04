import tasks.Task;
import tasks.Subtask;
import tasks.Epic;

import java.util.*;

public class TaskManager {
    private static Map<Integer, Task> idToTask = new HashMap<>();
    private static Map<Integer, Task> idToSubtask = new HashMap<>();
    private static Map<Integer, Task> idToEpic = new HashMap<>();
    static int id = 1;

    public Task addNewTask(Task newTask) {
        int newId = generateNewId();
        newTask.setId(newId);

        if(newTask.getClass() == Task.class) {
            idToTask.put(newTask.getId(), newTask);
            System.out.println("Задача добавлена");
        } else if(newTask.getClass() == Subtask.class) {
            Subtask subtask;
            subtask = (Subtask) newTask;

            if(subtask.getCurrentEpic() != null) {
                subtask.getCurrentEpic().addSubtask(newTask.getId(), newTask);
                subtask.getCurrentEpic().refreshEpicStatus();
                idToSubtask.put(newTask.getId(), newTask);
                System.out.println("Подзадача добавлена");
            } else {
                System.out.println("Подзадача должна иметь Эпик");
                return null;
            }
        } else if (newTask.getClass() == Epic.class) {
            idToEpic.put(newTask.getId(), newTask);
            System.out.println("Эпик добавлен");
        } else {
            System.out.println("Неизвестная сущность");
            return null;
        }
        return newTask;
    }

    public Task updateTask(Task updatedTask) {
        int id = updatedTask.getId();

        return updatedTask;
    }

    private int generateNewId() {
        return id++;
    }
}
