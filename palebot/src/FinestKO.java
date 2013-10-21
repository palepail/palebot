import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class FinestKO extends ListenerAdapter {
	String tweet = "";

	public void onMessage(MessageEvent event) {
		/**
		 * command to ban Uberpro
		 */
		if (event.getMessage().equals("!banuber")) {
			if (Palebot.getMessageCount() < 18) {
				Palebot.sendMessage();
				Palebot.sendMessage();
				event.getBot().sendMessage(event.getChannel(), ".timeout Uberpro 1");
				event.getBot().sendMessage(event.getChannel(), ".timeout Freeuber 1");
			}
		}
		/**
		 * command to ban tears
		 */
		if (event.getMessage().equals("!bantears")) {
			if (Palebot.getMessageCount() < 19) {
				Palebot.sendMessage();
				event.getBot().sendMessage(event.getChannel(), ".timeout Xxtearsxx 1");
			}
			/**
			 * command to savetweet
			 */
			if (event.getMessage().startsWith("!settweet")
					&& event.getUser().getChannelsOpIn().contains(event.getChannel())) {
				if (Palebot.getMessageCount() < 19) {
					tweet = event.getMessage().substring(10);
					Palebot.sendMessage();
					event.getBot().sendMessage(event.getChannel(), "Tweet Saved");
				}
			}

			/**
			 * command to say tweet
			 */
			if (event.getMessage().equals("!tweet") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {

				if (Palebot.getMessageCount() < 19) {
					Palebot.sendMessage();
					event.getBot().sendMessage(event.getChannel(),
							"If you would like to support FinestKO you can do by retweeting " + tweet);
				}
			}

			/**
			 * command to post donation link
			 */
			if (event.getMessage().equals("!donate") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {

				if (Palebot.getMessageCount() < 19) {
					Palebot.sendMessage();
					event.getBot().sendMessage(event.getChannel(),
							"If you would like to donate to FinestKO you can do so here Tinyurl.com/CoopaTuition2");
				}

			}

			/**
			 * command to post FKO links
			 */
			if (event.getMessage().equals("!fko") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {
				if (Palebot.getMessageCount() < 19) {

					Palebot.sendMessage();
					event.getBot()
							.sendMessage(
									event.getChannel(),
									"Please follow FinestKo at http://www.twitch.tv/finestko, https://twitter.com/FinestKO, http://www.youtube.com/FinestkoProductions, and https://www.facebook.com/FinestKO");
				}
			}

			/**
			 * FKO quotes
			 */

			if (event.getMessage().equals("!fkosays")) {
				System.out.println("attemting to read a random quote");
				if (Palebot.getMessageCount() < 19) {
					String line = "";
					BufferedReader reader;
					try {
						reader = new BufferedReader(new FileReader("quotes.txt"));
						List<String> lines = new ArrayList<String>();
						line = reader.readLine();
						while (line != null) {
							lines.add(line);
							line = reader.readLine();
						} // Choose a random one from the list
						Random r = new Random();
						Palebot.sendMessage();
						event.getBot().sendMessage(event.getChannel(),
								"FKO says: " + lines.get(r.nextInt(lines.size())));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (event.getUser().getChannelsOpIn().contains(event.getChannel())
					&& event.getMessage().startsWith("!fkosays add")) {
				if (Palebot.getMessageCount() < 19) {
					System.out.println("attempting to add a quote");

					String line = event.getMessage().substring(11);

					BufferedWriter output;
					try {
						output = new BufferedWriter(new FileWriter("quotes.txt", true));
						output.append(line);
						output.newLine();
						output.close();
						Palebot.sendMessage();
						event.getBot().sendMessage(event.getChannel(), "Quote Saved");
					} catch (IOException e) {
						e.printStackTrace();
					} finally {

					}
				}
			}
		}
	}
}