package isula.aco.upmsp;

import isula.aco.Ant;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
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

    public Double getHeuristicValue(Integer solutionComponent, Integer positionInSolution,
                                    UpmspEnvironment environmentnvironment) {

        double processingTime = environmentnvironment.getProcessingTime(solutionComponent, positionInSolution);
        return 1 / processingTime;
    }


    public Double getPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution,
                                         UpmspEnvironment upmspEnvironment) {

        int machineIndex = solutionComponent - 1;
        int jobIndex = positionInSolution;
        double[][] pheromoneMatrix = upmspEnvironment.getPheromoneMatrix();

        return pheromoneMatrix[machineIndex][jobIndex];
    }

    /**
     * A machine, our solution component, can have multiple jobs schedulled. Even all of them.
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

            List<Integer> jobsPerMachine = Arrays.asList(this.getSolution());
            ListIterator<Integer> iterator = jobsPerMachine.listIterator();

            double currentTime = 0;

            while (iterator.hasNext()) {
                int jobIndex = iterator.nextIndex();
                int machinePerJob = iterator.next();

                if (machinePerJob == machineId) {
                    double jobCompletitionTime = currentTime + upmspEnvironment.getProcessingTime(machineId, jobIndex);
                    weightedCompletitionTimes[jobIndex] = jobCompletitionTime * upmspEnvironment.getJobWeight(jobIndex);

                    currentTime = jobCompletitionTime;
                }
            }
        });

        return DoubleStream.of(weightedCompletitionTimes).sum();
    }


    public void setPheromoneTrailValue(Integer integer, Integer integer2, UpmspEnvironment upmspEnvironment, Double aDouble) {
        //Keep in mind how the pheromone train gets updated
    }
}
