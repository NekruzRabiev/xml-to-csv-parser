package task.withsax;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Если все элементы с данными, то лучше использовать код из под комментариев. В
 * этом случае избавимся от HashSett-a
 **/

public class XmlHandler extends DefaultHandler {

	private String csvPath;
	private String firstElement;
	private String seperator;

	// Названия тегов у которых есть данные
	private HashSet<String> tags = null;

	private FileWriter csvSource = null;

	private String result = "";

	public XmlHandler(String csvPath, String firstElement, String seperator) {
		this.csvPath = csvPath;
		this.firstElement = firstElement;
		this.seperator = seperator;
	}

	@Override
	public void startDocument() throws SAXException {
		try {
			tags = new HashSet<String>(14);
			tags.add("Id");
			tags.add("Name");
			tags.add("City");
			tags.add("Country");
			tags.add("IATA");
			tags.add("ICAO");
			tags.add("DST");
			tags.add("Latitude");
			tags.add("Longitude");
			tags.add("Altitude");
			tags.add("Offset");
			tags.add("Timezone");
			tags.add("Type");
			tags.add("Source");

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

//	@Override
//	public void characters(char[] ch, int start, int length) throws SAXException {
//		String str = new String(ch, start, length);
//		if (!str.isBlank())
//			result += str + seperator;
//	}
//
//	@Override
//	public void endElement(String uri, String localName, String qName) throws SAXException {
//
//		if (qName.equals(firstElement)) {
//			try {
//				if (result.length() > 1) {
//					writeStringToCsv(result.substring(0, result.length() - 1));
//				}
//				result = "";
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str = new String(ch, start, length);
		if (!str.isBlank())
			result += str;
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (tags.contains(qName)) {
			result += seperator;
		}

		if (qName.equals(firstElement)) {
			try {
				if (result.length() > 0) {
					writeStringToCsv(result.substring(0, result.length() - 1));
				}
				result = "";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void writeStringToCsv(String word) throws IOException {
		csvSource.write(word + "\n");
	}
}
