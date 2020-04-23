package cartographer.engine.io.reader;

import cartographer.engine.io.ProgressBar;

import nuberplex.common.util.BooleanUtils;

import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;


public class ExcelXmlArrayReader extends AbstractArrayReader
{
    private XSSFWorkbook workbook = null;

    private XSSFSheet sheet = null;

    private Iterator<Row> rowIterator = null;

    public ExcelXmlArrayReader(final InputStream input, final ProgressBar progressBar)
            throws IOException
    {
        super(input, progressBar);
        workbook = new XSSFWorkbook(this);
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


    public ExcelXmlArrayReader(final File file, final ProgressBar progressBar)
            throws IOException
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
            final ArrayList<String> results = new ArrayList<>();
            final Row row = rowIterator.next();
            final Integer cellCount = (int) row.getLastCellNum();
            for (int n = 0; n < cellCount; n++)
            {
                final Cell cell = row.getCell(n);
                switch (cell.getCellType())
                {
                    case BOOLEAN:
                        results.add(BooleanUtils.BOOLEAN(cell.getBooleanCellValue(), false).toString());
                        break;
                    case NUMERIC:
                        final String format = cell.getCellStyle().getDataFormatString();
                        final CellFormatResult value = CellFormat.getInstance(format).apply(cell);
                        results.add(value.text);
                        break;
                    case STRING:
                        results.add(cell.getRichStringCellValue().getString());
                        break;
                    case FORMULA:
                        throw new IOException(String.format("Cell type FORMULA not supported."));
                    case ERROR:
                        throw new IOException(String.format("Cell type ERROR not supported."));
                    case _NONE:
                        throw new IOException(String.format("Cell type _NONE not supported."));
                    case BLANK:
                        results.add("");
                        break;
                    default:
                        throw new IOException(String.format("Cell type % not supported.", cell.getCellType()));
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


    @Override
    public void close()
            throws IOException
    {
        workbook.close();
        super.close();
    }
}
