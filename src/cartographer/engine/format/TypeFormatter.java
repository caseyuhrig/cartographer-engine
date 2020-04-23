package cartographer.engine.format;

import cartographer.engine.DataType;

import nuberplex.common.lang.exception.RequiredParameterException;

import java.text.ParseException;


public class TypeFormatter
{
    public static TypeFormatter INSTANCE = new TypeFormatter();


    public String format(final DataType dataType, final String format, final String value)
            throws ParseException
    {
        if (dataType == null)
        {
            throw new RequiredParameterException("dataType");
        }
        final DataType.Type type = dataType.type();
        switch (type) {
            case STRING -> {
                return StringFormatter.DEFAULT.format(format, value);
            }
            case CURRENCY -> {
                return CurrencyFormatter.DEFAULT.format(format, value);
            }
            case DATE_TIME -> {
                return DateTimeFormatter.DEFAULT.format(format, value);
            }
            case PHONE -> {
                return PhoneFormatter.DEFAULT.format(format, value);
            }
            case LITERAL -> {
                return format;
            }
            case TYPE -> {
                return value;
            }
            case KEY -> {
                return value;
            }
            default -> {
                throw new UnsupportedOperationException(String.format("Format for data type %s does not exist.", type));
            }
        }
    }
}
