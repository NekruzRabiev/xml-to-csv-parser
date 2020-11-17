package task.withsax;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHandler extends DefaultHandler {

	private String csvPath;
	private String firstElement;
	private String seperator;

	private FileWriter csvSource = null;
	
	private List<String> list = new ArrayList<String>(15);

	public XmlHandler(String csvPath, String firstElement, String seperator) {
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
			list.add(str);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equals(firstElement)) {
			if (list.size() > 0) {
				String result = String.join(seperator, list);
				writeStringToCsv(result);
				list.clear();
			}
		}
	}

	private void writeStringToCsv(String word) {
		try {
			csvSource.write(word.toString() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}