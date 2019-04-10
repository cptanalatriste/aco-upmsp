package upmsp.naive;

import isula.aco.exception.InvalidInputException;
import isula.aco.upmsp.UpmspEnvironment;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

import static upmsp.isula.sample.AcoUpmspWithIsula.getProblemFromFile;
import static upmsp.isula.sample.UpmspProblemConfiguration.INPUT_FILE;
import static upmsp.isula.sample.UpmspProblemConfiguration.SHEET_INDEX;

public class NaiveUpmspScheduller {

    private UpmspEnvironment environment = null;

    public NaiveUpmspScheduller(double[][] problemRepresentation) throws InvalidInputException {
        this.environment = new UpmspEnvironment(problemRepresentation);
    }


    public static void main(String... args) {

        try {
            double[][] problemRepresentation = getProblemFromFile(INPUT_FILE,
                    SHEET_INDEX);
            NaiveUpmspScheduller naiveScheduller = new NaiveUpmspScheduller(problemRepresentation);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
