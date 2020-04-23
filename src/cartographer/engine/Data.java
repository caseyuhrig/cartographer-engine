package cartographer.engine;

import nuberplex.common.lang.exception.RequiredPropertyException;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;


public class Data extends AbstractStorable<Data>
{
    private static final long serialVersionUID = 1L;

    protected static Logger LOG = LogManager.getLogger(Data.class);

    private Long dictionaryID = null;

    private String dataPath = null;

    private Long dataSize = null;

    private ArrayList<DataRow> dataRows = null;

    private static StorableMetaData META = null;

    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "data";
            META.add(this, "dictionary_id", "dictionaryID");
            META.add(this, "data_path", "dataPath");
            META.add(this, "data_size", "dataSize");
        }
        return META;
    }


    public Long getDictionaryID()
    {
        return dictionaryID;
    }


    public Data setDictionaryID(final Long dictionaryID)
    {
        this.dictionaryID = dictionaryID;
        return this;
    }


    public File getDataFile()
    {
        return new File(dataPath);
    }


    public String getDataPath()
    {
        return dataPath;
    }


    public Data setDataPath(final String dataPath)
    {
        this.dataPath = dataPath;
        return this;
    }


    public Long getDataSize()
    {
        return this.dataSize;
    }


    public Data setDataSize(final Long dataSize)
    {
        this.dataSize = dataSize;
        return this;
    }


    public ArrayList<DataRow> getDataRows()
    {
        if (getID() == null)
        {
            throw new RequiredPropertyException("id");
        }
        // caching
        if (dataRows == null)
        {
            dataRows = new ArrayList<>();
            Store.POPULATE_LIST(dataRows, DataRow.class, "data_row", "data_id", getID(), "line_index");
        }
        return dataRows;
    }


    public Dictionary getDictionary()
    {
        return new Dictionary().setID(getDictionaryID()).load();
    }
}
