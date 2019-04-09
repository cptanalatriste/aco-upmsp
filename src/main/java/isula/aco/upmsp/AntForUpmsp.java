package isula.aco.upmsp;

import isula.aco.Ant;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * An Ant for the UPMSP problem. Its solutions are assignments of jobs to machines.
 */
public class AntForUpmsp extends Ant<Integer, UpmspEnvironment> {

    private static Logger logger = Logger.getLogger(AntForUpmsp.class
            .getName());

    public AntForUpmsp(UpmspEnvironment environment) {
        super();
        this.setSolution(new Integer[environment.getNumberOfJobs()]);
    }

    public List<Integer> getNeighbourhood(UpmspEnvironment upmspEnvironment) {
        return upmspEnvironment.getMachineCatalogue();
    }

    public Double getHeuristicValue(Integer machineId, Integer jobIndex,
                                    UpmspEnvironment environment) {

        double weightedProcessingTime = environment.getJobWeight(jobIndex) / environment.getProcessingTime(machineId, jobIndex);
        return weightedProcessingTime;
    }


    public Double getPheromoneTrailValue(Integer machineId, Integer jobIndex,
                                         UpmspEnvironment upmspEnvironment) {

        int machineIndex = machineId - 1;
        double[][] pheromoneMatrix = upmspEnvironment.getPheromoneMatrix();

        return pheromoneMatrix[machineIndex][jobIndex];
    }


    public void setPheromoneTrailValue(Integer machineId, Integer jobIndex, UpmspEnvironment upmspEnvironment,
                                       Double value) {
        int machineIndex = machineId - 1;
        double[][] pheromoneMatrix = upmspEnvironment.getPheromoneMatrix();

        pheromoneMatrix[machineIndex][jobIndex] = value;
    }

    /**
     * A machine, our solution component, can have multiple jobs scheduled. Even all of them.
     * Hence, this function always returns false.
     *
     * @param component
     * @return
     */
    @Override
    public boolean isNodeVisited(Integer component) {
        return false;
    }

    public boolean isSolutionReady(UpmspEnvironment upmspEnvironment) {
        return this.getCurrentIndex() == upmspEnvironment.getNumberOfJobs();
    }

    public double getSolutionCost(UpmspEnvironment upmspEnvironment) {

        double[] weightedCompletitionTimes = new double[upmspEnvironment.getNumberOfJobs()];

        IntStream.range(0, upmspEnvironment.getNumberOfMachines() + 1).forEachOrdered(machineId -> {

            List<Integer> jobsPerMachine = this.getSortedJobs(machineId, upmspEnvironment);
            ListIterator<Integer> iterator = jobsPerMachine.listIterator();

            double currentTime = 0;

            while (iterator.hasNext()) {
                int jobIndex = iterator.next();

                double jobCompletitionTime = currentTime + upmspEnvironment.getProcessingTime(machineId, jobIndex);
                weightedCompletitionTimes[jobIndex] = jobCompletitionTime * upmspEnvironment.getJobWeight(jobIndex);

                currentTime = jobCompletitionTime;

            }
        });

        return DoubleStream.of(weightedCompletitionTimes).sum();
    }

    private List<Integer> getSortedJobs(Integer machineId, UpmspEnvironment upmspEnvironment) {

        List<Integer> assignedJobs = new ArrayList<>();

        for (int jobIndex = 0; jobIndex < this.getSolution().length; jobIndex++) {
            if (this.getSolution()[jobIndex] == machineId) {
                assignedJobs.add(jobIndex);
            }
        }

        assignedJobs.sort((oneJob, anotherJob) -> {
            Double oneJobHeuristic = upmspEnvironment.getJobWeight(oneJob) / upmspEnvironment.getProcessingTime(machineId,
                    oneJob);
            Double anotherJobHeuristic = upmspEnvironment.getJobWeight(anotherJob) / upmspEnvironment.getProcessingTime(machineId,
                    anotherJob);

            return anotherJobHeuristic.compareTo(oneJobHeuristic);
        });

        return assignedJobs;
    }


}
