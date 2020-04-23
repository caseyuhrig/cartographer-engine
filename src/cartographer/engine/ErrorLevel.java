package cartographer.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nuberplex.store.AbstractStorableType;
import nuberplex.store.StorableMetaData;
import nuberplex.store.Store;


public class ErrorLevel extends AbstractStorableType<ErrorLevel>
{
    private static final long serialVersionUID = 1L;

    protected static Logger LOG = LogManager.getLogger(ErrorLevel.class);

    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "error_level";
        }
        return META;
    }


    public static ErrorLevel IDENT(final String identifier)
    {
        return (ErrorLevel) Store.GET(ErrorLevel.class, "error_level", "identifier", identifier);
    }
}
