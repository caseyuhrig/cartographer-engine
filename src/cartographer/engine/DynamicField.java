package cartographer.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nuberplex.common.lang.exception.RequiredPropertyException;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;


public class DynamicField extends AbstractStorable<DynamicField>
{
    private static final long serialVersionUID = 1L;

    protected static Logger LOG = LogManager.getLogger(DynamicField.class);

    private Long fieldID = null;

    private Long dataTypeID = null;

    private String dataFormat = null;

    private String label = null;

    private Integer dynamicFieldOrder = null;

    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "dynamic_field";
            META.add(this, "field_id", "fieldID");
            META.add(this, "data_type_id", "dataTypeID");
            META.add(this, "data_format", "dataFormat");
            META.add(this, "label", "label");
            META.add(this, "dynamic_field_order", "dynamicFieldOrder");
        }
        return META;
    }


    public Long getFieldID()
    {
        return fieldID;
    }


    public void setFieldID(final Long fieldID)
    {
        this.fieldID = fieldID;
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
        if (getDataTypeID() == null)
        {
            throw new RequiredPropertyException("dataTypeID");
        }
        return new DataType().setID(getDataTypeID()).load();
    }


    public String getDataFormat()
    {
        return dataFormat;
    }


    public void setDataFormat(final String dataFormat)
    {
        this.dataFormat = dataFormat;
    }


    public String getLabel()
    {
        return label;
    }


    public void setLabel(final String label)
    {
        this.label = label;
    }


    public Integer getDynamicFieldOrder()
    {
        return dynamicFieldOrder;
    }


    public void setDynamicFieldOrder(final Integer dynamicFieldOrder)
    {
        this.dynamicFieldOrder = dynamicFieldOrder;
    }
}
