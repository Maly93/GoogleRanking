package project;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;


import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

public class CustomSearch {


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub



		boolean found = false;
		int index = 0;
		int pages = 0;
		int page = 0;

		String searchStr = "";
		String apiKey = "AIzaSyB_HVYobrNwA_h8pDSxlfZj-2iH4IfxfKc";
		String searchKey = "012661291032744890452:ikdi3gyy1rk";
		String indexStr = new String("washingtonpost.com");

		List<Result> items = null;
		Scanner scan = new Scanner(System.in);

		@SuppressWarnings("deprecation")
		Customsearch customsearch = new Customsearch(new NetHttpTransport(), new JacksonFactory());



		// User input
		System.out.println("Enter your search: ");
		searchStr = scan.nextLine();

		System.out.println("How many pages would you like to search? (Limit 10)");
		pages = scan.nextInt();


		scan.close();

		if (pages > 10)
		{
			System.out.println("Invalid number, too high. Defaulting to 10");
			pages = 10;
		}
		else
			if (pages < 1)
			{	

				System.out.println("Invalid entry, cancelling search.");
				pages = 0;
			}

		try {


			// Loop through the requested number of pages.
			for (int i = 0; i < pages; i++)
			{
				// Connect to the search engine and perform the search
				com.google.api.services.customsearch.Customsearch.Cse.List list = customsearch.cse().list(searchStr);

				if (i > 0)
				{	
					// Determine the starting page for the iteration 
					list.setStart((long) (i * 10));
				}

				list.setKey(apiKey);
				list.setCx(searchKey);
				Search results = list.execute();
				items = results.getItems();


				System.out.println("PAGE " + (i+ 1));

				// Loop through the results for the current page
				for(Result result:items)
				{
					if (!found && result.getFormattedUrl().contains(indexStr))
					{
						found = true;
						index = items.indexOf(result) + i;
						page = i;
					}

					System.out.println("******************************************");
					System.out.println(result.getTitle());
					System.out.println(result.getFormattedUrl());


				}


			}


		} catch (IOException e) {

			e.printStackTrace();
		}

		if (found)
		{
			System.out.println("\n************************************************");
			System.out.println("Link containing The Washington Post is located approximately at position " + (index + 1));
			System.out.println("Title: " + items.get(index - page).getTitle());
			System.out.println("Snippet: " + items.get(index - page).getSnippet());
			System.out.println("URL: " + items.get(index - page).getFormattedUrl());
			System.out.println("************************************************");
		}
		else
		{
			System.out.println("*********************************************");
			System.out.println("None of the results found included the url " + indexStr);

		}

	}


}
