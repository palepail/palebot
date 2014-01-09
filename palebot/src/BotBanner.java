import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class BotBanner extends ListenerAdapter {
	int banCount = 0;

	private void secondLine(MessageEvent event) {
		String line = "";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("botendingphrases.txt"));
			line = reader.readLine();
			while (line != null) {
				if (event.getMessage().contains(line)) {
					System.out.println("bot confirmed");
					if (Palebot.getMessageCount() < 17) {
						banCount++;
						Palebot.sendMessage();
						event.getBot().sendMessage(event.getChannel(),
								".me - " + banCount + " : " + event.getUser().getNick() + " -  0 ");

						try {
							Thread.sleep(1000);
							Palebot.sendMessage();
							event.getBot().sendMessage(event.getChannel(), ".ban " + event.getUser().getNick());

						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					break;
				}
				line = reader.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addEndingPhrase(String string) {
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter("botendingphrases.txt", true));
			output.append(string);
			output.newLine();
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void addStartingPhrase(String string) {

		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter("botstartingphrases.txt", true));
			
			
			output.append(string);
			output.newLine();
			output.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public void onMessage(MessageEvent event) {
		/**
		 * check if message starts with common bot starter
		 */

		File file = new File("botstartingphrases.txt");
		File file2 = new File("botendingphrases.txt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
				file2.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String line = "";
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("botstartingphrases.txt"));
			line = reader.readLine();
			while (line != null) {
				if (event.getMessage().startsWith(line)) {
					System.out.println("questionable start");
					secondLine(event);
					break;
				}
				line = reader.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * command to display amount of bots banned
		 */
		if (event.getMessage().startsWith("!botsbanned")
				&& event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			if (Palebot.getMessageCount() < 19) {
				Palebot.sendMessage();
				event.getBot().sendMessage(event.getChannel(), "I have smote " + banCount + " bots today.");
			}
		}
		/**
		 * commands to add phrases to the ban list
		 */
		if (event.getMessage().startsWith("!botbanner add end")
				&& event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			if (Palebot.getMessageCount() < 19) {
				Palebot.sendMessage();
				line = event.getMessage().substring(19);

				event.getBot().sendMessage(event.getChannel(), "Phrase Added");
				addEndingPhrase(line);
			}
		}

		if (event.getMessage().startsWith("!botbanner add start")
				&& event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			if (Palebot.getMessageCount() < 19) {
				Palebot.sendMessage();

				line = event.getMessage().substring(21);
				addStartingPhrase(line);
				event.getBot().sendMessage(event.getChannel(), "Phrase Added");
			}
		}
	}
}
