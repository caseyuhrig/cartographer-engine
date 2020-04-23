package cartographer.engine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nuberplex.store.AbstractStorable;
import nuberplex.store.StorableMetaData;


public class Rule extends AbstractStorable<Rule>
{
    private static final long serialVersionUID = 1L;

    protected static Logger LOG = LogManager.getLogger(Rule.class);

    private static StorableMetaData META = null;

    private Long fieldID = null;

    private Long ruleDefinitionID = null;

    private Long errorLevelID = null;

    private Integer ruleOrder = null;

    private String ruleValue = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "rule";
            META.add(this, "rule_definition_id", "ruleDefinitionID");
            META.add(this, "field_id", "fieldID");
            META.add(this, "error_level_id", "errorLevelID");
            META.add(this, "rule_order", "ruleOrder");
            META.add(this, "rule_value", "ruleValue");
        }
        return META;
    }


    public Long getFieldID()
    {
        return fieldID;
    }


    public Rule setFieldID(final Long fieldID)
    {
        this.fieldID = fieldID;
        return this;
    }


    public Long getRuleDefinitionID()
    {
        return ruleDefinitionID;
    }


    public Rule setRuleDefinitionID(final Long ruleDefinitionID)
    {
        this.ruleDefinitionID = ruleDefinitionID;
        return this;
    }


    public RuleDefinition getRuleDefinition()
    {
        return new RuleDefinition().setID(ruleDefinitionID).load();
    }


    public Long getErrorLevelID()
    {
        return errorLevelID;
    }


    public Rule setErrorLevelID(final Long errorLevelID)
    {
        this.errorLevelID = errorLevelID;
        return this;
    }


    public ErrorLevel getErrorLevel()
    {
        return new ErrorLevel().setID(getErrorLevelID()).load();
    }


    public Integer getRuleOrder()
    {
        return ruleOrder;
    }


    public Rule setRuleOrder(final Integer ruleOrder)
    {
        this.ruleOrder = ruleOrder;
        return this;
    }


    public String getRuleValue()
    {
        return ruleValue;
    }


    public Rule setRuleValue(final String ruleValue)
    {
        this.ruleValue = ruleValue;
        return this;
    }
}
