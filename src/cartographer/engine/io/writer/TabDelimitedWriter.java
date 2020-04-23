package cartographer.engine.io.writer;

import cartographer.engine.DataField;
import cartographer.engine.DataRow;
import cartographer.engine.Field;
import cartographer.engine.Row;

import nuberplex.common.util.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;


public class TabDelimitedWriter extends BufferedWriter
{
    protected static Logger LOG = LogManager.getLogger(TabDelimitedWriter.class);
    private Long lineIndex = null;


    public TabDelimitedWriter(final Writer out)
    {
        super(out);
        this.lineIndex = 1L;
    }


    public void writeHeader(final Row row)
            throws IOException
    {
        final StringBuilder line = new StringBuilder();
        for (final Field field : row.getFields())
        {
            line.append(StringUtils.COALESCE(field.getLabel(), ""));
            // FIXME Needs the delimiter.
            line.append("\t");
        }
        write(line.toString());
        newLine();
        flush();
    }


    public void writeRow(final DataRow dataRow)
            throws IOException
    {
        final StringBuilder line = new StringBuilder();
        for (final DataField dataField : dataRow.getDataFields())
        {
            line.append(StringUtils.COALESCE(dataField.getValue(), ""));
            // FIXME Needs the delimiter.
            line.append("\t");
            dataField.save();
        }
        write(line.toString());
        newLine();
        flush();
        dataRow.setLineIndex(lineIndex++);
        dataRow.save();
    }
}
