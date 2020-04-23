package cartographer.engine;

import cartographer.engine.converter.ConverterFactory;
import cartographer.engine.exporter.ExporterFactory;
import cartographer.engine.importer.ImporterFactory;
import cartographer.engine.io.ProgressBar;

import nuberplex.common.lang.DateTime;
import nuberplex.common.lang.exception.RequiredParameterException;
import nuberplex.store.Store;
import nuberplex.store.StoreException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Engine
{
    private static Logger LOG = LogManager.getLogger(Engine.class);

    private ProgressBar progressBar = null;

    private final ArrayList<EngineListener> listeners;

    public Engine()
    {
        super();
        listeners = new ArrayList<>();
    }


    /**
     * <pre>
     * Dynamic Dictionary: Columns are identified by label, not position.
     * Dynamic Field: Column label can have multiple values to identify the column.
     * Load records into a hash or List depending if the dictionary is dynamic or not.
     * </pre>
     *
     * @param sourceDictionary
     * @param sourceFile
     * @param map
     * @param targetDictionary
     * @param targetFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws StoreException
     */
    public Conversion convert(final Dictionary sourceDictionary,
                              final File sourceFile,
                              final Map map,
                              final Dictionary targetDictionary,
                              final File targetFile)
    {
        try
        {
            if (sourceDictionary == null)
            {
                throw new RequiredParameterException("sourceDictionary");
            }
            if (sourceFile == null)
            {
                throw new RequiredParameterException("sourceFile");
            }
            if (map == null)
            {
                throw new RequiredParameterException("map");
            }
            if (targetDictionary == null)
            {
                throw new RequiredParameterException("targetDictionary");
            }
            if (targetFile == null)
            {
                throw new RequiredParameterException("targetFile");
            }
            Store.COMMIT();
            final Conversion conversion = new Conversion();
            conversion.setLabel(sourceDictionary.getLabel() + " -> " + map.getLabel() + " -> " + targetDictionary.getLabel() + " "
                    + DateTime.now());
            conversion.setSourceDictionaryID(sourceDictionary.getID());
            conversion.setSourceDataPath(sourceFile.getPath());
            conversion.setSourceDataSize(sourceFile.length());
            conversion.setMapID(map.getID());
            conversion.setTargetDictionaryID(targetDictionary.getID());
            conversion.setTargetDataPath(targetFile.getPath());
            conversion.setTargetDataSize(0L);
            conversion.save();
            LOG.info("Importing Data");
            ImporterFactory.FIND(conversion).importData(progressBar);
            LOG.info("Converting Data");
            ConverterFactory.FIND(conversion).convertData(progressBar);
            LOG.info("Exporting Data");
            ExporterFactory.FIND(conversion).exportData(progressBar);
            conversion.setTargetDataSize(targetFile.length());
            conversion.save();
            Store.COMMIT();
            return conversion;
        }
        catch (final Throwable throwable)
        {
            LOG.error(throwable.getLocalizedMessage(), throwable);
            final EngineExceptionEvent event = new EngineExceptionEvent();
            event.setEngine(this);
            event.setThrowable(throwable);
            fireEngineExceptionListeners(event);
        }
        return null;
    }


    public void addListener(final EngineListener listener)
    {
        listeners.add(listener);
    }


    protected void fireEngineExceptionListeners(final EngineExceptionEvent event)
    {
        for (final EngineListener listener : listeners)
        {
            if (listener instanceof EngineExceptionListener)
            {
                listener.perform(event);
            }
        }
    }


    public Engine setProgressBar(final ProgressBar progressBar)
    {
        this.progressBar = progressBar;
        return this;
    }
}
