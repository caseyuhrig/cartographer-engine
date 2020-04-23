package cartographer.engine;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;


public class Dictionary extends AbstractStorable<Dictionary>
{
    private static Logger LOG = LogManager.getLogger(Dictionary.class);
    private static StorableMetaData META = null;
    private Long customerID = null;
    private String label = null;
    private String dataPath = null;
    private Long headerLineNumber = null;
    private Long skipLines = null;
    private Boolean dynamic = null;
    private Boolean xml = null;
    private Boolean createHeader = null;
    private Long rowLength = null;
    private String columnDelimiter = null;
    private String fieldDelimiter = null;
    private ArrayList<Row> rows = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "dictionary";
            META.add(this, "customer_id", "customerID"); // required
            META.add(this, "label", "label"); // required, not empty
            META.add(this, "header_line_number", "headerLineNumber");
            META.add(this, "create_header", "createHeader");
            META.add(this, "skip_lines", "skipLines");
            META.add(this, "dynamic", "dynamic");
            META.add(this, "xml", "xml");
            META.add(this, "data_path", "dataPath");
            META.add(this, "row_length", "rowLength");
            META.add(this, "column_delimiter", "columnDelimiter");
            META.add(this, "field_delimiter", "fieldDelimiter");
        }
        return META;
    }


    public static Dictionary createEmptyDictionary(final String label)
    {
        final var dictionary = new Dictionary();
        dictionary.setCustomerID(UserProfile.CURRENT().getCustomerID());
        dictionary.setLabel(label);
        dictionary.setCreateHeader(false);
        dictionary.setSkipLines(0L);
        dictionary.setDynamic(false);
        dictionary.setXml(false);
        dictionary.save();
        final var defaultRow = new Row();
        defaultRow.setDictionaryID(dictionary.getID());
        defaultRow.setLabel("DEFAULT");
        defaultRow.setCreateUser(dictionary.getCreateUser());
        defaultRow.setRowOrder(1);
        defaultRow.setMinOccurrence(Row.DEFAULT_MIN_OCCURRENCE);
        defaultRow.setMaxOccurrence(Row.DEFAULT_MAX_OCCURRENCE);
        defaultRow.save();
        return dictionary;
    }


    public Boolean hasTypesRows()
    {
        for (final var row : getRows())
        {
            for (final var field : row.getFields())
            {
                if (field.getDataType().getIdentifier().equals("TYPE"))
                {
                    return true;
                }
            }
        }
        return false;
    }


    public Long getCustomerID()
    {
        return customerID;
    }


    public Dictionary setCustomerID(final Long customerID)
    {
        this.customerID = customerID;
        return this;
    }


    public String getLabel()
    {
        return label;
    }


    public Dictionary setLabel(final String label)
    {
        this.label = label;
        return this;
    }


    public String getDataPath()
    {
        return dataPath;
    }


    public Dictionary setDataPath(final String dataPath)
    {
        this.dataPath = dataPath;
        return this;
    }


    public Long getHeaderLineNumber()
    {
        return headerLineNumber;
    }


    public Dictionary setHeaderLineNumber(final Long headerLineNumber)
    {
        this.headerLineNumber = headerLineNumber;
        return this;
    }


    public Long getSkipLines()
    {
        return skipLines;
    }


    public Dictionary setSkipLines(final Long skipLines)
    {
        this.skipLines = skipLines;
        return this;
    }


    public Boolean getDynamic()
    {
        return dynamic;
    }


    public Dictionary setDynamic(final Boolean dynamic)
    {
        this.dynamic = dynamic;
        return this;
    }


    public Boolean getXml()
    {
        return xml;
    }


    public Dictionary setXml(final Boolean xml)
    {
        this.xml = xml;
        return this;
    }


    public Boolean getCreateHeader()
    {
        return createHeader;
    }


    public Dictionary setCreateHeader(final Boolean createHeader)
    {
        this.createHeader = createHeader;
        return this;
    }


    public Long getRowLength()
    {
        return rowLength;
    }


    public Dictionary setRowLength(final Long rowLength)
    {
        this.rowLength = rowLength;
        return this;
    }


    public String getColumnDelimiter()
    {
        return columnDelimiter;
    }


    public Dictionary setColumnDelimiter(final String columnDelimiter)
    {
        this.columnDelimiter = columnDelimiter;
        return this;
    }


    public String getFieldDelimiter()
    {
        return fieldDelimiter;
    }


    public Dictionary setFieldDelimiter(final String fieldDelimiter)
    {
        this.fieldDelimiter = fieldDelimiter;
        return this;
    }


    public Row firstRow()
    {
        return getRows().get(0);
    }


    public ArrayList<Row> getRows()
    {
        if (rows == null)
        {
            rows = new ArrayList<>();
            // TODO Optimize query SELECT dictionary_row(s).
            try (final var rs = Store.selectList("SELECT id FROM dictionary_row WHERE dictionary_id = ? ORDER BY row_order",
                                                 getID()))
            {
                while (rs.next())
                {
                    rows.add(new Row().setID(rs.getLong("id")).load());
                }
            }
            catch (final SQLException sqle)
            {
                LOG.error(sqle.getLocalizedMessage(), sqle);
            }
        }
        return rows;
    }


    public Dictionary load(final Long customerID, final String label)
    {
        if (customerID == null)
        {
            throw new RequiredParameterException("customerID");
        }
        if (label == null || label.isBlank())
        {
            throw new RequiredParameterException("label");
        }
        // TODO Optimize query SELECT dictionary (customer_id, label).
        setID(Store.selectID("SELECT id FROM dictionary WHERE customer_id = ? AND label = ?", customerID, label));
        load();
        return this;
    }


    @Override
    public void clear()
    {
        super.clear();
        rows = null;
    }


    public void clearRows()
    {
        rows = null;
    }


    @Override
    public String toString()
    {
        return label;
    }
}
