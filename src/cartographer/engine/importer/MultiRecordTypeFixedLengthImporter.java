package cartographer.engine.importer;

import cartographer.engine.Conversion;
import cartographer.engine.DataField;
import cartographer.engine.DataRow;
import cartographer.engine.DataType;
import cartographer.engine.Row;
import cartographer.engine.format.TypeFormatter;
import cartographer.engine.io.ProgressBar;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.common.util.ListUtils;
import nuberplex.store.Store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;


/**
 * @author casey
 */
public class MultiRecordTypeFixedLengthImporter extends AbstractImporter
{
    // private final static Logger LOG =
    // LogManager.getLogger(MultiRecordTypeFixedLengthImporter.class);
    private final Charset charset;


    public MultiRecordTypeFixedLengthImporter(final Conversion conversion)
    {
        super(conversion);
        this.charset = Charset.forName("ASCII");
    }


    @Override
    public Importer importData(final ProgressBar progressBar)
            throws Exception
    {
        final var file = getConversion().getSourceData().getDataFile();
        if (file == null)
        {
            throw new RequiredParameterException("file");
        }
        if (file.exists() == false)
        {
            throw new FileNotFoundException(file.getPath());
        }
        final var dictionary = getConversion().getSourceDictionary();
        final var rows = dictionary.getRows();
        final var data = getConversion().getSourceData();
        data.setDataSize(file.length());
        data.save();
        // final var rowsHash = new HashMap<String, Row>();
        // rows.forEach(row -> rowsHash.put(row.getLabel(), row));
        final var lineCount = lineCount(file);
        Long lineIndex = 0L;
        try (final var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))))
        {
            if (progressBar != null)
            {
                progressBar.updatePosition(0);
                progressBar.updateMaximum(lineCount.intValue());
            }
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                lineIndex++;
                final var row = parseRecordType(rows, line);
                if (row != null)
                {
                    // final var row = rows.stream().filter(r ->
                    // r.getLabel().equals(recordType)).findFirst().orElse(null);
                    final var dataRow = new DataRow();
                    dataRow.setDataID(data.getID());
                    dataRow.setDictionaryID(dictionary.getID());
                    dataRow.setRowID(row.getID());
                    dataRow.setLineIndex(lineIndex);
                    dataRow.setSkipRow(false);
                    dataRow.save();
                    final var array = parseArray(row, line);
                    int n = 0;
                    for (final var field : row.getFields())
                    {
                        final var dataField = new DataField();
                        dataField.setDictionaryID(row.getDictionaryID());
                        dataField.setDataTypeID(field.getDataTypeID());
                        dataField.setDataFormat(field.getDataFormat());
                        dataField.setDataRowID(dataRow.getID());
                        dataField.setFieldID(field.getID());
                        dataField.setCreateUser(dataRow.getCreateUser());
                        dataField.setUpdateUser(dataRow.getUpdateUser());
                        final var value = ListUtils.GET(array, n).strip();
                        dataField.setOriginalValue(value);
                        dataField.setValue(TypeFormatter.INSTANCE.format(dataField.getDataType(), field.getDataFormat(), value));
                        dataField.save();
                        n++;
                    }
                    Store.COMMIT();
                }
                if (progressBar != null && lineIndex % 20 == 0)
                {
                    progressBar.updatePosition(lineIndex.intValue());
                }
            }
        }
        if (progressBar != null)
        {
            progressBar.updatePosition(0);
        }
        return this;
    }


    private ArrayList<String> parseArray(final Row row, final String line)
            throws IOException
    {
        final var record = new ArrayList<String>();
        final var buffer = ByteBuffer.wrap(line.getBytes());
        final var chars = charset.decode(buffer);
        for (final var field : row.getFields())
        {
            final String value = chars.slice(field.getPosition() - 1, field.getLength().intValue()).toString();
            record.add(value);
        }
        return record;
    }


    private Long lineCount(final File file)
            throws FileNotFoundException,
                IOException
    {
        try (final var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))))
        {
            return reader.lines().count();
        }
    }


    private Row parseRecordType(final ArrayList<Row> rows, final String line)
    {
        // TO HARD AT THIS TIME, DON'T THINK YOU CAN DO THIS?
        // rows.stream()..forEach(row->row.getFields().stream());
        // rows.stream().collect(Collectors.toList()).
        // forEach(row -> row.getFields().stream().filter(field -> field.getDataType().type() ==
        // DataType.Type.TYPE).findFirst()
        // .orElse(null));
        for (final Row row : rows)
        {
            for (final var field : row.getFields())
            {
                if (field.getDataType().type() == DataType.Type.TYPE)
                {
                    // guessing a string length check needs to be done where, but going to be lazy.
                    final String recordType = line.substring(field.getPosition() - 1, field.getLength().intValue());
                    // System.out.println(String.format("RT: [%s]", recordType));
                    if (recordType.equals(row.getLabel()))
                    {
                        // return recordType;
                        return row;
                    }
                }
            }
        }
        return null;
    }
}
