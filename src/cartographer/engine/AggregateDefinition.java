package cartographer.engine;

import nuberplex.store.AbstractStorableType;
import nuberplex.store.StorableMetaData;


public class AggregateDefinition extends AbstractStorableType<AggregateDefinition>
{
    private static StorableMetaData META = null;


    public enum Identifier
    {
        GROUP_BY, SUM, AVG, COUNT, MIN, MAX, COALESCE, EMPTY
    }


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "aggregate_definition";
        }
        return META;
    }
}
