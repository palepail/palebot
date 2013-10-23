import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.Timer;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class Utilities extends ListenerAdapter {
	PircBotX bot = Palebot.getBot();
	Timer timer;

	public void onMessage(MessageEvent event) {
		/**
		 * palebot info
		 */
		if (event.getMessage().equals("!palebot") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			event.getBot().sendMessage(event.getChannel(),
					"Hi, I'm palebot! I'm run and operated by palepail so if I mess up he's to blame.");

		}

		if (event.getUser().getChannelsOpIn().contains(event.getChannel())
				&& event.getMessage().startsWith("FKO says:") && event.getUser().getNick().equals("Rise_bot")) {

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
						System.out.println("Found Quote");
						found = true;
						break;
					}
					line = reader.readLine();
				}
				if (!found) {
					addQuote(quote, event);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void addQuote(String string, MessageEvent event) {
		System.out.println("attempting to add a quote");
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