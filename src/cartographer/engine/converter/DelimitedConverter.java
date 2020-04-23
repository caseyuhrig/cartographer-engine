package cartographer.engine.converter;

import cartographer.engine.Conversion;
import cartographer.engine.DataField;
import cartographer.engine.DataRow;
import cartographer.engine.format.TypeFormatter;
import cartographer.engine.io.ProgressBar;

import nuberplex.store.Store;

import java.util.HashMap;


public class DelimitedConverter extends AbstractConverter
{
    public DelimitedConverter(final Conversion conversion)
    {
        super(conversion);
    }


    @Override
    public Converter convertData(final ProgressBar progressBar)
            throws Exception
    {
        final var sourceDictionary = getConversion().getSourceDictionary();
        final var sourceData = getConversion().getSourceData();
        final var map = getConversion().getMap();
        final var targetDictionary = getConversion().getTargetDictionary();
        final var targetData = getConversion().getTargetData();
        Long lineIndex = 1L;
        if (progressBar != null)
        {
            progressBar.updateMaximum(sourceData.getDataRows().size());
        }
        // final Row targetRow = null;
        for (final var sourceDataRow : sourceData.getDataRows())
        {
            if (progressBar != null)
            {
                progressBar.updatePosition(lineIndex.intValue());
            }
            if (sourceDataRow.getSkipRow() == false && lineIndex > sourceDictionary.getSkipLines())
            {
                final var targetRow = targetDictionary.firstRow();
                // Initialize the target hash for scripting.
                final var targetHash = new HashMap<String, String>();
                final var targetHashIDs = new HashMap<String, Long>();
                targetRow.getFields().forEach(field -> {
                    targetHash.put(field.getLabel(), "");
                    targetHashIDs.put(field.getLabel(), -1L);
                });
                // Find the targetDataRow for our field.
                final var targetDataRow = new DataRow()
                        .loadBy_Data_Dictionary_Row(targetData.getID(), targetData.getDictionaryID(), targetRow.getID(), lineIndex);
                // Create the targetDataRow if one does not exist.
                if (targetDataRow.isNew() == true)
                {
                    targetDataRow.setDataID(targetData.getID());
                    targetDataRow.setDictionaryID(targetRow.getDictionaryID());
                    targetDataRow.setRowID(targetRow.getID());
                    targetDataRow.setSkipRow(false);
                    targetDataRow.setLineIndex(lineIndex);
                    targetDataRow.save();
                }
                for (final var targetField : targetRow.getFields())
                {
                    // Find the targetDataRow for our field.
                    // final DataRow targetDataRow = new
                    // DataRow().loadBy_Data_Dictionary_Row(targetData.getID(),
                    // targetData.getDictionaryID(),
                    // targetRow.getID(),
                    // lineIndex);
                    // Create the targetDataRow if one does not exist.
                    // if (targetDataRow.isNew() == true)
                    // {
                    // targetDataRow.setDataID(targetData.getID());
                    // targetDataRow.setDictionaryID(targetRow.getDictionaryID());
                    // targetDataRow.setRowID(targetRow.getID());
                    // targetDataRow.setSkipRow(false);
                    // targetDataRow.setLineIndex(lineIndex);
                    // targetDataRow.save();
                    // }
                    // Get all the mappings that map to the target field.
                    final var mappings = map.findSourceMappings(targetField.getID());
                    if (mappings.size() > 0)
                    {
                        // Loop through the mapping building up any source field data that needs to
                        // be combined to create our target data fields data.
                        var value = "";
                        for (final var mapping : mappings)
                        {
                            final var sourceDataField = new DataField().load(sourceDataRow.getID(), mapping.getSourceFieldID());
                            value += mapping.parse(sourceDataField.getValue());
                        }
                        // Now format the resulting combined source fields for the targetDataField.
                        final var formattedValue = TypeFormatter.INSTANCE
                                .format(targetField.getDataType(), targetField.getDataFormat(), value);
                        // Save the completed targetDataField.
                        final var targetDataField = new DataField();
                        targetDataField.setDictionaryID(targetDictionary.getID());
                        targetDataField.setDataTypeID(targetField.getDataTypeID());
                        targetDataField.setFieldID(targetField.getID());
                        targetDataField.setDataRowID(targetDataRow.getID());
                        targetDataField.setOriginalValue(value);
                        targetDataField.setValue(formattedValue);
                        targetDataField.save();
                        // target hash
                        targetHash.put(targetField.getLabel(), formattedValue);
                        targetHashIDs.put(targetField.getLabel(), targetDataField.getID());
                    }
                    else
                    {
                        // If no mapping exists, create a blank targetDataField.
                        final var targetDataField = new DataField();
                        targetDataField.setDictionaryID(targetDictionary.getID());
                        targetDataField.setDataTypeID(targetField.getDataTypeID());
                        targetDataField.setFieldID(targetField.getID());
                        targetDataField.setDataRowID(targetDataRow.getID());
                        targetDataField.setOriginalValue("");
                        targetDataField.setValue("");
                        targetDataField.save();
                        // target hash
                        targetHash.put(targetField.getLabel(), "");
                        targetHashIDs.put(targetField.getLabel(), targetDataField.getID());
                    }
                }
                // HACK TODO FIXME FUNKY Add back in the JavaScript logic engine.
                if (map.getScript() != null && !map.getScript().isBlank())
                {
                    // final ScriptEngineManager mgr = new ScriptEngineManager();
                    // final ScriptEngine engine = mgr.getEngineByName("javascript");
                    // final Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
                    switch (map.getScript()) {
                        case "CCCS.capella" -> {
                            if (targetHash.get("Dummy1 (A)").isBlank())
                            {
                                targetHash.put("Name2 (A)", "");
                                new DataField().setID(targetHashIDs.get("Name2 (A)")).load().setValue("").save();
                            }
                            if (targetHash.get("Dummy2 (A)").isBlank())
                            {
                                targetHash.put("Name2 (A)", " ");
                                new DataField().setID(targetHashIDs.get("Name2 (A)")).load().setValue("").save();
                            }
                            if (targetHash.get("Name1 (A)").isBlank())
                            {
                                targetHash.put("Name1 (A)", targetHash.get("Dummy2 (A)"));
                                new DataField().setID(targetHashIDs.get("Name1 (A)")).load().setValue(targetHash.get("Dummy2 (A)"))
                                        .save();
                            }
                        }
                        case "CCCS.webar" -> {
                            if (targetHash.get("Name1 (A)").isBlank())
                            {
                                targetHash.put("Name1 (A)", targetHash.get("Dummy1 (A)"));
                                new DataField().setID(targetHashIDs.get("Name1 (A)")).load().setValue(targetHash.get("Dummy1 (A)"))
                                        .save();
                            }
                            if (targetHash.get("Name2 (A)").isBlank())
                            {
                                targetHash.put("Name2 (A)", targetHash.get("Dummy2 (A)"));
                                new DataField().setID(targetHashIDs.get("Name2 (A)")).load().setValue(targetHash.get("Dummy2 (A)"))
                                        .save();
                            }
                        }
                        default -> {
                            // do nothing
                        }
                    }
                }
            }
            Store.COMMIT();
            lineIndex++;
        }
        return this;
    }
}
