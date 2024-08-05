package controllers;

import tasks.Task;
import tasks.Subtask;
import tasks.Epic;

import java.util.*;

public class TaskManager {
    private Map<Integer, Task> idToTask = new HashMap<>();
    private Map<Integer, Task> idToSubtask = new HashMap<>();
    private Map<Integer, Task> idToEpic = new HashMap<>();
    private int id = 1;

    public Task addNewTask(Task newTask) {
        int newId = generateNewId();
        newTask.setId(newId);

        if(newTask.getClass() == Task.class) {
            idToTask.put(newTask.getId(), newTask);
        } else if(newTask.getClass() == Subtask.class) {
            Subtask subtask;
            try {
                subtask = (Subtask) newTask;
            } catch (Exception exception) {
                return null;
            }
            Epic epic = subtask.getCurrentEpic();
            if(epic != null) {
                epic.addSubtask(newTask.getId(), newTask);
                epic.refreshEpicStatus();
                idToSubtask.put(newTask.getId(), newTask);
            } else {
                return null;
            }
        } else if (newTask.getClass() == Epic.class) {
            idToEpic.put(newTask.getId(), newTask);
        } else {
            return null;
        }
        return newTask;
    }

    public Task updateTask(Task updatedTask) {
        int id = updatedTask.getId();

        if(idToTask.containsKey(id) && updatedTask.getClass() == Task.class) {
            idToTask.put(updatedTask.getId(), updatedTask);
            return updatedTask;

        } else if(idToSubtask.containsKey(id) && updatedTask.getClass() == Subtask.class) {
            Subtask subtaskMap = (Subtask) idToSubtask.get(updatedTask.getId());
            Subtask subtaskTemp;

            try {
                subtaskTemp = (Subtask) updatedTask;
            } catch (Exception exception) {
                return null;
            }

            if(subtaskTemp.getCurrentEpic() == null) {
                subtaskTemp.setCurrentEpic(subtaskMap.getCurrentEpic());
            }

            Epic epic =  subtaskMap.getCurrentEpic();
            epic.addSubtask(id, subtaskTemp);
            epic.refreshEpicStatus();
            idToSubtask.put(id, subtaskTemp);
            return subtaskTemp;

        } else if (idToEpic.containsKey(id) && updatedTask.getClass() == Epic.class) {
            idToEpic.put(id, updatedTask);
            return updatedTask;
        } else {
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
            Subtask subtask;
            try {
                subtask = (Subtask) idToSubtask.get(taskId);
            } catch (Exception exception) {
                return null;
            }

            Epic epic = subtask.getCurrentEpic();
            epic.removeSubtask(taskId);
            epic.refreshEpicStatus();
            removedTask = idToSubtask.get(taskId);
            idToSubtask.remove(taskId);

        } else if (idToEpic.containsKey(taskId) && deleteTask.getClass() == Epic.class) {
            removedTask = idToEpic.get(taskId);
            idToEpic.remove(taskId);
        } else {
            return null;
        }
        return removedTask;
    }

    public ArrayList<Task> getAllTasks() {
        if (!idToTask.isEmpty()) {
            return new ArrayList<>(idToTask.values());
        }
        return null;
    }

    public ArrayList<Task> getAllSubtasks() {
        if (!idToSubtask.isEmpty()) {
            return new ArrayList<>(idToSubtask.values());
        }
        return null;
    }

    public ArrayList<Task> getAllEpic() {
        if (!idToEpic.isEmpty()) {
            return new ArrayList<>(idToEpic.values());
        }
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
            return null;
        }
        return requestedTask;
    }

    public ArrayList<Task> getEpicSubtasks(Task epicTask) {
        Epic epic;

        try {
            epic = (Epic) epicTask;
        } catch (Exception exception) {
            return null;
        }

        if (!epic.getSubtasks().isEmpty()) {
            ArrayList<Task> subtasks = new ArrayList<>(epic.getSubtasks().values());
            return subtasks;
        }
        return null;
    }

    public void deleteEpicSubtasks(Task epicTask) {
        Epic epic;
        try {
            epic = (Epic) epicTask;
        } catch (Exception exception) {
            return;
        }

        if (!epic.getSubtasks().isEmpty()) {
            epic.clearSubtasks();
            epic.refreshEpicStatus();
        }
    }

    public void deleteAllTasks() {
        int tasksSum = 0;
        if (!idToTask.isEmpty()) {
            tasksSum = idToTask.size();
            idToTask.clear();
        }
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
    }

    public void deleteAllEpic() {
        int tasksSum = 0;
        if (!idToEpic.isEmpty()) {
            tasksSum += idToEpic.size();
            idToEpic.clear();
        }
    }

    private int generateNewId() {
        return id++;
    }
}
