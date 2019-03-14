package isula.aco.upmsp;

import isula.aco.Ant;

import java.util.List;

/**
 * An Ant for the UPMSP problem. Its solutions are assignments of jobs to machines.
 */
public class AntForUpmsp extends Ant<Integer, UpmspEnvironment> {

    public AntForUpmsp(UpmspEnvironment environment) {
        super();
        this.setSolution(new Integer[environment.getNumberOfJobs()]);
    }

    public List<Integer> getNeighbourhood(UpmspEnvironment upmspEnvironment) {
        return null;
    }

    public boolean isSolutionReady(UpmspEnvironment upmspEnvironment) {
        return false;
    }

    public double getSolutionCost(UpmspEnvironment upmspEnvironment) {
        return 0;
    }

    public Double getHeuristicValue(Integer integer, Integer integer2, UpmspEnvironment upmspEnvironment) {
        return null;
    }


    public Double getPheromoneTrailValue(Integer integer, Integer integer2, UpmspEnvironment upmspEnvironment) {
        return null;
    }

    public void setPheromoneTrailValue(Integer integer, Integer integer2, UpmspEnvironment upmspEnvironment, Double aDouble) {

    }
}
