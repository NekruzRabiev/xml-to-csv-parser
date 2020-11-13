package task.withsax;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class XmlToCsvParser {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		
		String csvPath = "src/main/resources/airports.csv";
		String xmlPath = "src/main/resources/airports.xml";
		String firstElement = "Airport";
		String seperator = ",";
		
		XmlHandler handler = new XmlHandler(csvPath, firstElement, seperator);
		XmlHandlerJoin handlerJoin = new XmlHandlerJoin(csvPath, firstElement, seperator);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		SAXParser parser1 = factory.newSAXParser();
		
//		long start = System.currentTimeMillis();
//		parser.parse(new File(xmlPath), handler);
//		long end = System.currentTimeMillis();
//		System.out.println("String.join: " + (end - start));
		
		long start1 = System.currentTimeMillis();
		parser1.parse(new File(xmlPath), handlerJoin);
		long end1 = System.currentTimeMillis();
		System.out.println("StringJoin: " + (end1 - start1));

	}
}
