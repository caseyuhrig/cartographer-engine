package cartographer.engine;

import nuberplex.common.lang.exception.RequiredPropertyException;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DataRow extends AbstractStorable<DataRow>
{
    private static final long serialVersionUID = 1L;
    protected static Logger LOG = LogManager.getLogger(DataRow.class);
    private Long dataID = null;
    private Long dictionaryID = null;
    private Long rowID = null;
    private Long lineIndex = null;
    private String line = null;
    private Boolean skipRow = null;
    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "data_row";
            META.add(this, "data_id", "dataID");
            META.add(this, "dictionary_id", "dictionaryID");
            META.add(this, "row_id", "rowID");
            META.add(this, "line_index", "lineIndex");
            META.add(this, "line", "line");
            META.add(this, "skip_row", "skipRow");
        }
        return META;
    }


    public DataRow loadBy_Data_Dictionary_Row(final Long dataID, final Long dictionaryID, final Long rowID, final Long lineIndex)
    {
        setID(Store.selectID("SELECT id FROM data_row WHERE data_id = ? AND dictionary_id = ? AND row_id = ? AND line_index = ?",
                             dataID,
                             dictionaryID,
                             rowID,
                             lineIndex));
        if (getID() != null)
        {
            load();
        }
        return this;
    }


    public Long getDataID()
    {
        return dataID;
    }


    public DataRow setDataID(final Long dataID)
    {
        this.dataID = dataID;
        return this;
    }


    public Long getDictionaryID()
    {
        return dictionaryID;
    }


    public DataRow setDictionaryID(final Long dictionaryID)
    {
        this.dictionaryID = dictionaryID;
        return this;
    }


    public Row getRow()
    {
        return new Row().setID(rowID).load();
    }


    public Long getRowID()
    {
        return rowID;
    }


    public DataRow setRowID(final Long rowID)
    {
        this.rowID = rowID;
        return this;
    }


    /**
     * If the row has a data type field set. Only one should be set for the row. Return it!
     * @returns null if no data type field was found in this record otherwise the id.
     **/
    public Long getDataTypeID()
    {
        final var dataType = getDataFields().stream().filter(dataField -> dataField.getDataType().type() == DataType.Type.TYPE)
                .findFirst().orElse(null);
        if (dataType == null)
        {
            return null;
        }
        return dataType.getID();
    }


    public Long getLineIndex()
    {
        return lineIndex;
    }


    public DataRow setLineIndex(final Long lineIndex)
    {
        this.lineIndex = lineIndex;
        return this;
    }


    public String getLine()
    {
        return line;
    }


    public DataRow setLine(final String line)
    {
        this.line = line;
        return this;
    }


    public Boolean getSkipRow()
    {
        return skipRow;
    }


    public DataRow setSkipRow(final Boolean skipRow)
    {
        this.skipRow = skipRow;
        return this;
    }


    private ArrayList<DataField> dataFields = null;


    // FIXME Embedded list...
    public ArrayList<DataField> getDataFields()
    {
        if (getID() == null)
        {
            throw new RequiredPropertyException("id");
        }
        if (dataFields == null)
        {
            dataFields = new ArrayList<>();
            try (final ResultSet rs = Store
                    .selectList("SELECT data_field.* FROM data_field, field WHERE field.id = data_field.field_id AND data_field.data_row_id = ? ORDER BY field.position",
                                getID()))
            {
                while (rs.next())
                {
                    final DataField dataField = new DataField();
                    Store.POPULATE(dataField, rs);
                    dataFields.add(dataField);
                }
            }
            catch (final SQLException e)
            {
                LOG.error(e.getLocalizedMessage(), e);
            }
        }
        return dataFields;
    }


    public DataRow clearFields()
    {
        dataFields = null;
        return this;
    }
}
