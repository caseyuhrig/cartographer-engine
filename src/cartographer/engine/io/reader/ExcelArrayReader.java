package cartographer.engine.io.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import nuberplex.common.util.BooleanUtils;

import cartographer.engine.io.ProgressBar;


public class ExcelArrayReader extends AbstractArrayReader
{
    private HSSFWorkbook workbook = null;

    private HSSFSheet sheet = null;

    private Iterator<Row> rowIterator = null;


    public ExcelArrayReader(final InputStream input, final ProgressBar progressBar)
            throws IOException
    {
        super(input, progressBar);
        workbook = new HSSFWorkbook(this);
        workbook.setMissingCellPolicy(Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        sheet = workbook.getSheetAt(0);
        rowIterator = sheet.iterator();
        final Integer maxRows = sheet.getLastRowNum();
        if (progressBar != null)
        {
            progressBar.updatePosition(0);
            progressBar.updateMaximum(maxRows);
        }
    }


    public ExcelArrayReader(final File file, final ProgressBar progressBar)
            throws FileNotFoundException, IOException
    {
        this(new FileInputStream(file), progressBar);
    }


    @Override
    public ArrayList<String> readArray()
            throws IOException
    {
        if (rowIterator.hasNext() == true)
        {
            lineNumber++;
            if (progressBar != null)
            {
                progressBar.updatePosition(lineNumber.intValue());
            }
            final ArrayList<String> results = new ArrayList<String>();
            final Row row = rowIterator.next();
            // final Integer rowNum = row.getRowNum();
            final Integer cellCount = (int) row.getLastCellNum();
            // System.out.format("ROW[%d] LAST CELL NUM: %d", rowNum, cellCount).println();
            final FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (int n = 0; n < cellCount; n++)
            {
                final Cell cell = row.getCell(n);
                final CellValue cellValue = evaluator.evaluate(cell);
                if (cellValue == null)
                {
                    // System.err.format("col = %d, row = %d: NULL cell value", n,
                    // lineNumber).println();
                    results.add("");
                }
                else
                {
                    switch (cellValue.getCellType())
                    {
                        case BOOLEAN:
                            results.add(BooleanUtils.BOOLEAN(cellValue.getBooleanValue(), false).toString());
                            break;
                        case NUMERIC:
                            final String format = cell.getCellStyle().getDataFormatString();
                            final CellFormatResult value = CellFormat.getInstance(format).apply(cell);
                            results.add(value.text);
                            break;
                        case STRING:
                            results.add(cellValue.getStringValue());
                            break;
                        case FORMULA:
                            throw new IOException(String.format("Cell type FORMULA not implemented."));
                        case ERROR:
                            throw new IOException(String.format("Cell type ERROR not implemented."));
                        case _NONE:
                            throw new IOException(String.format("Cell type _NONE not implemented."));
                        case BLANK:
                            results.add("");
                            break;
                        default:
                            throw new IOException(String.format("Cell type % not supported.", cell.getCellType()));
                    }
                }
            }
            // System.out.println("Results Size: " + results.size());
            return results;
        }
        else
        {
            if (progressBar != null)
            {
                progressBar.updatePosition(0);
            }
            return null;
        }
    }
}
