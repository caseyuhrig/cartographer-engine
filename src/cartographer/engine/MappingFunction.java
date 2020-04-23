package cartographer.engine;

import nuberplex.common.lang.exception.RequiredPropertyException;
import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;


public class MappingFunction extends AbstractStorable<MappingFunction>
{
    private static final long serialVersionUID = 1L;
    // private static Logger LOG = LogManager.getLogger(MappingFunction.class);

    private Long mappingID = null;

    private Long functionDefinitionID = null;

    private String functionValue = null;

    private Integer functionOrder = null;

    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "mapping_function";
            META.add(this, "mapping_id", "mappingID");
            META.add(this, "function_definition_id", "functionDefinitionID");
            META.add(this, "function_value", "functionValue");
            META.add(this, "function_order", "functionOrder");
        }
        return META;
    }


    public Long getMappingID()
    {
        return mappingID;
    }


    public void setMappingID(final Long mappingID)
    {
        this.mappingID = mappingID;
    }


    public Long getFunctionDefinitionID()
    {
        return functionDefinitionID;
    }


    public void setFunctionDefinitionID(final Long functionDefinitionID)
    {
        this.functionDefinitionID = functionDefinitionID;
    }


    public FunctionDefinition getFunctionDefinition()
    {
        if (getFunctionDefinitionID() == null)
        {
            throw new RequiredPropertyException("functionDefinitionID");
        }
        return new FunctionDefinition().setID(getFunctionDefinitionID()).load();
    }


    public String getFunctionValue()
    {
        return functionValue;
    }


    public void setFunctionValue(final String functionValue)
    {
        this.functionValue = functionValue;
    }


    public Integer getFunctionOrder()
    {
        return functionOrder;
    }


    public void setFunctionOrder(final Integer functionOrder)
    {
        this.functionOrder = functionOrder;
    }
}
