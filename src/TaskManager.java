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

        if(idToTask.containsKey(id) && updatedTask.getClass() == Task.class) {
            idToTask.put(updatedTask.getId(), updatedTask);
            System.out.println("Задача обновлена");
            return updatedTask;

        } else if(idToSubtask.containsKey(id) && updatedTask.getClass() == Subtask.class) {
            Subtask subtaskMap = (Subtask) idToSubtask.get(updatedTask.getId());
            Subtask subtaskTemp = (Subtask) updatedTask;

            if(subtaskTemp.getCurrentEpic() == null) {
                subtaskTemp.setCurrentEpic(subtaskMap.getCurrentEpic());
            }

            subtaskMap.getCurrentEpic().addSubtask(id, subtaskTemp);
            subtaskMap.getCurrentEpic().refreshEpicStatus();
            idToSubtask.put(id, subtaskTemp);
            System.out.print("Подзадача обновлена");
            return subtaskTemp;

        } else if (idToEpic.containsKey(id) && updatedTask.getClass() == Epic.class) {
            idToEpic.put(id, updatedTask);
            System.out.print("Эпик обновлен");
            return updatedTask;

        } else {
            System.out.print("Задачи с таким id нет");
            return null;
        }
    }

    public Task deleteTask(Task deleteTask) {

        return deleteTask;
    }
    private int generateNewId() {
        return id++;
    }
}
