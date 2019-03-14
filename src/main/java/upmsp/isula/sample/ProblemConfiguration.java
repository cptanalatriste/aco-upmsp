package upmsp.isula.sample;

import isula.aco.ConfigurationProvider;

public class ProblemConfiguration implements ConfigurationProvider {

    public static final String INPUT_FILE = "target/classes/Large_Problem_Data10m_100n.xls";
    public static final int SHEET_INDEX = 0;

    @Override
    public int getNumberOfAnts() {
        // The No_of_Ants parameter from the paper.
        return 60;
    }

    @Override
    public int getNumberOfIterations() {
        // This value is not present in the paper. I infer it from the figures.
        return 3000;
    }

    @Override
    public double getEvaporationRatio() {
        return 0;
    }


    @Override
    public double getInitialPheromoneValue() {
        return 0;
    }

    @Override
    public double getHeuristicImportance() {
        return 0;
    }

    @Override
    public double getPheromoneImportance() {
        return 0;
    }
}
