package cartographer.engine;

import nuberplex.store.AbstractStorableType;
import nuberplex.store.StorableMetaData;


public class FunctionDefinition extends AbstractStorableType<FunctionDefinition>
{
    private static final long serialVersionUID = 1L;

    private String promptText = null;

    private static StorableMetaData META = null;


    @Override
    public StorableMetaData META()
    {
        if (META == null)
        {
            META = initMETA();
            META.storableName = "function_definition";
            META.add(this, "prompt_text", "promptText");
        }
        return META;
    }


    public String getPromptText()
    {
        return promptText;
    }


    public FunctionDefinition setPromptText(final String promptText)
    {
        this.promptText = promptText;
        return this;
    }
}
