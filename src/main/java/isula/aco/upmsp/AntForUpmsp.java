package isula.aco.upmsp;

import isula.aco.Ant;

import java.util.List;
import java.util.logging.Logger;

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
        return 1/processingTime;
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
        return 0;
    }


    public void setPheromoneTrailValue(Integer integer, Integer integer2, UpmspEnvironment upmspEnvironment, Double aDouble) {

    }
}
