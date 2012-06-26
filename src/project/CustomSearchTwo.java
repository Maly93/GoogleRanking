package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

public class CustomSearchTwo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Customsearch.Builder build;
		
		build = new Customsearch.Builder(new NetHttpTransport(), new JacksonFactory(), 
				new BasicAuthentication("maly3026@gmail.com", "Psp030793"));
		
		boolean found = false;
		int i = 0;

		String apiKey = "AIzaSyB_HVYobrNwA_h8pDSxlfZj-2iH4IfxfKc";
		String searchKey = "011746582223404494513:1sy1av_ze2s";
		String indexStr = new String("washingtonpost.com");
		String topURL = "";

		List<Result> items = null;
		ArrayList<String> searches = new ArrayList<String>();

		int count = 0;
		StringBuilder builder = new StringBuilder();
		builder.append("headline, URL, rank, top rank URL\n");


		@SuppressWarnings("deprecation")
		Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory());

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
			if (headlines.item(i).getAttributes().item(0).getNodeValue().toString().equals("headline"))
			{
				searches.add(headlines.item(i).getTextContent());
				count++;
			}

			i++;
		}

		for (int j = 0; j < searches.size() - 1; j++)
		{
			found = false;

			try {


				com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(searches.get(j));

				list.setKey(apiKey);
				list.setCx(searchKey);
				Search results = list.execute();
				items = results.getItems();

				topURL = items.get(0).getFormattedUrl();

				for( int k = 0; k < items.size(); k++)
				{

					if (!found && items.get(k).getFormattedUrl().contains(indexStr))
					{
						found = true;
						builder.append(items.get(k).getTitle() + ", " + items.get(k).getFormattedUrl() + ", " 
								+ (k + 1) + ", ");
					}
				}

				if (!found)
				{
					builder.append(searches.get(j) + ", Not Found, Not Found, ");
				}

				builder.append(topURL);
				builder.append("\n");


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		System.out.println(builder.toString());


		File xmlFile = new File("C:\\Documents and Settings\\alym\\My Documents\\googleRankings.txt");

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
