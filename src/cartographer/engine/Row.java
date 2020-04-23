package cartographer.engine;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.common.lang.exception.RequiredPropertyException;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableException;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Row extends AbstractStorable<Row>
{
    // private static Logger LOG = LogManager.getLogger(Row.class);
    public static Integer DEFAULT_MIN_OCCURRENCE = 1;
    public static Integer DEFAULT_MAX_OCCURRENCE = 1;
    /**
     * This is used to tell the setRowOrder(NEXT_ROW) method to automatically grab the next row
     * order value.
     */
    public static Integer NEXT_ROW = -1;
    private Long dictionaryID = null;
    private Long parentRowID = null;
    private Long nextRowID = null;
    private String position = null;
    private String label = null;
    private Integer rowOrder = null;
    private Integer minOccurrence = null;
    private Integer maxOccurrence = null;
    private ArrayList<Field> fields = null;
    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "dictionary_row";
            META.add(this, "dictionary_id", "dictionaryID"); // required
            META.add(this, "parent_row_id", "parentRowID");
            META.add(this, "next_row_id", "nextRowID");
            META.add(this, "position", "position");
            META.add(this, "label", "label"); // required, not empty
            META.add(this, "row_order", "rowOrder");
            META.add(this, "min_occurrence", "minOccurrence");
            META.add(this, "max_occurrence", "maxOccurrence");
        }
        return META;
    }


    public static Row create(final Long dictionaryID, final String label)
    {
        return new Row().setDictionaryID(dictionaryID).setLabel(label).save();
    }


    @Override
    public String toString()
    {
        return label;
    }


    public Boolean hasAggregateDefinitions()
    {
        for (final Field field : getFields())
        {
            if (field.hasAggregateDefinition() == false)
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Check to see if one of the fields in this row has an aggregate definition defined.
     * @return True or false if an aggregate definition exists in one of the fields in the row.
     */
    public Boolean hasAggregateDefinition()
    {
        for (final Field field : getFields())
        {
            if (field.hasAggregateDefinition() == true)
            {
                return true;
            }
        }
        return false;
    }


    public Field getFieldByAggregateDefinition(final AggregateDefinition.Identifier identifier)
    {
        if (identifier == null)
        {
            throw new RequiredParameterException("identifier");
        }
        for (final Field field : getFields())
        {
            if (field.hasAggregateDefinition() == true)
            {
                if (identifier.toString().equals(field.getAggregateDefinition().getIdentifier()) == true)
                {
                    return field;
                }
            }
        }
        throw new RuntimeException(String.format("Missing aggregate definition %", identifier));
    }


    public Boolean hasAggregateDefinition(final AggregateDefinition.Identifier identifier)
    {
        if (identifier == null)
        {
            throw new RequiredParameterException("identifier");
        }
        for (final Field field : getFields())
        {
            if (field.hasAggregateDefinition() == true)
            {
                if (identifier.toString().equals(field.getAggregateDefinition().getIdentifier()) == true)
                {
                    return true;
                }
            }
        }
        return false;
    }


    public Row load(final Long dictionaryID, final String label)
    {
        try (final ResultSet rs = Store
                .selectList("SELECT * FROM dictionary_row WHERE dictionary_id = ? AND label = ?", dictionaryID, label))
        {
            while (rs.next())
            {
                Store.POPULATE(this, rs);
            }
        }
        catch (final SQLException sqle)
        {
            throw new StorableException(sqle.getLocalizedMessage(), sqle);
        }
        return this;
    }


    public ArrayList<Field> getFields()
    {
        if (getID() == null)
        {
            throw new RequiredPropertyException("id");
        }
        if (fields == null)
        {
            // System.out.format("LOADING FIELDS FOR ROW %s", getLabel()).println();
            fields = new ArrayList<>();
            try (final ResultSet rs = Store.selectList("SELECT * FROM field WHERE row_id = ? ORDER BY position", getID()))
            {
                while (rs.next())
                {
                    final Field field = new Field();
                    Store.POPULATE(field, rs);
                    fields.add(field);
                }
            }
            catch (final SQLException sqle)
            {
                throw new StorableException(sqle.getLocalizedMessage(), sqle);
            }
        }
        return fields;
    }


    public Long getDictionaryID()
    {
        return dictionaryID;
    }


    public Dictionary getDictionary()
    {
        if (dictionaryID == null)
        {
            throw new RequiredPropertyException("dictionaryID");
        }
        return new Dictionary().setID(dictionaryID).load();
    }


    public Row setDictionaryID(final Long dictionaryID)
    {
        this.dictionaryID = dictionaryID;
        return this;
    }


    public Long getParentRowID()
    {
        return parentRowID;
    }


    public Row getParentRow()
    {
        if (parentRowID == null)
        {
            throw new RequiredPropertyException("parentRowID");
        }
        return new Row().setID(parentRowID).load();
    }


    public Row setParentRowID(final Long parentRowID)
    {
        this.parentRowID = parentRowID;
        return this;
    }


    public Long getNextRowID()
    {
        return nextRowID;
    }


    public Row getNextRow()
    {
        if (nextRowID == null)
        {
            throw new RequiredPropertyException("nextRowID");
        }
        return new Row().setID(nextRowID).load();
    }


    public Row setNextRowID(final Long nextRowID)
    {
        this.nextRowID = nextRowID;
        return this;
    }


    public String getPosition()
    {
        return position;
    }


    public Row setPosition(final String position)
    {
        this.position = position;
        return this;
    }


    public String getLabel()
    {
        return label;
    }


    public Row setLabel(final String label)
    {
        this.label = label;
        return this;
    }


    public Integer getRowOrder()
    {
        return rowOrder;
    }


    public Row setRowOrder(final Integer rowOrder)
    {
        if (rowOrder == NEXT_ROW)
        {
            try (final ResultSet rs = Store
                    .selectList("SELECT (COALESCE(MAX(row_order),0)+1)::INTEGER FROM dictionary_row WHERE dictionary_id = ?",
                                getDictionaryID()))
            {
                if (rs.next())
                {
                    setRowOrder(rs.getInt(1));
                }
            }
            catch (final SQLException sqle)
            {
                throw new StorableException(sqle.getLocalizedMessage(), sqle);
            }
        }
        else
        {
            this.rowOrder = rowOrder;
        }
        return this;
    }


    public Integer getMinOccurrence()
    {
        return minOccurrence;
    }


    public Row setMinOccurrence(final Integer minOccurrence)
    {
        this.minOccurrence = minOccurrence;
        return this;
    }


    public Integer getMaxOccurrence()
    {
        return maxOccurrence;
    }


    public Row setMaxOccurrence(final Integer maxOccurrence)
    {
        this.maxOccurrence = maxOccurrence;
        return this;
    }


    @Override
    public void clear()
    {
        super.clear();
        fields = null;
    }
}
