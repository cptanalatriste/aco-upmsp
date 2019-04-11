package upmsp.naive;

import isula.aco.exception.InvalidInputException;
import isula.aco.upmsp.UpmspEnvironment;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import upmsp.Machine;
import upmsp.isula.sample.AcoUpmspWithIsula;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;

import static upmsp.isula.sample.AcoUpmspWithIsula.getProblemFromFile;
import static upmsp.isula.sample.UpmspProblemConfiguration.INPUT_FILE;
import static upmsp.isula.sample.UpmspProblemConfiguration.SHEET_INDEX;

public class NaiveUpmspScheduler {

    public static Logger logger = Logger.getLogger(AcoUpmspWithIsula.class.getName());

    private UpmspEnvironment environment = null;
    private Machine[] machines = null;

    public NaiveUpmspScheduler(double[][] problemRepresentation) throws InvalidInputException {
        this.environment = new UpmspEnvironment(problemRepresentation);
        this.machines = new Machine[this.environment.getNumberOfMachines()];
    }


    private Double schedule() {

        double[] weightedCompletitionTimes = new double[this.environment.getNumberOfJobs()];

        PriorityQueue<Integer> prioririzedJobs = getJobQueue();
        int currentTime = 0;
        int pendingJobs = this.environment.getNumberOfJobs();

        while (pendingJobs > 0) {
            for (Machine machine : this.machines) {

                machine.updateStatus(currentTime);

                if (machine.isFinished()) {
                    Integer finishedJob = machine.getCurrentJob();
                    weightedCompletitionTimes[finishedJob] = currentTime * environment.getJobWeight(finishedJob);
                    pendingJobs -= 1;
                }

                if (machine.isAvailable()) {
                    Integer nextJobIndex = prioririzedJobs.poll();
                    Double jobTime = environment.getProcessingTime(machine.getId(), nextJobIndex);
                    machine.setCurrentJob(nextJobIndex, jobTime);
                }

            }

            currentTime += 1;
        }

        return DoubleStream.of(weightedCompletitionTimes).sum();

    }

    private PriorityQueue<Integer> getJobQueue() {
        return null;
    }


    public static void main(String... args) {

        try {
            double[][] problemRepresentation = getProblemFromFile(INPUT_FILE,
                    SHEET_INDEX);
            NaiveUpmspScheduler naiveScheduller = new NaiveUpmspScheduler(problemRepresentation);
            logger.info("Total weighted completition time: " + naiveScheduller.schedule());


        } catch (InvalidInputException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
