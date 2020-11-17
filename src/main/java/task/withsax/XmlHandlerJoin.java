package task.withsax;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandlerJoin extends DefaultHandler {

	private String csvPath;
	private String firstElement;
	private String seperator;

	private FileWriter csvSource = null;
	
	private StringJoiner result = null;

	public XmlHandlerJoin(String csvPath, String firstElement, String seperator) {
		this.csvPath = csvPath;
		this.firstElement = firstElement;
		this.seperator = seperator;
	}

	@Override
	public void startDocument() throws SAXException {
		try {
			csvSource = new FileWriter(csvPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void endDocument() throws SAXException {
		try {
			csvSource.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str = new String(ch, start, length);
		if (!str.isBlank()) {
			result.add(str);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.contains(firstElement))
			result = new StringJoiner(seperator, "", "\n");
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equals(firstElement)) {
			if (result.length() > 0) {
				writeStringToCsv(result);
			}
		}
	}

	private void writeStringToCsv(StringJoiner line) {
		try {
			csvSource.write(line.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}