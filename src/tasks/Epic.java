package tasks;

import java.util.*;

public class Epic extends Task{

    private Map<Integer, Task> subtasks = new HashMap<>();

    public Epic(Integer id, String name, String description) {
        super(id, name, description);
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id) {
        super(id);
    }

    public void addSubtask(Integer id, Task subtask) {
        subtasks.put(id, subtask);
    }

    public Map<Integer, Task> getSubtasks() {
        return subtasks;
    }

    public void removeSubtask(Integer id) {
        subtasks.remove(id);
    }

    public void clearSubtasks() {
        subtasks.clear();
    }

    public void refreshEpicStatus() {
        int countNew = 0;
        int countDone = 0;

        if (subtasks.isEmpty()) {
            this.status = TaskStatus.NEW;
        } else {
            for (Task subtask : subtasks.values()) {
                if (subtask.getStatus().equals(TaskStatus.NEW)) {
                    countNew++;
                } else if (subtask.getStatus().equals(TaskStatus.DONE)){
                    countDone++;
                }
            }

            if (countNew == subtasks.size()) {
                this.status = TaskStatus.NEW;
            } else if (countDone == subtasks.size()) {
                this.status = TaskStatus.DONE;
            } else {
                this.status = TaskStatus.IN_PROGRESS;
            }
        }
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
