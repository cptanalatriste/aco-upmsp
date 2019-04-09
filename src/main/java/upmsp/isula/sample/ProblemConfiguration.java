package upmsp.isula.sample;

import isula.aco.ConfigurationProvider;

public class ProblemConfiguration implements ConfigurationProvider {

    public static final String INPUT_FILE = "target/classes/Small_Problem_Data4m_20n.xls";
    public static final int SHEET_INDEX = 7;

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
    public double getInitialPheromoneValue() {
        //The Tau(I, j, k) parameter from the paper.
        return 10;
    }

    @Override
    public double getEvaporationRatio() {
        //The rho parameter from the paper.
        return 1 - 0.01;
    }


    @Override
    public double getHeuristicImportance() {
        //This parameter is not mentioned in the paper.Current value taken from the Ant System paper
        return 5;
    }

    @Override
    public double getPheromoneImportance() {
        //This parameter is not mentioned in the paper.Current value taken from the Ant System paper
        return 1;
    }
}
