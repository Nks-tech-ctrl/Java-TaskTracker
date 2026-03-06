public class Task {

    private int userId;
    private String userName;
    private int taskId;
    private String description;
    private String priority;
    private String status;

    public Task(int userId, String userName, int taskId,
                String description, String priority, String status) {

        this.userId = userId;
        this.userName = userName;
        this.taskId = taskId;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toCSV() {
        return userId + "," + userName + "," + taskId + ","
                + description + "," + priority + "," + status;
    }

    public static Task fromCSV(String line) {

        String[] data = line.split(",");

        int userId = Integer.parseInt(data[0]);
        String userName = data[1];
        int taskId = Integer.parseInt(data[2]);
        String description = data[3];
        String priority = data[4];
        String status = data[5];

        return new Task(userId, userName, taskId, description, priority, status);
    }
}