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
        Integer taskId = deleteTask.getId();
        Task removedTask;
        if (idToTask.containsKey(taskId) && deleteTask.getClass() == Task.class) {
            removedTask = idToTask.get(taskId);
            idToTask.remove(taskId);

        } else if (idToSubtask.containsKey(taskId) && deleteTask.getClass() == Subtask.class) {
            Subtask subtask = (Subtask) idToSubtask.get(taskId);
            subtask.getCurrentEpic().removeSubtask(taskId);
            subtask.getCurrentEpic().refreshEpicStatus();
            removedTask = idToSubtask.get(taskId);
            idToSubtask.remove(taskId);

        } else if (idToEpic.containsKey(taskId) && deleteTask.getClass() == Epic.class) {
            removedTask = idToEpic.get(taskId);
            idToEpic.remove(taskId);

        } else {
            System.out.print("Указанный id задачи отсутствует ");
            return null;
        }
        System.out.println("Задача удалена: " + removedTask);
        return removedTask;
    }

    public ArrayList<Task> getAllTasks() {
        if (!idToTask.isEmpty()) {
            return new ArrayList<>(idToTask.values());
        }
        System.out.print("Список задач пустой");
        return null;
    }

    public ArrayList<Task> getAllSubtasks() {
        if (!idToSubtask.isEmpty()) {
            return new ArrayList<>(idToSubtask.values());
        }
        System.out.print("Список подзадач пуст");
        return null;
    }

    public ArrayList<Task> getAllEpic() {
        if (!idToEpic.isEmpty()) {
            return new ArrayList<>(idToEpic.values());
        }
        System.out.print("Список эпиков пуст");
        return null;
    }

    public Task getTask(Task task) {
        Task requestedTask;
        if (idToTask.containsKey(task.getId())) {
            requestedTask = idToTask.get(task.getId());

        } else if (idToSubtask.containsKey(task.getId())) {
            requestedTask = idToSubtask.get(task.getId());

        } else if (idToEpic.containsKey(task.getId())) {
            requestedTask = idToEpic.get(task.getId());

        } else {
            System.out.print("Указанный id отсутствует");
            return null;
        }

        System.out.print("Задача");
        return requestedTask;
    }

    public ArrayList<Task> getEpicSubtasks(Task epicTask) {
        Epic epic;

        try {
            epic = (Epic) epicTask;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        }

        if (!epic.getSubtasks().isEmpty()) {
            ArrayList<Task> subtasks = new ArrayList<>(epic.getSubtasks().values());
            System.out.print("Эпик " + epic.getName() + " с подзадачами");
            return subtasks;
        }

        System.out.print("Эпик не имеет подзадач");
        return null;
    }

    public void deleteEpicSubtasks(Task epicTask) {
        Epic epic;
        try {
            epic = (Epic) epicTask;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return;
        }

        if (!epic.getSubtasks().isEmpty()) {
            epic.clearSubtasks();
            epic.refreshEpicStatus();
            System.out.println("Удалены все подзадачи эпика: " + epic.getName());
        } else {
            System.out.println("Эпик не имеет подзадач");
        }
    }

    public void deleteAllTasks() {
        int tasksSum = 0;
        if (!idToTask.isEmpty()) {
            tasksSum = idToTask.size();
            idToTask.clear();
        }

        System.out.println("Удалено " + tasksSum + " задач");
    }

    public void deleteAllSubtasks() {
        int tasksSum = 0;
        if (!idToSubtask.isEmpty()) {
            tasksSum = idToSubtask.size();
            idToSubtask.clear();
            for (Task epicTask : idToEpic.values()) {
                Epic epic = (Epic) epicTask;
                epic.clearSubtasks();
                epic.refreshEpicStatus();
            }
        }

        System.out.println("Удалено " + tasksSum + " подзадач");
    }

    public void deleteAllEpic() {
        int tasksSum = 0;
        if (!idToEpic.isEmpty()) {
            tasksSum += idToEpic.size();
            idToEpic.clear();
        }
        System.out.println("Удалено " + tasksSum + " эпиков");
    }

    private int generateNewId() {
        return id++;
    }
}
