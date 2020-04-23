package cartographer.engine;

import nuberplex.common.io.Base64InputStream;
import nuberplex.common.io.Base64OutputStream;
import nuberplex.common.lang.DateTime;
import nuberplex.common.lang.exception.PatternMatchingException;
import nuberplex.common.lang.exception.RequiredArgumentException;
import nuberplex.common.lang.exception.UnexpectedStateException;
import nuberplex.common.util.StringUtils;
import nuberplex.common.util.validator.Validator;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class License
{
    public static void main(final String... strings)
            throws IOException
    {
        final String key1 = License.ENCODE("ABC", DateTime.valueOf("20190101"));
        final License license = License.DECODE(key1);
        final String key2 = License.ENCODE(license.getCustomerID(), license.getExpirationDate());
        System.out.format("L: [%s]", license.toString()).println();
        System.out.format("K1:[%s]", key1).println();
        System.out.format("K2:[%s]", key2).println();
        if (key1.contentEquals(key2) == true)
        {
            System.out.println("GOOD: KEYS MATCH!");
        }
        else
        {
            System.out.println("BAD: KEY MISMATCH ;(");
        }
    }

    private static final Pattern PATTERN = Pattern.compile("^([A-Z_]+)[-]([0-9]{8})$");

    private String customerID = null;

    private DateTime expirationDate = null;

    private String key = null;

    public License()
    {
        super();
    }


    private static License DECODE(final String key)
            throws IOException
    {
        if (StringUtils.IS_EMPTY(key) == true)
        {
            throw new RequiredArgumentException("valueBase64");
        }
        // Decode the base64 data into human readable text.
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(new GZIPInputStream(new Base64InputStream(new ByteArrayInputStream(key.getBytes())))));
        String line = null;
        String valueDelimited = null;
        while ((line = reader.readLine()) != null)
        {
            valueDelimited = CLEAN(line);
        }
        reader.close();
        // Now parse the human readable text and populate a bean.
        if (Validator.isEmpty(valueDelimited) == true)
        {
            throw new UnexpectedStateException("Unable to process base64 data, an empty string resulted.");
        }
        final Matcher matcher = PATTERN.matcher(valueDelimited);
        if (matcher.matches() == true)
        {
            final License license = new License();
            license.setCustomerID(matcher.group(1));
            license.setExpirationDate(DateTime.valueOf(matcher.group(2)));
            license.setKey(key);
            return license;
        }
        else
        {
            throw new PatternMatchingException(
                    "The delimited license string did not match the REGEX pattern specified for decoding.");
        }
    }


    private static final String ENCODE(final String customerID, final DateTime expirationDate)
            throws IOException
    {
        if (StringUtils.IS_EMPTY(customerID) == true)
        {
            throw new RequiredArgumentException("customerID");
        }
        if (expirationDate == null)
        {
            throw new RequiredArgumentException("expirationDate");
        }
        // Create a human readable, machine parsable text string to perform some encoding magic on
        // and then base64 encoded.
        final StringBuilder sb = new StringBuilder();
        sb.append(customerID);
        sb.append("-");
        sb.append(expirationDate.format("yyyyMMdd"));
        final String value = sb.toString();
        // encode the string, we need some more magic in here to make it harder to decode the
        // license.
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final OutputStream output = new GZIPOutputStream(new Base64OutputStream(bytes));
        output.write(value.getBytes());
        output.flush();
        output.close();
        final String result = CLEAN(bytes.toString());
        if (Validator.isEmpty(result) == true)
        {
            if (result == null)
            {
                throw new UnexpectedStateException("The encoded license string is null.");
            }
            throw new UnexpectedStateException("The encoded license string is empty string.");
        }
        return result;
    }


    /**
     * Removes '\r', '\n', and ' ' spaces from anywhere in the string.
     * @param value The value to clean.
     * @return The cleaned value or null if the value supplied was null.
     */
    private static String CLEAN(final String value)
    {
        if (value == null)
        {
            return null;
        }
        return value.trim().replaceAll("[\r\n ]+", "");
    }


    @Override
    public String toString()
    {
        return String.format("%s-%s", customerID, expirationDate.format("yyyyMMdd"));
    }


    public String getCustomerID()
    {
        return customerID;
    }


    public void setCustomerID(final String customerID)
    {
        this.customerID = customerID;
    }


    public DateTime getExpirationDate()
    {
        return expirationDate;
    }


    public void setExpirationDate(final DateTime expirationDate)
    {
        this.expirationDate = expirationDate;
    }


    public String getKey()
    {
        return key;
    }


    public void setKey(final String key)
    {
        this.key = key;
    }
}
