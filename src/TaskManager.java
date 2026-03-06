import java.io.*;
import java.util.*;

public class TaskManager {

    private static final String FILE_NAME = "tasks.txt";

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public List<Task> loadTasks() {

        List<Task> tasks = new ArrayList<>();

        try {

            File file = new File(FILE_NAME);

            if (!file.exists())
                file.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                if (!line.trim().isEmpty())
                    tasks.add(Task.fromCSV(line));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading tasks.");
        }

        return tasks;
    }

    public void saveTasks(List<Task> tasks) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (Task t : tasks) {

                bw.write(t.toCSV());
                bw.newLine();
            }

            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving tasks.");
        }
    }

    public int generateTaskId(List<Task> tasks) {

        int max = 0;

        for (Task t : tasks) {

            if (t.getTaskId() > max)
                max = t.getTaskId();
        }

        return max + 1;
    }

    public void addTask(int userId, String name, String desc, String priority) {

        List<Task> tasks = loadTasks();

        int id = generateTaskId(tasks);

        Task task = new Task(userId, name, id, desc, priority, "Pending");

        tasks.add(task);

        saveTasks(tasks);

        System.out.println(GREEN + "Task Added Successfully!" + RESET);
    }

    public void viewTasks(int userId) {

        List<Task> tasks = loadTasks();

        System.out.println();
        System.out.printf("%-8s %-35s %-15s %-10s\n",
                "TaskID", "Description", "Priority", "Status");

        System.out.println("--------------------------------------------------------------------------");

        for (Task t : tasks) {

            if (t.getUserId() == userId) {

                String desc = t.getDescription();

                // Limit description length so it doesn't break table
                if (desc.length() > 35)
                    desc = desc.substring(0, 32) + "...";

                String priority = t.getPriority();
                String status = t.getStatus();

                if (priority.equalsIgnoreCase("High"))
                    priority = RED + priority + RESET;

                if (status.equalsIgnoreCase("Done"))
                    status = GREEN + status + RESET;

                System.out.printf("%-8d %-35s %-10s %-10s\n",
                        t.getTaskId(),
                        desc,
                        priority,
                        status);
            }
        }
    }

    public void markDone(int taskId) {

        List<Task> tasks = loadTasks();
        boolean found = false;

        for (Task t : tasks) {

            if (t.getTaskId() == taskId) {

                t.setStatus("Done");
                found = true;
            }
        }

        if (!found) {

            System.out.println("Task ID not found.");
            return;
        }

        saveTasks(tasks);

        System.out.println(GREEN + "Task marked as Done." + RESET);
    }

    public void deleteTask(int taskId) {

        List<Task> tasks = loadTasks();

        tasks.removeIf(t -> t.getTaskId() == taskId);

        saveTasks(tasks);

        System.out.println("Task deleted.");
    }

    public void searchTasks(int userId, String keyword) {

        List<Task> tasks = loadTasks();

        System.out.println("\nSearch Results:");

        for (Task t : tasks) {

            if (t.getUserId() == userId &&
                    t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {

                System.out.printf("%d | %s | %s | %s\n",
                        t.getTaskId(),
                        t.getDescription(),
                        t.getPriority(),
                        t.getStatus());
            }
        }
    }

    public boolean validateUser(int id, String name) {

        List<Task> tasks = loadTasks();

        for (Task t : tasks) {

            if (t.getUserId() == id) {

                if (t.getUserName().equalsIgnoreCase(name))
                    return true;
                else
                    return false;
            }
        }

        return true;
    }
}