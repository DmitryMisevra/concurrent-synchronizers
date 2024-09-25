import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {

    private final int numberOfTasks;

    public ComplexTaskExecutor(int numOfTasks) {
        this.numberOfTasks = numOfTasks;
    }

    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);
        List<String> results = Collections.synchronizedList(new ArrayList<>());
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> combineResults(results));

        for (int i = 0; i < numberOfTasks; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                try {
                    ComplexTask task = new ComplexTask(taskId);
                    results.add(task.execute());
                    System.out.println("Task " + taskId + " executed and waiting");
                    barrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    private void combineResults(List<String> results) {
        System.out.println("Combining results:");
        for (String result : results) {
            System.out.println(result);
        }
    }
}
