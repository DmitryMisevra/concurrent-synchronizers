public class ComplexTask {
    private final int id;

    public ComplexTask(int id) {
        this.id = id;
    }

    public String execute() {
        return "result of task: " + id;
    }
}
