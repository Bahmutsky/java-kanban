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

    public List<Task> getSubtasks() {
        List<Task> subtaskList = new ArrayList<>(subtasks.values());
        return subtaskList;
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
                TaskStatus subStatus = subtask.getStatus();

                if (subStatus.equals(TaskStatus.IN_PROGRESS)) {
                    break;
                } else if (subStatus.equals(TaskStatus.NEW)) {
                    countNew++;
                } else if (subStatus.equals(TaskStatus.DONE)){
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
    public TaskType getType() {
        return TaskType.EPIC;
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
