import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class FinestKO extends ListenerAdapter {
	String tweet="";
	public void onMessage(MessageEvent event) {
		/**
		 * command to ban tears
		 */
		if (event.getMessage().equals("!bantears") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			event.getBot().sendMessage(event.getChannel(), ".ban Xxtearsxx");
			event.getBot().sendMessage(event.getChannel(), ".unban Xxtearsxx");
		}
		
		/**
		 * command to savetweet
		 */
		if (event.getMessage().startsWith("!settweet") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {

					tweet = event.getMessage().substring(10);
					
					event.getBot().sendMessage(event.getChannel(),
							"Tweet Saved");
		}
		
		/**
		 * command to say tweet
		 */
		if (event.getMessage().equals("!tweet") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {

			event.getBot().sendMessage(event.getChannel(),
					"If you would like to support FinestKO you can do by retweeting " + tweet);
		}

		/**
		 * command to post donation link
		 */
		if (event.getMessage().equals("!donate") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			event.getBot().sendMessage(event.getChannel(),
					"If you would like to donate to FinestKO you can do so here Tinyurl.com/CoopaTuition2");

		}

		/**
		 * command to post FKO links
		 */
		if (event.getMessage().equals("!fko") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			event.getBot()
					.sendMessage(
							event.getChannel(),
							"Please follow FinestKo at http://www.twitch.tv/finestko, https://twitter.com/FinestKO, http://www.youtube.com/FinestkoProductions, and https://www.facebook.com/FinestKO");

		}
		
		

		/**
		 * FKO quotes
		 */
		if (event.getUser().getChannelsOpIn().contains(event.getChannel()) && event.getMessage().equals("!fkosays")) {
			System.out.println("attemting to read a random quote");
			String line = "";
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("quotes.txt"));
				List<String> lines = new ArrayList<String>();
				line = reader.readLine();
				while (line != null) {
					lines.add(line);
					line = reader.readLine();
				}
				// Choose a random one from the list
				Random r = new Random();
				event.getBot().sendMessage(event.getChannel(), lines.get(r.nextInt(lines.size())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (event.getUser().getChannelsOpIn().contains(event.getChannel())
				&& event.getMessage().startsWith("!fkosays add")) {

			System.out.println("attempting to add a quote");
			Date dNow = new Date( );
		      SimpleDateFormat ft = 
		      new SimpleDateFormat ("MM.dd.yyyy");
			String line = event.getMessage().substring(12) + " " + ft.format(dNow);
			FileOutputStream fop = null;
			File file;

			try {

				file = new File("quotes.txt");
				fop = new FileOutputStream(file);

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = line.getBytes();

				fop.write(contentInBytes);
				fop.flush();
				fop.close();

				System.out.println("Done");
				event.getBot().sendMessage(event.getChannel(), "Quote Saved");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (fop != null) {
						fop.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}