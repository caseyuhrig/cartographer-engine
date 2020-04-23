/**
 * FIXME (c) Copyright 2012, Columbia Dynamics, All Rights Reserved.
 * http://www.columbiadynamics.com
 * casey.uhrig@columbiadynamics.com
 */
package cartographer.engine;

import java.util.ArrayList;

import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;

import cartographer.engine.function.FunctionFactory;


public class Mapping extends AbstractStorable<Mapping>
{
    private static final long serialVersionUID = 1L;

    // private static Logger LOG = LogManager.getLogger(Mapping.class);
    private Long mapID = null;

    private Long sourceRowID = null;

    private Long sourceFieldID = null;

    private Long targetRowID = null;

    private Long targetFieldID = null;

    private Integer mappingOrder = null;

    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "mapping";
            META.add(this, "map_id", "mapID");
            META.add(this, "source_row_id", "sourceRowID");
            META.add(this, "source_field_id", "sourceFieldID");
            META.add(this, "target_row_id", "targetRowID");
            META.add(this, "target_field_id", "targetFieldID");
            META.add(this, "mapping_order", "mappingOrder");
        }
        return META;
    }


    @Override
    public void delete()
    {
        for (final MappingFunction function : getMappingFunctions())
        {
            function.delete();
        }
        super.delete();
    }


    public Mapping loadByMapAndSourceField(final Long mapID, final Long sourceFieldID)
    {
        setID(Store.selectID("SELECT id FROM mapping WHERE map_id = ? AND source_field_id = ?", mapID, sourceFieldID));
        if (getID() != null)
        {
            load();
        }
        return this;
    }


    public Mapping loadByLink()
    {
        setID(Store.selectID("SELECT id FROM mapping WHERE map_id = ? AND source_field_id = ? AND target_field_id = ?",
                             mapID,
                             sourceFieldID,
                             targetFieldID));
        if (getID() != null)
        {
            load();
        }
        return this;
    }


    /**
     * FIXME Needs caching for speed, but needs to be reloaded when a new mapping function is added.
     * Possibly list the array names in a static array for a bean that the child bean can trigger a
     * refresh on any bean using a list of the child beans.
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList<MappingFunction> getMappingFunctions()
    {
        return (ArrayList<MappingFunction>) Store
                .POPULATE_LIST(MappingFunction.class, "mapping_function", "mapping_id", getID(), "function_order");
    }


    public String parse(final String value)
    {
        String result = value;
        for (final MappingFunction function : getMappingFunctions())
        {
            final String identifier = function.getFunctionDefinition().getIdentifier();
            // Keep re-parsing the value through all the functions.
            result = FunctionFactory.DEFAULT().getFunction(identifier).execute(result, function.getFunctionValue());
        }
        return result;
    }


    public Map getMap()
    {
        return new Map().setID(mapID).load();
    }


    public Long getMapID()
    {
        return mapID;
    }


    public Mapping setMapID(final Long mapID)
    {
        this.mapID = mapID;
        return this;
    }


    public Row getSourceRow()
    {
        return new Row().setID(sourceRowID).load();
    }


    public Long getSourceRowID()
    {
        return sourceRowID;
    }


    public Mapping setSourceRowID(final Long sourceRowID)
    {
        this.sourceRowID = sourceRowID;
        return this;
    }


    public Field getSourceField()
    {
        return new Field().setID(sourceFieldID).load();
    }


    public Long getSourceFieldID()
    {
        return sourceFieldID;
    }


    public Mapping setSourceFieldID(final Long sourceFieldID)
    {
        this.sourceFieldID = sourceFieldID;
        return this;
    }


    public Row getTargetRow()
    {
        return new Row().setID(targetRowID).load();
    }


    public Long getTargetRowID()
    {
        return targetRowID;
    }


    public Field getTargetField()
    {
        return new Field().setID(targetFieldID).load();
    }


    public Mapping setTargetRowID(final Long targetRowID)
    {
        this.targetRowID = targetRowID;
        return this;
    }


    public Long getTargetFieldID()
    {
        return targetFieldID;
    }


    public Mapping setTargetFieldID(final Long targetFieldID)
    {
        this.targetFieldID = targetFieldID;
        return this;
    }


    public Integer getMappingOrder()
    {
        return mappingOrder;
    }


    public Mapping setMappingOrder(final Integer mappingOrder)
    {
        this.mappingOrder = mappingOrder;
        return this;
    }
}
