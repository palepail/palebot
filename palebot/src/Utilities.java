import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class Utilities extends ListenerAdapter {
	PircBotX bot = Palebot.getBot();
	Timer timer;

	public void onMessage(MessageEvent event) {
		/**
		 * palebot info
		 */
		if (event.getMessage().equals("!palebot") &&  event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			if (Palebot.getMessageCount() < 19) {
				event.getBot().sendMessage(event.getChannel(),

				"Hi, I'm palebot! I'm run and operated by palepail so if I mess up he's to blame.");
				Palebot.sendMessage();
			}
		}

		if (event.getMessage().equalsIgnoreCase("!addQuotes")
				&& event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			Palebot.getWindow().addQuotes();
			if (Palebot.getMessageCount() < 19) {
				event.getBot().sendMessage(event.getChannel(), "Quotes Enabled");
				Palebot.sendMessage();
			}

		}
		if (event.getMessage().equalsIgnoreCase("!removeQuotes")
				&& event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			Palebot.getWindow().removeQuotes();
			if (Palebot.getMessageCount() < 19) {
				event.getBot().sendMessage(event.getChannel(), "Quotes Diabled");
				Palebot.sendMessage();
			}

		}

		if (event.getMessage().startsWith("FKO says:") && event.getUser().getNick().equalsIgnoreCase("Rise_bot")) {

			// Check if quote has been added
			BufferedReader reader;
			String line;
			String quote = event.getMessage().substring(10);
			boolean found = false;
			try {
				reader = new BufferedReader(new FileReader("quotes.txt"));
				line = reader.readLine();
				while (line != null) {
					if (line.equals(quote)) {
						System.out.println("Already Have Quote");
						found = true;
						break;
					}
					line = reader.readLine();
				}
				if (!found) {
					addQuote(quote, event);
					System.out.println("Adding Quote");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void addQuote(String string, MessageEvent event) {
		String line = string;

		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter("quotes.txt", true));
			output.append(line);
			output.newLine();
			output.close();
			// event.getBot().sendMessage(event.getChannel(), "Quote Saved");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

}