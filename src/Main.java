import controllers.TaskManager;
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

        System.out.println("Список эпиков, задач и подзадач");
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());


        newTask = new Task(1,"Задача #1 Изменение", "Описание задачи", TaskStatus.IN_PROGRESS);
        System.out.println(taskManager.updateTask(newTask));
        System.out.println("-----------------------------------------");

        newTask = new Task(2,"Задача #2 Изменение", "Описание задачи", TaskStatus.IN_PROGRESS);
        System.out.println(taskManager.updateTask(newTask));
        System.out.println("-----------------------------------------");

        newTask = new Subtask(5, "Подзадача #1 изменение", "Описание задачи", TaskStatus.DONE);
        System.out.println(taskManager.updateTask(newTask));
        System.out.println("-----------------------------------------");

        newTask = new Subtask(6, "Подзадача #2 изменение", "Описание задачи", TaskStatus.DONE);
        System.out.println(taskManager.updateTask(newTask));
        System.out.println("-----------------------------------------");

        newTask = new Subtask(7, "Подзадача #3 изменение", "Описание задачи", TaskStatus.IN_PROGRESS);
        System.out.println(taskManager.updateTask(newTask));
        System.out.println("-----------------------------------------");

        System.out.println("Список эпиков, задач и подзадач");
        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());


        taskManager.deleteTask(new Task(1));
        taskManager.deleteTask(new Epic(3));
        taskManager.deleteTask(new Subtask(5));

        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());

        taskManager.deleteAllTasks();
        taskManager.deleteAllSubtasks();
        taskManager.deleteAllEpic();

        System.out.println(taskManager.getAllEpic());
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllSubtasks());
    }
}
