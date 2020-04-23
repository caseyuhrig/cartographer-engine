package cartographer.engine.converter;

import cartographer.engine.Conversion;


public class ConverterFactory
{
    public static Converter FIND(final Conversion conversion)
    {
        final var sourceDictionary = conversion.getSourceDictionary();
        if (sourceDictionary.getRows().size() == 1)
        {
            if (sourceDictionary.firstRow().hasAggregateDefinition() == true)
            {
                return new DelimitedRollupConverter(conversion);
            }
            else
            {
                return new DelimitedConverter(conversion);
            }
        }
        else if (sourceDictionary.getRows().size() > 1)
        {
            // FIXME TODO Check if ANY have aggregate definitions.
            return new MultiRecordTypeToSingleConverter(conversion);
        }
        else
        {
            throw new RuntimeException(
                    String.format("No rows have been defined for source dictionary: %s", sourceDictionary.getLabel()));
        }
    }
}
