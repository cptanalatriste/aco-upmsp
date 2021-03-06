package upmsp.isula.sample;

import isula.aco.*;
import isula.aco.algorithms.antsystem.OfflinePheromoneUpdate;
import isula.aco.algorithms.antsystem.PerformEvaporation;
import isula.aco.algorithms.antsystem.RandomNodeSelection;
import isula.aco.algorithms.antsystem.StartPheromoneMatrix;
import isula.aco.upmsp.AntForUpmsp;
import isula.aco.upmsp.UpmspEnvironment;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AcoUpmspWithIsula {

    public static Logger logger = Logger.getLogger(AcoUpmspWithIsula.class.getName());

    public static void main(String... args) {
        logger.info("ANT COLONY OPTIMIZATION FOR THE UNRELATED PARALLEL MACHINE SCHEDULING PROBLEM");

        try {
            double[][] problemRepresentation = getProblemFromFile(UpmspProblemConfiguration.INPUT_FILE,
                    UpmspProblemConfiguration.SHEET_INDEX);
            UpmspEnvironment environment = new UpmspEnvironment(problemRepresentation);

            UpmspProblemConfiguration configurationProvider = new UpmspProblemConfiguration(environment);
            AntColony<Integer, UpmspEnvironment> antColony = getAntColony(configurationProvider);

            AcoProblemSolver<Integer, UpmspEnvironment> acoProblemSolver = new AcoProblemSolver<>();
            acoProblemSolver.initialize(environment, antColony, configurationProvider);
            acoProblemSolver.addDaemonActions(new StartPheromoneMatrix<>(), new PerformEvaporation<>());

            acoProblemSolver.addDaemonActions(getPheromoneUpdatePolicy());

            acoProblemSolver.getAntColony().addAntPolicies(new RandomNodeSelection<>());
            acoProblemSolver.solveProblem();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DaemonAction<Integer, UpmspEnvironment> getPheromoneUpdatePolicy() {
        return new OfflinePheromoneUpdate<Integer, UpmspEnvironment>() {
            @Override
            protected double getPheromoneDeposit(Ant<Integer, UpmspEnvironment> ant, Integer positionInSolution,
                                                 Integer solutionComponent, UpmspEnvironment environment,
                                                 ConfigurationProvider configurationProvider) {
                int constantQ = 1;
                return constantQ / ant.getSolutionCost(environment);
            }

        };
    }


    private static AntColony<Integer, UpmspEnvironment> getAntColony(UpmspProblemConfiguration configurationProvider) {
        return new AntColony<Integer, UpmspEnvironment>(configurationProvider.getNumberOfAnts()) {
            @Override
            protected Ant<Integer, UpmspEnvironment> createAnt(UpmspEnvironment environment) {
                AntForUpmsp antForUpmsp = new AntForUpmsp(environment);
                return antForUpmsp;
            }
        };

    }

    public static double[][] getProblemFromFile(String dataFile, int sheetIndex) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(new File(dataFile));
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        DataFormatter dataFormatter = new DataFormatter();

        List<List<Double>> allRows = new ArrayList<>();

        sheet.forEach(row -> {
            List<Double> rowInformation = new ArrayList<>();

            row.forEach(cell -> {
                if (cell.getColumnIndex() > 0) {
                    Double cellValue = Double.parseDouble(dataFormatter.formatCellValue(cell));
                    rowInformation.add(cellValue);
                }

            });

            allRows.add(rowInformation);

        });


        double asArray[][] = new double[allRows.size()][];
        int rowCounter = 0;

        for (List<Double> rowAsList : allRows) {
            asArray[rowCounter++] = ArrayUtils.toPrimitive(rowAsList.toArray(new Double[rowAsList.size()]));
        }


        return asArray;
    }
}
