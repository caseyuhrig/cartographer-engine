/**
 * (c) 2018, Casey Uhrig
 */
package cartographer.engine;

import cartographer.engine.DataType.Type;

import nuberplex.common.lang.exception.RequiredPropertyException;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


/**
 * Field object for a Dictionary.
 * @author casey
 * @see Dictionary#getFields()
 */
public class Field extends AbstractStorable<Field>
{
    private static final long serialVersionUID = 1L;

    protected static Logger LOG = LogManager.getLogger(Field.class);

    private Long rowID = null;

    private Long dataTypeID = null;

    private String dataFormat = null;

    private String label = null;

    private Integer position = null;

    private Long length = null;

    private Long aggregateDefinitionID = null;

    private Long aggregateRowID = null;

    private Long aggregateFieldID = null;

    private Integer minOccurrence = null;

    private Integer maxOccurrence = null;

    private static StorableMetaData META = null;

    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "field";
            META.add(this, "row_id", "rowID");
            META.add(this, "data_type_id", "dataTypeID");
            META.add(this, "data_format", "dataFormat");
            META.add(this, "label", "label");
            META.add(this, "position", "position");
            META.add(this, "length", "length");
            META.add(this, "aggregate_definition_id", "aggregateDefinitionID");
            META.add(this, "aggregate_row_id", "aggregateRowID");
            META.add(this, "aggregate_field_id", "aggregateFieldID");
            META.add(this, "min_occurrence", "minOccurrence");
            META.add(this, "max_occurrence", "maxOccurrence");
        }
        return META;
    }


    @Override
    public String toString()
    {
        return label;
    }


    @SuppressWarnings("unchecked")
    public ArrayList<Rule> getRules()
    {
        return (ArrayList<Rule>) Store.POPULATE_LIST(Rule.class, "rule", "field_id", getID(), "rule_order");
    }


    @SuppressWarnings("unchecked")
    public ArrayList<DynamicField> getDynamicFields()
    {
        return (ArrayList<DynamicField>) Store
                .POPULATE_LIST(DynamicField.class, "dynamic_field", "field_id", getID(), "dynamic_field_order");
    }


    public Long getDataTypeID()
    {
        return this.dataTypeID;
    }


    public Field setDataTypeID(final Long dataTypeID)
    {
        this.dataTypeID = dataTypeID;
        return this;
    }


    public DataType getDataType()
    {
        if (getDataTypeID() == null)
        {
            throw new RequiredPropertyException("dataTypeID");
        }
        return new DataType().setID(getDataTypeID()).load();
    }


    public Field setDataType(final Type type)
    {
        setDataTypeID(new DataType().setIdentifier(type.name()).load().getID());
        return this;
    }


    public String getDataFormat()
    {
        return dataFormat;
    }


    public Field setDataFormat(final String dataFormat)
    {
        this.dataFormat = dataFormat;
        return this;
    }


    public Long getRowID()
    {
        return rowID;
    }


    public Row getRow()
    {
        if (getRowID() == null)
        {
            throw new RequiredPropertyException("rowID");
        }
        return new Row().setID(rowID).load();
    }


    public Field setRowID(final Long rowID)
    {
        this.rowID = rowID;
        return this;
    }


    public String getLabel()
    {
        return label;
    }


    public Field setLabel(final String label)
    {
        this.label = label;
        return this;
    }


    public Long getLength()
    {
        return length;
    }


    public Field setLength(final Long length)
    {
        this.length = length;
        return this;
    }


    public Integer getPosition()
    {
        return position;
    }


    public Field setPosition(final Integer position)
    {
        this.position = position;
        return this;
    }


    /**
     * Returns true if an aggregate definition has been defined for this field.
     * @return True if an aggregate definition has been defined.
     */
    public Boolean hasAggregateDefinition()
    {
        return aggregateDefinitionID != null;
    }


    public Long getAggregateDefinitionID()
    {
        return aggregateDefinitionID;
    }


    public AggregateDefinition getAggregateDefinition()
    {
        if (getAggregateDefinitionID() == null)
        {
            return null;
        }
        return new AggregateDefinition().setID(aggregateDefinitionID).load();
    }


    public Field setAggregateDefinitionID(final Long aggregateDefinitionID)
    {
        this.aggregateDefinitionID = aggregateDefinitionID;
        return this;
    }


    public Long getAggregateRowID()
    {
        return aggregateRowID;
    }


    public Row getAggregateRow()
    {
        if (getAggregateRowID() == null)
        {
            return null;
        }
        return new Row().setID(aggregateRowID).load();
    }


    public void setAggregateRowID(final Long aggregateRowID)
    {
        this.aggregateRowID = aggregateRowID;
    }


    public Long getAggregateFieldID()
    {
        return aggregateFieldID;
    }


    public Field getAggregateField()
    {
        if (getAggregateFieldID() == null)
        {
            return null;
        }
        return new Field().setID(aggregateFieldID).load();
    }


    public void setAggregateFieldID(final Long aggregateFieldID)
    {
        this.aggregateFieldID = aggregateFieldID;
    }


    public Integer getMinOccurrence()
    {
        return minOccurrence;
    }


    public Field setMinOccurrence(final Integer minOccurrence)
    {
        this.minOccurrence = minOccurrence;
        return this;
    }


    public Integer getMaxOccurrence()
    {
        return maxOccurrence;
    }


    public Field setMaxOccurrence(final Integer maxOccurrence)
    {
        this.maxOccurrence = maxOccurrence;
        return this;
    }
}
