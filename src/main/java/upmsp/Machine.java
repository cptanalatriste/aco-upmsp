package upmsp;

public class Machine {

    private Integer id = null;
    private Integer currentJobIndex = null;
    private Integer expectedFinishTime = null;

    public Machine(Integer machineId) {
        this.id = machineId;
    }

    public Integer getId() {
        return this.id;
    }

    public void updateStatus(Integer currentTime) {
    }


    public boolean isAvailable() {
        return false;
    }


    public void setCurrentJob(Integer currentJobIndex, Double jobTime) {
    }

    public boolean isFinished() {
        return false;
    }

    public Integer getCurrentJob() {
        return 0;
    }


}
