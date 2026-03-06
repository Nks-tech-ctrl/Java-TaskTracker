import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TaskManager manager = new TaskManager();

        int userId = 0;
        String name = "";

        System.out.println("***** Multi User Task Tracker *****");

        try {

            System.out.print("Enter User ID: ");
            userId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            name = sc.nextLine();

            if (!manager.validateUser(userId, name)) {
                System.out.println("Access denied. ID exists with another name!");
                return;
            }

        } catch (Exception e) {
            System.out.println("Invalid credentials!");
            return;
        }

        int choice;

        while (true) {

            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task Done");
            System.out.println("4. Delete Task");
            System.out.println("5. Search Task");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Description: ");
                    String desc = sc.nextLine();

                    System.out.print("Priority (High/Medium/Low): ");
                    String priority = sc.nextLine();

                    manager.addTask(userId, name, desc, priority);
                    break;

                case 2:

                    manager.viewTasks(userId);
                    break;

                case 3:

                    System.out.print("Enter Task ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    manager.markDone(id);
                    break;

                case 4:

                    System.out.print("Enter Task ID: ");
                    int del = sc.nextInt();
                    sc.nextLine();

                    manager.deleteTask(del);
                    break;

                case 5:

                    System.out.print("Keyword: ");
                    String keyword = sc.nextLine();

                    manager.searchTasks(userId, keyword);
                    break;

                case 6:

                    System.out.println("Exiting... Goodbye!");
                    return;

                default:

                    System.out.println("Invalid choice!");
            }
        }
    }
}