package cartographer.engine;

import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DataField extends AbstractStorable<DataField>
{
    private static final long serialVersionUID = 1L;
    protected static Logger LOG = LogManager.getLogger(DataField.class);
    private Long dataRowID = null;
    private Long dataTypeID = null;
    private String dataFormat = null;
    private Long dictionaryID = null;
    private Long fieldID = null;
    private String originalValue = null;
    private String value = null;
    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "data_field";
            META.add(this, "data_row_id", "dataRowID");
            META.add(this, "data_type_id", "dataTypeID");
            META.add(this, "data_format", "dataFormat");
            META.add(this, "dictionary_id", "dictionaryID");
            META.add(this, "field_id", "fieldID");
            META.add(this, "original_value", "originalValue");
            META.add(this, "value", "value");
        }
        return META;
    }


    public DataField load(final Long dataRowID, final Long fieldID)
    {
        setID(Store.selectID("SELECT * FROM data_field WHERE field_id = ? AND data_row_id = ?", fieldID, dataRowID));
        if (getID() != null)
        {
            load();
        }
        return this;
    }


    public Long getDataRowID()
    {
        return dataRowID;
    }


    public DataField setDataRowID(final Long dataRowID)
    {
        this.dataRowID = dataRowID;
        return this;
    }


    public Long getDataTypeID()
    {
        return dataTypeID;
    }


    public void setDataTypeID(final Long dataTypeID)
    {
        this.dataTypeID = dataTypeID;
    }


    public DataType getDataType()
    {
        return new DataType().setID(dataTypeID).load();
    }


    public String getDataFormat()
    {
        return dataFormat;
    }


    public DataField setDataFormat(final String dataFormat)
    {
        this.dataFormat = dataFormat;
        return this;
    }


    public Long getDictionaryID()
    {
        return dictionaryID;
    }


    public DataField setDictionaryID(final Long dictionaryID)
    {
        this.dictionaryID = dictionaryID;
        return this;
    }


    public Long getFieldID()
    {
        return fieldID;
    }


    public DataField setFieldID(final Long fieldID)
    {
        this.fieldID = fieldID;
        return this;
    }


    public String getOriginalValue()
    {
        return originalValue;
    }


    public DataField setOriginalValue(final String originalValue)
    {
        this.originalValue = originalValue;
        return this;
    }


    public String getValue()
    {
        return value;
    }


    public DataField setValue(final String value)
    {
        this.value = value;
        return this;
    }
}
