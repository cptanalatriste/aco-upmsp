package isula.aco.upmsp;

import isula.aco.Ant;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * An Ant for the UPMSP problem. Its solutions are assignments of jobs to machines.
 */
public class AntForUpmsp extends Ant<Integer, UpmspEnvironment> {

    public AntForUpmsp(UpmspEnvironment environment) {
        super();
        this.setSolution(new Integer[environment.getNumberOfJobs()]);
    }

    public List<Integer> getNeighbourhood(UpmspEnvironment upmspEnvironment) {
        int numberOfMachines = upmspEnvironment.getNumberOfMachines();
        List<Integer> machineList = IntStream.range(1, numberOfMachines).boxed().collect(Collectors.toList());
        return machineList;

    }

    public Double getHeuristicValue(Integer solutionComponent, Integer positionInSolution,
                                    UpmspEnvironment environmentnvironment) {

        double processingTime = environmentnvironment.getProcessingTime(solutionComponent, positionInSolution);
        return processingTime;
    }


    public Double getPheromoneTrailValue(Integer integer, Integer integer2, UpmspEnvironment upmspEnvironment) {
        return null;
    }

    public boolean isSolutionReady(UpmspEnvironment upmspEnvironment) {
        return false;
    }

    public double getSolutionCost(UpmspEnvironment upmspEnvironment) {
        return 0;
    }


    public void setPheromoneTrailValue(Integer integer, Integer integer2, UpmspEnvironment upmspEnvironment, Double aDouble) {

    }
}
