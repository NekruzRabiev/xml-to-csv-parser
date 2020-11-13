package task.withsax;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Если все элементы с данными, то лучше использовать код из под комментариев. В
 * этом случае избавимся от HashSet-a
 **/

public class XmlHandler extends DefaultHandler {

	private String csvPath;
	private String firstElement;
	private String seperator;

	// Названия тегов у которых есть данные
	private HashSet<String> tags = null;

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