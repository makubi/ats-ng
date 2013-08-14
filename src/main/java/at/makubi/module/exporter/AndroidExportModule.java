package at.makubi.module.exporter;

import at.makubi.entities.Entry;
import at.makubi.entities.Identifier;
import at.makubi.entities.Translation;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

@Component
public class AndroidExportModule implements ExportModule {

    @Override
    public File export(File file, Iterable<Entry> entryCollection) {
        final File outputFile;

        try {
            outputFile = File.createTempFile("ats.", ".xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            Node resources = doc.getFirstChild();


            NodeList childNodes = resources.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);

                if ("string".equals(item.getNodeName())) {
                    String linguaNumber = item.getAttributes().getNamedItem("lingua").getTextContent();
                    final String[] linguaIdentifier = linguaNumber.split("\\.");
                    final long attributeLinguaNumber = Long.parseLong(linguaIdentifier[0]);
                    final int attributeLinguaSubNumber = linguaIdentifier.length > 1 ? Integer.parseInt(linguaIdentifier[1]) : -1;

                    final String linguaNumberWithoutLeadingZeros = attributeLinguaNumber + "." + attributeLinguaSubNumber;

                    for (Entry entry : entryCollection) {
                        final Identifier identifier = entry.getIdentifier();
                        final long number = identifier.getNumber();
                        final int subNumber = identifier.getSubNumber();


                        final String linguaNumberString = subNumber > 0 ? number + "." + subNumber : "" + number;

                        if (linguaNumberString.matches(linguaNumberWithoutLeadingZeros)) {
                            for (Translation translation : entry.getTexts()) {
                                if ("de".matches(translation.getCountryCode())) {
                                    item.setTextContent(translation.getText());
                                }
                            }
                        }
                    }

                }

            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        return outputFile;
    }
}
