import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class QuoteCommand extends ListenerAdapter {
	public void onMessage(MessageEvent event) {
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
			String line = event.getMessage().substring(12) + ". Quote added by " + event.getUser().getNick();
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