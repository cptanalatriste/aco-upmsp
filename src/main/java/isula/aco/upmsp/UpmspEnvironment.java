package isula.aco.upmsp;

import isula.aco.Environment;
import isula.aco.exception.InvalidInputException;

import java.util.logging.Logger;

public class UpmspEnvironment extends Environment {


    private static Logger logger = Logger.getLogger(UpmspEnvironment.class
            .getName());

    public UpmspEnvironment(double[][] problemRepresentation) throws InvalidInputException {
        super(problemRepresentation);
    }

    protected double[][] createPheromoneMatrix() {
        return new double[0][];
    }

    public int getNumberOfJobs() {
        return this.getProblemRepresentation()[0].length;
    }

    public int getNumberOfMachines() {
        return this.getProblemRepresentation().length - 3;
    }

    public double getProcessingTime(Integer machineId, Integer jobIndex) {

        double[] machineRowInfo = getProblemRepresentation()[machineId];
        double processingTime = machineRowInfo[jobIndex];
        return processingTime;
    }
}
