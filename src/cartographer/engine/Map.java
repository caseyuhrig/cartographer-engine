/**
 * (c) 2018, Casey Uhrig
 */
package cartographer.engine;

import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Casey Uhrig
 */
public class Map extends AbstractStorable<Map>
{
    // private static final Logger LOG = LogManager.getLogger(Map.class);
    private String label = null;
    private Long customerID = null;
    private Long sourceDictionaryID = null;
    private Long targetDictionaryID = null;
    private String scriptMimeType = null;
    private String script = null;
    private ArrayList<Mapping> mappings = null;
    private HashMap<Long, ArrayList<Mapping>> sourceMappingCache = null;
    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "map";
            META.add(this, "customer_id", "customerID"); // required
            META.add(this, "label", "label"); // required
            META.add(this, "script_mime_type", "scriptMimeType");
            META.add(this, "script", "script");
            META.add(this, "source_dictionary_id", "sourceDictionaryID"); // required
            META.add(this, "target_dictionary_id", "targetDictionaryID"); // required
        }
        return META;
    }


    public String getLabel()
    {
        return label;
    }


    public Map setLabel(final String label)
    {
        this.label = label;
        return this;
    }


    public Long getCustomerID()
    {
        return customerID;
    }


    public Map setCustomerID(final Long customerID)
    {
        this.customerID = customerID;
        return this;
    }


    public Long getSourceDictionaryID()
    {
        return sourceDictionaryID;
    }


    public Map setSourceDictionaryID(final Long sourceDictionaryID)
    {
        this.sourceDictionaryID = sourceDictionaryID;
        return this;
    }


    public Long getTargetDictionaryID()
    {
        return targetDictionaryID;
    }


    public Map setTargetDictionaryID(final Long targetDictionaryID)
    {
        this.targetDictionaryID = targetDictionaryID;
        return this;
    }


    public String getScriptMimeType()
    {
        return scriptMimeType;
    }


    public Map setScriptMimeType(final String scriptMimeType)
    {
        this.scriptMimeType = scriptMimeType;
        return this;
    }


    public String getScript()
    {
        return script;
    }


    public Map setScript(final String script)
    {
        this.script = script;
        return this;
    }


    @SuppressWarnings("unchecked")
    public ArrayList<Mapping> findSourceMappings(final Long targetFieldID)
    {
        if (targetFieldID == null)
        {
            throw new RequiredParameterException("targetFieldID");
        }
        if (sourceMappingCache == null)
        {
            sourceMappingCache = new HashMap<>();
        }
        final ArrayList<Mapping> mappings = sourceMappingCache.get(targetFieldID);
        if (mappings == null)
        {
            if (LOG.isDebugEnabled() == true)
            {
                LOG.debug(String.format("TFID: %d", targetFieldID));
            }
            sourceMappingCache.put(targetFieldID,
                                   (ArrayList<Mapping>) Store.POPULATE(Mapping.class,
                                                                       "*",
                                                                       "mapping",
                                                                       "map_id = ? AND target_field_id = ?",
                                                                       "mapping_order",
                                                                       getID(),
                                                                       targetFieldID));
            return sourceMappingCache.get(targetFieldID);
        }
        return mappings;
    }


    @SuppressWarnings("unchecked")
    public ArrayList<Mapping> getMappingByTargetFieldID(final Long targetFieldID)
    {
        return (ArrayList<Mapping>) Store
                .POPULATE(Mapping.class, "*", "mapping", "target_field_id = ?", "mapping_order", targetFieldID);
    }


    @SuppressWarnings("unchecked")
    public ArrayList<Mapping> getMappingsByRows(final Long sourceRowID, final Long targetRowID)
    {
        if (mappings == null)
        {
            mappings = (ArrayList<Mapping>) Store.POPULATE(Mapping.class,
                                                           "*",
                                                           "mapping",
                                                           "map_id = ? AND source_row_id = ? AND target_row_id = ?",
                                                           "mapping_order",
                                                           getID(),
                                                           sourceRowID,
                                                           targetRowID);
        }
        return mappings;
    }


    @SuppressWarnings("unchecked")
    public ArrayList<Mapping> getMappings()
    {
        if (mappings == null)
        {
            mappings = (ArrayList<Mapping>) Store.POPULATE_LIST(Mapping.class, "mapping", "map_id", getID(), "mapping_order ASC");
        }
        return mappings;
    }


    @SuppressWarnings("unchecked")
    public ArrayList<Conversion> getConversions()
    {
        return (ArrayList<Conversion>) Store.POPULATE_LIST(Conversion.class, "conversion", "map_id", getID(), "create_date DESC");
    }


    @Override
    public void clear()
    {
        super.clear();
        mappings = null;
    }


    public void reset()
    {
        mappings = null;
    }


    @Override
    public String toString()
    {
        return label;
    }


    @Override
    public void delete()
    {
        for (final Conversion conversion : getConversions())
        {
            conversion.delete();
        }
        for (final Mapping mapping : getMappings())
        {
            mapping.delete();
        }
        super.delete();
    }
}
