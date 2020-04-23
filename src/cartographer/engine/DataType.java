package cartographer.engine;

import nuberplex.store.AbstractStorableType;
import nuberplex.store.StorableMetaData;


public class DataType extends AbstractStorableType<DataType>
{
    private static final long serialVersionUID = 1L;


    public enum Type
    {
        STRING, CURRENCY, LITERAL, DATE_TIME, PHONE, TYPE, KEY
    }


    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "data_type";
        }
        return META;
    }


    public Type type()
    {
        return Type.valueOf(getIdentifier());
    }
}
