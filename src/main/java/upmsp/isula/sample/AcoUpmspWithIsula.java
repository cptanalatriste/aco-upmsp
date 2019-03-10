package upmsp.isula.sample;

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
            double[][] problemRepresentation = getProblemFromFile(ProblemConfiguration.INPUT_FILE, ProblemConfiguration.SHEET_INDEX);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double[][] getProblemFromFile(String dataFile, int sheetIndex) throws IOException, InvalidFormatException {

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

                    logger.info("cellValue: " + cellValue);
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
