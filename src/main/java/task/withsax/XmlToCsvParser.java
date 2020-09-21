package task.withsax;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class XmlToCsvParser {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		long start = System.currentTimeMillis();
		
		String csvPath = "src/main/resources/airports.csv";
		String xmlPath = "src/main/resources/airports.xml";
		String firstElement = "Airport";
		String seperator = ",";
		
		XmlHandler handler = new XmlHandler(csvPath, firstElement, seperator);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		parser.parse(new File(xmlPath), handler);
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}
