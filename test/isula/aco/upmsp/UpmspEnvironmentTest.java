package isula.aco.upmsp;

import isula.aco.exception.InvalidInputException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.BeforeClass;
import org.junit.Test;
import upmsp.isula.sample.AcoUpmspWithIsula;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UpmspEnvironmentTest {

    private static final String TEST_FILE = "target/classes/very_small_example.xlsx";
    public static final int SHEET_INDEX = 0;

    private static UpmspEnvironment testEnvironment = null;
    private static int numberOfMachines = 2;
    private static int numberOfJobs = 4;

    private static int highWeightJobIndex = 1;
    private static int lowWeightJobIndex = 2;
    private static double delta = 0.01;


    @BeforeClass
    public static void setUp() throws IOException, InvalidFormatException, InvalidInputException {
        double[][] problemRepresentation = AcoUpmspWithIsula.getProblemFromFile(TEST_FILE, SHEET_INDEX);
        testEnvironment = new UpmspEnvironment(problemRepresentation);
    }


    @Test
    public void createPheromoneMatrix() {

        double[][] pheromoneMatrix = testEnvironment.createPheromoneMatrix();

        assertEquals(pheromoneMatrix.length, numberOfMachines);
        assertEquals(pheromoneMatrix[0].length, numberOfJobs);
    }

    @Test
    public void getNumberOfJobs() {
        assertEquals(testEnvironment.getNumberOfJobs(), numberOfJobs);
    }

    @Test
    public void getNumberOfMachines() {
        assertEquals(testEnvironment.getNumberOfMachines(), numberOfMachines);
    }

    @Test
    public void getMachineCatalogue() {
        List<Integer> expectedMachines = Arrays.asList(1, 2);
        assertThat(testEnvironment.getMachineCatalogue(), is(expectedMachines));
    }

    @Test
    public void getProcessingTime() {
        double seniorEasyAndHigh = 2.0;

        assertEquals(testEnvironment.getProcessingTime(1, highWeightJobIndex), seniorEasyAndHigh, delta);

        double juniorHardAndLow = 18.0;
        assertEquals(testEnvironment.getProcessingTime(2, lowWeightJobIndex), juniorHardAndLow, delta);
    }

    @Test
    public void getJobWeight() {

        int highWeightValue = 3;
        int lowWeightValue = 1;

        assertEquals(testEnvironment.getJobWeight(highWeightJobIndex), highWeightValue, delta);
        assertEquals(testEnvironment.getJobWeight(lowWeightJobIndex), lowWeightValue, delta);

    }
}