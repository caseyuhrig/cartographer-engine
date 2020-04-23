package cartographer.engine;

import nuberplex.store.AbstractStorableType;
import nuberplex.store.StorableMetaData;


public class RuleDefinition extends AbstractStorableType<RuleDefinition>
{
    private static final long serialVersionUID = 1L;

    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "rule_definition";
        }
        return META;
    }
}
