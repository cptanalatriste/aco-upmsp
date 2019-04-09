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

import static aco.upmsp.test.TestingUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AntForUpmspTest {

    private static AntForUpmsp testAnt = null;
    private static UpmspEnvironment testEnvironment = null;


    @BeforeClass
    public static void setUp() throws IOException, InvalidFormatException, InvalidInputException {
        double[][] problemRepresentation = AcoUpmspWithIsula.getProblemFromFile(TEST_FILE, SHEET_INDEX);

        testEnvironment = new UpmspEnvironment(problemRepresentation);
        testAnt = new AntForUpmsp(testEnvironment);
    }


    @Test
    public void getNeighbourhood() {
        List<Integer> expectedNeighbpurhood = Arrays.asList(1, 2);
        assertThat(testAnt.getNeighbourhood(testEnvironment), is(expectedNeighbpurhood));

    }

    @Test
    public void getHeuristicValue() {
        double heuristicValue = testAnt.getHeuristicValue(SENIOR_MACHINE_ID, HIGH_JOB_INDEX, testEnvironment);
        assertEquals(heuristicValue, HIGH_WEIGHT_VALUE / TIME_SENIOR_EASY_HIGH, DELTA);

        heuristicValue = testAnt.getHeuristicValue(JUNIOR_MACHINE_ID, LOW_JOB_INDEX, testEnvironment);
        assertEquals(heuristicValue, LOW_WEIGHT_VALUE / TIME_JUNIOR_HARD_LOW, DELTA);
    }

    @Test
    public void getPheromoneTrailValue() {
        double samplePheromoneValue = 10.0;

        testAnt.setPheromoneTrailValue(SENIOR_MACHINE_ID, HIGH_JOB_INDEX, testEnvironment, samplePheromoneValue);
        assertEquals(samplePheromoneValue, testAnt.getPheromoneTrailValue(SENIOR_MACHINE_ID, HIGH_JOB_INDEX,
                testEnvironment), DELTA);

    }

    @Test
    public void getSolutionCost() {
        testAnt.visitNode(SENIOR_MACHINE_ID);
        testAnt.visitNode(SENIOR_MACHINE_ID);
        testAnt.visitNode(SENIOR_MACHINE_ID);

        assertFalse(testAnt.isSolutionReady(testEnvironment));
        testAnt.visitNode(SENIOR_MACHINE_ID);
        assertTrue(testAnt.isSolutionReady(testEnvironment));

        double onlySeniorTime = 58;
        assertEquals(testAnt.getSolutionCost(testEnvironment), onlySeniorTime, DELTA);

        testAnt.clear();

        testAnt.visitNode(SENIOR_MACHINE_ID);
        testAnt.visitNode(SENIOR_MACHINE_ID);
        testAnt.visitNode(SENIOR_MACHINE_ID);
        testAnt.visitNode(JUNIOR_MACHINE_ID);

        double withHelpTime = 67.0;
        assertEquals(testAnt.getSolutionCost(testEnvironment), withHelpTime, DELTA);


    }


}