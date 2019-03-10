package isula.aco.upmsp;

import isula.aco.Environment;
import isula.aco.exception.InvalidInputException;

public class UpmspEnvironment extends Environment {

    public UpmspEnvironment(double[][] problemGraph) throws InvalidInputException {
        super(problemGraph);
    }

    protected double[][] createPheromoneMatrix() {
        return new double[0][];
    }
}
