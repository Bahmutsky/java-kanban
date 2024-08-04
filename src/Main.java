import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task newTask = new Task("Задача #1", "Описание задачи", TaskStatus.NEW);
        System.out.println(taskManager.addNewTask(newTask));
        System.out.println("-----------------------------------------");

        newTask = new Task("Задача #2", "Описание задачи", TaskStatus.NEW);
        System.out.println(taskManager.addNewTask(newTask));
        System.out.println("-----------------------------------------");


        Epic newEpic1 = new Epic("Эпик #1", "Описание эпика");
        System.out.println(taskManager.addNewTask(newEpic1));
        System.out.println("-----------------------------------------");

        Epic newEpic2 = new Epic("Эпик #2", "Описание эпика");
        System.out.println(taskManager.addNewTask(newEpic2));
        System.out.println("-----------------------------------------");


        newTask = new Subtask("Подзадача #1", "Описание подзадачи", newEpic1);
        System.out.println(taskManager.addNewTask(newTask));
        System.out.println("-----------------------------------------");

        newTask = new Subtask("Подзадача #2", "Описание подзадачи", newEpic1);
        System.out.println(taskManager.addNewTask(newTask));
        System.out.println("-----------------------------------------");
    }
}
