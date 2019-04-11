package isula.aco.upmsp;

import isula.aco.Environment;
import isula.aco.exception.InvalidInputException;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UpmspEnvironment extends Environment {


    private static Logger logger = Logger.getLogger(UpmspEnvironment.class
            .getName());

    private int numberOfMachines;
    private List<Integer> machineCatalogue;


    public UpmspEnvironment(double[][] problemRepresentation) throws InvalidInputException {
        super(problemRepresentation);
        this.numberOfMachines = getNumberOfMachines();
        this.machineCatalogue = IntStream.range(1, this.numberOfMachines + 1).boxed().collect(Collectors.toList());
    }

    protected double[][] createPheromoneMatrix() {
        return new double[getNumberOfMachines()][getNumberOfJobs()];
    }

    public int getNumberOfJobs() {
        return this.getProblemRepresentation()[0].length;
    }

    public int getNumberOfMachines() {
        return this.getProblemRepresentation().length - 3;
    }

    public List<Integer> getMachineCatalogue() {
        return machineCatalogue;
    }

    public double getProcessingTime(Integer machineId, Integer jobIndex) {
        double[] machineRowInfo = getProblemRepresentation()[machineId];
        double processingTime = machineRowInfo[jobIndex];
        return processingTime;
    }

    public double getJobWeight(Integer jobIndex) {
        int weightRow = this.getProblemRepresentation().length - 1;
        double[] weightInfoRow = this.getProblemRepresentation()[weightRow];
        return weightInfoRow[jobIndex];
    }
}
