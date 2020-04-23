package cartographer.engine;

import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;


public class DataFormat extends AbstractStorable<DataFormat>
{
    protected static Logger LOG = LogManager.getLogger(DataFormat.class);
    private Long dataTypeID = null;
    private String format = null;
    private String customFormat = null;
    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "data_format";
            META.add(this, "data_type_id", "dataTypeID");
            META.add(this, "format", "format");
        }
        return META;
    }


    public Long getDataTypeID()
    {
        return dataTypeID;
    }


    public DataFormat setDataTypeID(final Long dataTypeID)
    {
        this.dataTypeID = dataTypeID;
        return this;
    }


    public String getFormat()
    {
        return format;
    }


    public DataFormat setFormat(final String format)
    {
        this.format = format;
        return this;
    }


    public String getCustomFormat()
    {
        return customFormat;
    }


    public DataFormat setCustomFormat(final String customFormat)
    {
        this.customFormat = customFormat;
        return this;
    }


    @Override
    public boolean equals(final Object object)
    {
        if (object == null)
        {
            return false;
        }
        if (object instanceof DataFormat)
        {
            final var dataFormat = DataFormat.class.cast(object);
            if (getID() == dataFormat.getID() && getFormat().equals(dataFormat.getFormat()))
            {
                return true;
            }
        }
        return false;
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(getID(), getFormat());
    }
}
