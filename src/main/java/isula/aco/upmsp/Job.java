package isula.aco.upmsp;

public class Job {

    private int jobId;
    private double[] processingTime;
    private int dueDate;
    private double weight;

    public Job(int jobId, double[] processingTime, int dueDate, double weight) {
        this.jobId = jobId;
        this.processingTime = processingTime;
        this.dueDate = dueDate;
        this.weight = weight;
    }

}
