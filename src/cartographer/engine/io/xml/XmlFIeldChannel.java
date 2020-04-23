package cartographer.engine.io.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;


public class XmlFIeldChannel
{
    public XmlFIeldChannel()
    {
        // TODO Auto-generated constructor stub
    }


    public static void main(final String args[])
            throws Exception
    {
        final File stylesheetFile = new File("C:/Users/casey/Documents/eclipse-workspace/cartographer-engine/style.xsl");
        final InputStream stylesheetInput = new FileInputStream(stylesheetFile);
        final File xmlFile = new File("C:/Users/casey/Documents/eclipse-workspace/cartographer-engine/data.xml");
        final InputStream xmlInput = new FileInputStream(xmlFile);
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        // final Document document = builder.parse(xmlFile);
        final Document document = builder.parse(xmlInput);
        // final StreamSource styleSource = new StreamSource(stylesheetFile);
        final StreamSource styleSource = new StreamSource(stylesheetInput);
        final Transformer transformer = TransformerFactory.newInstance().newTransformer(styleSource);
        final Source source = new DOMSource(document);
        final File outputFile = new File("C:/Users/casey/Desktop/output-from-xml.csv");
        if (outputFile.createNewFile() == false)
        {
            throw new IOException(String.format("Cannot create %", outputFile.getPath()));
        }
        // final Result outputTarget = new StreamResult();
        final OutputStream output = new FileOutputStream(outputFile);
        final Result outputTarget = new StreamResult(output);
        transformer.transform(source, outputTarget);
    }
}
