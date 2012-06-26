package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class SampelEverything {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int i = 0;
		int count = 0;
		StringBuilder builder = new StringBuilder();
		builder.append("headline\n");
		
		Document doc = null;
		try
		{
			File file = new File("C:\\Documents and Settings\\alym\\My Documents\\results.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			doc.getDocumentElement().normalize();
			

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		Element element = doc.getDocumentElement();
		NodeList headlines = element.getElementsByTagName("str");
		
		while (count < 100)
		{
			if (headlines.item(i).getAttributes().item(0).getNodeValue().toString().equals("headline") 
					&& !builder.toString().contains(headlines.item(i).getTextContent()))
			{
				builder.append(headlines.item(i).getTextContent());
				builder.append("\n");
				count++;
			}
			
			i++;
		}
		
		System.out.println(builder.toString());
		
		File xmlFile = new File("C:\\Documents and Settings\\alym\\My Documents\\fileTest.txt");
		
		BufferedWriter writer = null;
		
		try {
			writer = new BufferedWriter(new FileWriter(xmlFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (writer != null)
		{
			try {
				writer.write(builder.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
