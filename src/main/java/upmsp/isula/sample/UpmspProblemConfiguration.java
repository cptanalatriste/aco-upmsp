package upmsp.isula.sample;

import isula.aco.ConfigurationProvider;
import isula.aco.upmsp.UpmspEnvironment;

public class UpmspProblemConfiguration implements ConfigurationProvider {

    public static final String INPUT_FILE = "target/classes/Large_Problem_Data10m_100n.xls";
    public static final int SHEET_INDEX = 1;


    //public static final String INPUT_FILE = "target/classes/very_small_example.xlsx";
    //public static final int SHEET_INDEX = 0;


    private int numberOfAnts;

    public UpmspProblemConfiguration(UpmspEnvironment environment) {
        this.numberOfAnts = environment.getNumberOfJobs();
    }


    @Override
    public int getNumberOfAnts() {
        // From the Ant System paper, this should be "number comparable to the problem to be solved"
        return this.numberOfAnts;
    }

    @Override
    public int getNumberOfIterations() {
        //Value taken from the Ant System paper
        return 5000;
    }

    @Override
    public double getInitialPheromoneValue() {
        //In the Ant System paper, they only mention it should be a "small positive constant"
        return .05;
    }

    @Override
    public double getEvaporationRatio() {
        //Value taken from the Ant System paper
        return 1 - 0.05;
    }


    @Override
    public double getHeuristicImportance() {
        //Value taken from the Ant System paper
        return 5;
    }

    @Override
    public double getPheromoneImportance() {
        //Value taken from the Ant System paper
        return 1;
    }
}
