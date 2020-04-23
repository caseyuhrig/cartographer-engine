package cartographer.engine;

import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;
import nuberplex.store.TimestampUtils;


public class Conversion extends AbstractStorable<Conversion>
{
    private static final long serialVersionUID = 1L;

    protected static Logger LOG = LogManager.getLogger(Conversion.class);

    private String label = null;

    private Long mapID = null;

    private Timestamp startTime = null;

    private Timestamp endTime = null;

    private Long sourceDataID = null;

    private Long sourceDictionaryID = null;

    private String sourceDataPath = null;

    private Long sourceDataSize = null;

    private Long targetDictionaryID = null;

    private Long targetDataID = null;

    private String targetDataPath = null;

    private Long targetDataSize = null;

    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "conversion";
            META.add(this, "map_id", "mapID");
            META.add(this, "label", "label");
            META.add(this, "start_time", "startTime");
            META.add(this, "end_time", "endTime");
            // source
            META.add(this, "source_dictionary_id", "sourceDictionaryID");
            META.add(this, "source_data_id", "sourceDataID");
            META.add(this, "source_data_path", "sourceDataPath");
            META.add(this, "source_data_size", "sourceDataSize");
            // target
            META.add(this, "target_dictionary_id", "targetDictionaryID");
            META.add(this, "target_data_id", "targetDataID");
            META.add(this, "target_data_path", "targetDataPath");
            META.add(this, "target_data_size", "targetDataSize");
        }
        return META;
    }


    @Override
    public void beforeInsert()
    {
        setStartTime(TimestampUtils.NOW());
        setStartTime(TimestampUtils.NOW());
        final Data sourceData = new Data();
        sourceData.setDictionaryID(sourceDictionaryID);
        sourceData.setDataPath(sourceDataPath);
        sourceData.setDataSize(0L);
        sourceData.save();
        sourceDataID = sourceData.getID();
        final Data targetData = new Data();
        targetData.setDictionaryID(targetDictionaryID);
        targetData.setDataPath(targetDataPath);
        targetData.setDataSize(0L);
        targetData.save();
        targetDataID = targetData.getID();
    }


    @Override
    public void beforeUpdate()
    {
        setEndTime(TimestampUtils.NOW());
    }


    @Override
    public void delete()
    {
        getSourceData().delete();
        getTargetData().delete();
        super.delete();
    }


    public Map getMap()
    {
        if (mapID == null)
        {
            return null;
        }
        return new Map().setID(mapID).load();
    }


    public Long getMapID()
    {
        return mapID;
    }


    public Conversion setMapID(final Long mapID)
    {
        this.mapID = mapID;
        return this;
    }


    public String getLabel()
    {
        return label;
    }


    public Conversion setLabel(final String label)
    {
        this.label = label;
        return this;
    }


    public Timestamp getStartTime()
    {
        return startTime;
    }


    public Conversion setStartTime(final Timestamp startTime)
    {
        this.startTime = startTime;
        return this;
    }


    public Timestamp getEndTime()
    {
        return endTime;
    }


    public Conversion setEndTime(final Timestamp endTime)
    {
        this.endTime = endTime;
        return this;
    }


    public Dictionary getSourceDictionary()
    {
        if (sourceDictionaryID == null)
        {
            return null;
        }
        return new Dictionary().setID(sourceDictionaryID).load();
    }


    public Long getSourceDictionaryID()
    {
        return sourceDictionaryID;
    }


    public Conversion setSourceDictionaryID(final Long sourceDictionaryID)
    {
        this.sourceDictionaryID = sourceDictionaryID;
        return this;
    }


    public Data getSourceData()
    {
        if (sourceDataID == null)
        {
            return null;
        }
        return new Data().setID(sourceDataID).load();
    }


    public Long getSourceDataID()
    {
        return sourceDataID;
    }


    public Conversion setSourceDataID(final Long sourceDataID)
    {
        this.sourceDataID = sourceDataID;
        return this;
    }


    public String getSourceDataPath()
    {
        return sourceDataPath;
    }


    public Conversion setSourceDataPath(final String sourceDataPath)
    {
        this.sourceDataPath = sourceDataPath;
        return this;
    }


    public Long getSourceDataSize()
    {
        return sourceDataSize;
    }


    public Conversion setSourceDataSize(final Long sourceDataSize)
    {
        this.sourceDataSize = sourceDataSize;
        return this;
    }


    public Dictionary getTargetDictionary()
    {
        if (targetDictionaryID == null)
        {
            return null;
        }
        return new Dictionary().setID(targetDictionaryID).load();
    }


    public Long getTargetDictionaryID()
    {
        return targetDictionaryID;
    }


    public Conversion setTargetDictionaryID(final Long targetDictionaryID)
    {
        this.targetDictionaryID = targetDictionaryID;
        return this;
    }


    public Data getTargetData()
    {
        if (targetDataID == null)
        {
            return null;
        }
        return new Data().setID(targetDataID).load();
    }


    public Long getTargetDataID()
    {
        return targetDataID;
    }


    public Conversion setTargetDataID(final Long targetDataID)
    {
        this.targetDataID = targetDataID;
        return this;
    }


    public String getTargetDataPath()
    {
        return targetDataPath;
    }


    public Conversion setTargetDataPath(final String targetDataPath)
    {
        this.targetDataPath = targetDataPath;
        return this;
    }


    public Long getTargetDataSize()
    {
        return targetDataSize;
    }


    public Conversion setTargetDataSize(final Long targetDataSize)
    {
        this.targetDataSize = targetDataSize;
        return this;
    }
}
