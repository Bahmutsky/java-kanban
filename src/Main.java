public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task newTask = new Task("Задача 1", "Описание задачи");
        Task createdTask = taskManager.addNewTask(newTask);

        Task updatedTask = new Task(1, "Вынести мусор");
        taskManager.updateTask(updatedTask);

    }
}
