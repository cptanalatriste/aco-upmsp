package isula.aco.upmsp;

import aco.upmsp.test.TestingUtils;
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


    private static UpmspEnvironment testEnvironment = null;

    @BeforeClass
    public static void setUp() throws IOException, InvalidFormatException, InvalidInputException {
        double[][] problemRepresentation = AcoUpmspWithIsula.getProblemFromFile(TestingUtils.TEST_FILE,
                TestingUtils.SHEET_INDEX);
        testEnvironment = new UpmspEnvironment(problemRepresentation);
    }


    @Test
    public void createPheromoneMatrix() {

        double[][] pheromoneMatrix = testEnvironment.createPheromoneMatrix();

        assertEquals(pheromoneMatrix.length, TestingUtils.NUMBER_OF_MACHINES);
        assertEquals(pheromoneMatrix[0].length, TestingUtils.NUMBER_OF_JOBS);
    }

    @Test
    public void getNumberOfJobs() {
        assertEquals(testEnvironment.getNumberOfJobs(), TestingUtils.NUMBER_OF_JOBS);
    }

    @Test
    public void getNumberOfMachines() {
        assertEquals(testEnvironment.getNumberOfMachines(), TestingUtils.NUMBER_OF_MACHINES);
    }

    @Test
    public void getMachineCatalogue() {
        List<Integer> expectedMachines = Arrays.asList(1, 2);
        assertThat(testEnvironment.getMachineCatalogue(), is(expectedMachines));
    }

    @Test
    public void getProcessingTime() {

        assertEquals(testEnvironment.getProcessingTime(TestingUtils.SENIOR_MACHINE_ID, TestingUtils.HIGH_JOB_INDEX),
                TestingUtils.TIME_SENIOR_EASY_HIGH, TestingUtils.DELTA);
        assertEquals(testEnvironment.getProcessingTime(TestingUtils.JUNIOR_MACHINE_ID, TestingUtils.LOW_JOB_INDEX),
                TestingUtils.TIME_JUNIOR_HARD_LOW, TestingUtils.DELTA);
    }

    @Test
    public void getJobWeight() {


        assertEquals(testEnvironment.getJobWeight(TestingUtils.HIGH_JOB_INDEX), TestingUtils.HIGH_WEIGHT_VALUE,
                TestingUtils.DELTA);
        assertEquals(testEnvironment.getJobWeight(TestingUtils.LOW_JOB_INDEX), TestingUtils.LOW_WEIGHT_VALUE,
                TestingUtils.DELTA);

    }
}