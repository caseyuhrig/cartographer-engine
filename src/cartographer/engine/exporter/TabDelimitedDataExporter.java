package cartographer.engine.exporter;

import cartographer.engine.Conversion;
import cartographer.engine.Data;
import cartographer.engine.DataRow;
import cartographer.engine.Dictionary;
import cartographer.engine.Row;
import cartographer.engine.io.ProgressBar;
import cartographer.engine.io.writer.TabDelimitedWriter;

import nuberplex.store.Store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class TabDelimitedDataExporter extends AbstractExporter
{
    public TabDelimitedDataExporter(final Conversion conversion)
    {
        super(conversion);
    }


    @Override
    public Exporter exportData(final ProgressBar progressBar)
            throws Exception
    {
        final Data data = getConversion().getTargetData();
        final File file = new File(getConversion().getTargetDataPath());
        final Dictionary dictionary = data.getDictionary();
        data.setDictionaryID(dictionary.getID());
        data.setDataPath(file.getPath());
        data.setDataSize(0L);
        data.save();
        int n = 0;
        final Long rowCount = Store.selectLong(String.format("SELECT COUNT(*) FROM data_row WHERE data_id = %s", data.getID()));
        if (progressBar != null)
        {
            progressBar.updatePosition(0);
            progressBar.updateMaximum(rowCount.intValue());
        }
        try (final TabDelimitedWriter writer = new TabDelimitedWriter(new OutputStreamWriter(new FileOutputStream(file))))
        {
            if (dictionary.getCreateHeader() == true)
            {
                if (dictionary.getRows().size() == 1)
                {
                    final Row row = dictionary.getRows().get(0);
                    writer.writeHeader(row);
                }
                else
                {
                    throw new UnsupportedOperationException("Cannot write a header for a multi-record type delimited file.");
                }
            }
            for (final DataRow dataRow : data.getDataRows())
            {
                if (dataRow.getSkipRow() == false)
                {
                    writer.writeRow(dataRow);
                    if (progressBar != null)
                    {
                        // System.out.println(String.format("Channel Position: %s",
                        // channel.position()));
                        progressBar.updatePosition(n);
                    }
                    n++;
                }
            }
            data.save();
            Store.COMMIT();
        }
        data.setDataSize(file.length());
        data.save();
        if (progressBar != null)
        {
            progressBar.updatePosition(0);
        }
        Store.COMMIT();
        return this;
    }
}
