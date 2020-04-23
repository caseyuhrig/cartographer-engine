package cartographer.engine;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class DictionaryImporter
{
    public static Dictionary importTabDelimited(final File file)
            throws FileNotFoundException,
                IOException,
                SAXException,
                ParserConfigurationException
    {
        System.out.println(String.format("Importing: %s", file.getPath()));
        try (var input = new FileInputStream(file))
        {
            final var dictionary = new Dictionary();
            dictionary.setCustomerID(UserProfile.CURRENT().getCustomerID());
            dictionary.setSkipLines(0L);
            dictionary.setCreateHeader(false);
            dictionary.setDynamic(false);
            dictionary.setXml(false);
            final var defaultRow = new Row();
            final var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            final var properties = document.getElementsByTagName("Properties");
            for (int n = 0; n < properties.getLength(); n++)
            {
                final var property = properties.item(n);
                if (property.getNodeType() == Node.ELEMENT_NODE)
                {
                    final var propertyElement = Element.class.cast(property);
                    final var dictionaryType = propertyElement.getElementsByTagName("DictionaryType").item(0) != null
                            ? propertyElement.getElementsByTagName("DictionaryType").item(0).getTextContent()
                            : "";
                    final var dictionaryName = propertyElement.getElementsByTagName("DictionaryName").item(0) != null
                            ? propertyElement.getElementsByTagName("DictionaryName").item(0).getTextContent()
                            : "";
                    final var label = !dictionaryName.isBlank() ? dictionaryName : file.getName();
                    System.out.println(String.format("Dictionary Type: %s", dictionaryType));
                    System.out.println(String.format("Dictionary Label: %s", label));
                    dictionary.setLabel(label);
                    dictionary.setColumnDelimiter("\t");
                    dictionary.save();
                    defaultRow.setDictionaryID(dictionary.getID());
                    defaultRow.setLabel("DEFAULT");
                    defaultRow.setMinOccurrence(0);
                    defaultRow.setMaxOccurrence(Integer.MAX_VALUE);
                    defaultRow.setRowOrder(1);
                    defaultRow.save();
                }
            }
            final var nList = document.getElementsByTagName("FieldDetail");
            for (int n = 0; n < nList.getLength(); n++)
            {
                final var nNode = nList.item(n);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    final var eElement = Element.class.cast(nNode);
                    final var format = eElement.getElementsByTagName("Format") != null
                            ? eElement.getElementsByTagName("Format").item(0).getTextContent()
                            : null;
                    final var fieldName = eElement.getElementsByTagName("FieldName").item(0).getTextContent();
                    final var columnPos = eElement.getElementsByTagName("ColumnPos") != null
                            && eElement.getElementsByTagName("ColumnPos").item(0) != null
                                    ? eElement.getElementsByTagName("ColumnPos").item(0).getTextContent()
                                    : null;
                    final var position = columnPos != null && !columnPos.isBlank() ? Integer.parseInt(columnPos) : null;
                    final var field = new Field();
                    System.out.println(String.format("  FieldName: %s", fieldName));
                    System.out.println(String.format("   Position: %s", position));
                    System.out.println(String.format("     Format: %s", format));
                    field.setRowID(defaultRow.getID());
                    field.setDataType(DataType.Type.STRING);
                    field.setLabel(fieldName);
                    field.setPosition(position);
                    field.setMinOccurrence(1);
                    field.setMaxOccurrence(1);
                    field.save();
                }
            }
            return dictionary;
        }
    }


    public static Dictionary importFixedLength(final File file)
            throws FileNotFoundException,
                IOException,
                SAXException,
                ParserConfigurationException
    {
        System.out.println(String.format("Importing: %s", file.getPath()));
        try (var input = new FileInputStream(file))
        {
            final var dictionary = new Dictionary();
            dictionary.setCustomerID(UserProfile.CURRENT().getCustomerID());
            dictionary.setSkipLines(0L);
            dictionary.setCreateHeader(false);
            dictionary.setDynamic(false);
            dictionary.setXml(false);
            final var defaultRow = new Row();
            final var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            final var properties = document.getElementsByTagName("Properties");
            for (int n = 0; n < properties.getLength(); n++)
            {
                final var property = properties.item(n);
                if (property.getNodeType() == Node.ELEMENT_NODE)
                {
                    final var propertyElement = Element.class.cast(property);
                    final var dictionaryType = propertyElement.getElementsByTagName("DictionaryType").item(0) != null
                            ? propertyElement.getElementsByTagName("DictionaryType").item(0).getTextContent()
                            : "";
                    final var dictionaryName = propertyElement.getElementsByTagName("DictionaryName").item(0) != null
                            ? propertyElement.getElementsByTagName("DictionaryName").item(0).getTextContent()
                            : "";
                    final var recordLength = propertyElement.getElementsByTagName("RecordLength").item(0) != null
                            ? propertyElement.getElementsByTagName("RecordLength").item(0).getTextContent()
                            : "";
                    System.out.println(String.format("Dictionary Type: %s", dictionaryType));
                    System.out.println(String.format("Dictionary Name: %s", dictionaryName));
                    System.out.println(String.format("  Record Length: %s", recordLength));
                    final var label = !dictionaryName.isBlank() ? dictionaryName : file.getName();
                    final var rowLength = !recordLength.isBlank() ? Long.parseLong(recordLength) : null;
                    dictionary.setLabel(label);
                    dictionary.setRowLength(rowLength);
                    dictionary.save();
                    defaultRow.setDictionaryID(dictionary.getID());
                    defaultRow.setLabel("DEFAULT");
                    defaultRow.setMinOccurrence(0);
                    defaultRow.setMaxOccurrence(Integer.MAX_VALUE);
                    defaultRow.setRowOrder(1);
                    defaultRow.save();
                }
            }
            final var nList = document.getElementsByTagName("FieldDetail");
            for (int n = 0; n < nList.getLength(); n++)
            {
                final var nNode = nList.item(n);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    final var eElement = Element.class.cast(nNode);
                    final var format = eElement.getElementsByTagName("Format") != null
                            ? eElement.getElementsByTagName("Format").item(0).getTextContent()
                            : null;
                    final var typeOcc = eElement.getElementsByTagName("TypeOcc") != null
                            ? eElement.getElementsByTagName("TypeOcc").item(0).getTextContent()
                            : null;
                    final var fieldName = eElement.getElementsByTagName("FieldName").item(0).getTextContent();
                    final var startPos = eElement.getElementsByTagName("StartPos") != null
                            && eElement.getElementsByTagName("StartPos").item(0) != null
                                    ? eElement.getElementsByTagName("StartPos").item(0).getTextContent()
                                    : null;
                    final var columnPos = eElement.getElementsByTagName("ColumnPos") != null
                            && eElement.getElementsByTagName("ColumnPos").item(0) != null
                                    ? eElement.getElementsByTagName("ColumnPos").item(0).getTextContent()
                                    : null;
                    final var fieldLength = eElement.getElementsByTagName("FieldLength") != null
                            ? eElement.getElementsByTagName("FieldLength").item(0).getTextContent()
                            : null;
                    System.out.println(String.format("     Format: %s", format));
                    System.out.println(String.format("    TypeOcc: %s", typeOcc));
                    System.out.println(String.format("  FieldName: %s", fieldName));
                    System.out.println(String.format("   StartPos: %s", startPos));
                    System.out.println(String.format("FieldLength: %s", fieldLength));
                    // Note that both ColumnPos and FieldPos should not exists, Cart v4 uses one
                    // value for both meanings.
                    final var columnPosition = columnPos != null && !columnPos.isBlank() ? Integer.parseInt(columnPos) : null;
                    final var position = startPos != null && !startPos.isBlank() ? Integer.parseInt(startPos) : columnPosition;
                    final var length = fieldLength != null && !fieldLength.isBlank() ? Long.parseLong(fieldLength) : null;
                    final var field = new Field();
                    field.setRowID(defaultRow.getID());
                    field.setDataType(DataType.Type.STRING);
                    field.setLabel(fieldName);
                    field.setPosition(position);
                    field.setLength(length);
                    field.setMinOccurrence(1);
                    field.setMaxOccurrence(1);
                    field.save();
                }
            }
            return dictionary;
        }
    }


    public static Dictionary importFixedLengthMultiRecordType(final File file)
            throws FileNotFoundException,
                IOException,
                SAXException,
                ParserConfigurationException
    {
        System.out.println(String.format("Importing: %s", file.getPath()));
        try (var input = new FileInputStream(file))
        {
            final var dictionary = new Dictionary();
            dictionary.setCustomerID(UserProfile.CURRENT().getCustomerID());
            dictionary.setSkipLines(0L);
            dictionary.setCreateHeader(false);
            dictionary.setDynamic(false);
            dictionary.setXml(false);
            // final var defaultRow = new Row();
            final var document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            final var properties = document.getElementsByTagName("Properties");
            for (int n = 0; n < properties.getLength(); n++)
            {
                final var property = properties.item(n);
                if (property.getNodeType() == Node.ELEMENT_NODE)
                {
                    final var propertyElement = Element.class.cast(property);
                    final var dictionaryType = propertyElement.getElementsByTagName("DictionaryType").item(0) != null
                            ? propertyElement.getElementsByTagName("DictionaryType").item(0).getTextContent()
                            : "";
                    final var dictionaryName = propertyElement.getElementsByTagName("DictionaryName").item(0) != null
                            ? propertyElement.getElementsByTagName("DictionaryName").item(0).getTextContent()
                            : "";
                    final var recordLength = propertyElement.getElementsByTagName("RecordLength").item(0) != null
                            ? propertyElement.getElementsByTagName("RecordLength").item(0).getTextContent()
                            : "";
                    System.out.println(String.format("Dictionary Type: %s", dictionaryType));
                    System.out.println(String.format("Dictionary Name: %s", dictionaryName));
                    System.out.println(String.format("  Record Length: %s", recordLength));
                    final var label = !dictionaryName.isBlank() ? dictionaryName : file.getName();
                    // final var rowLength = !recordLength.isBlank() ? Long.parseLong(recordLength)
                    // : null;
                    dictionary.setLabel(label);
                    // dictionary.setRowLength(rowLength);
                    dictionary.save();
                    // defaultRow.setDictionaryID(dictionary.getID());
                    // defaultRow.setLabel("DEFAULT");
                    // defaultRow.setMinOccurrence(0);
                    // defaultRow.setMaxOccurrence(Integer.MAX_VALUE);
                    // defaultRow.setRowOrder(1);
                    // defaultRow.save();
                }
            }
            final var nList = document.getElementsByTagName("FieldDetail");
            for (int n = 0; n < nList.getLength(); n++)
            {
                final var nNode = nList.item(n);
                if (nNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    final var eElement = Element.class.cast(nNode);
                    final var format = eElement.getElementsByTagName("Format") != null
                            ? eElement.getElementsByTagName("Format").item(0).getTextContent()
                            : null;
                    final var typeOcc = eElement.getElementsByTagName("TypeOcc") != null
                            ? eElement.getElementsByTagName("TypeOcc").item(0).getTextContent()
                            : null;
                    final var fieldName = eElement.getElementsByTagName("FieldName").item(0).getTextContent();
                    final var startPos = eElement.getElementsByTagName("StartPos") != null
                            && eElement.getElementsByTagName("StartPos").item(0) != null
                                    ? eElement.getElementsByTagName("StartPos").item(0).getTextContent()
                                    : null;
                    final var columnPos = eElement.getElementsByTagName("ColumnPos") != null
                            && eElement.getElementsByTagName("ColumnPos").item(0) != null
                                    ? eElement.getElementsByTagName("ColumnPos").item(0).getTextContent()
                                    : null;
                    final var fieldLength = eElement.getElementsByTagName("FieldLength") != null
                            ? eElement.getElementsByTagName("FieldLength").item(0).getTextContent()
                            : null;
                    System.out.println(String.format("     Format: %s", format));
                    System.out.println(String.format("    TypeOcc: %s", typeOcc));
                    System.out.println(String.format("  FieldName: %s", fieldName));
                    System.out.println(String.format("   StartPos: %s", startPos));
                    System.out.println(String.format("FieldLength: %s", fieldLength));
                    final var row = new Row();
                    row.setDictionaryID(dictionary.getID());
                    row.setLabel(typeOcc);
                    // try to load the row is one exists, otherwise this will be a new row/type and
                    // we'll be creating it.
                    if (row.load(dictionary.getID(), typeOcc).exists() == false)
                    {
                        row.setDictionaryID(dictionary.getID());
                        row.setLabel(typeOcc);
                        row.setMinOccurrence(0);
                        row.setMaxOccurrence(Integer.MAX_VALUE);
                        row.setRowOrder(Row.NEXT_ROW);
                        row.save();
                    }
                    // Note that both ColumnPos and FieldPos should not exists, Cart v4 uses one
                    // value for both meanings.
                    final var columnPosition = columnPos != null && !columnPos.isBlank() ? Integer.parseInt(columnPos) : null;
                    final var position = startPos != null && !startPos.isBlank() ? Integer.parseInt(startPos) : columnPosition;
                    final var length = fieldLength != null && !fieldLength.isBlank() ? Long.parseLong(fieldLength) : null;
                    final var field = new Field();
                    field.setRowID(row.getID());
                    field.setDataType(DataType.Type.STRING);
                    field.setLabel(fieldName);
                    field.setPosition(position);
                    field.setLength(length);
                    field.setMinOccurrence(1);
                    field.setMaxOccurrence(1);
                    field.save();
                }
            }
            return dictionary;
        }
    }
}
