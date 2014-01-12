import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class QuoteCommand extends ListenerAdapter {
	public void onMessage(MessageEvent event) {

		if (event.getMessage().startsWith("!fkosays")) {

			if (event.getMessage().equals("!fkosays")) {
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

			if (event.getMessage().equalsIgnoreCase("!fkosays count")) {
				if (Palebot.getMessageCount() < 19) {
					try {
						Palebot.sendMessage();
						event.getBot().sendMessage(event.getChannel(), "I have " + count("quotes.txt") + " quotes.");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (event.getUser().getChannelsOpIn().contains(event.getChannel())
					&& event.getMessage().startsWith("!fkosays add")) {

				System.out.println("attempting to add a quote");
				String line = event.getMessage().substring(12);
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
					if (Palebot.getMessageCount() < 19) {
						event.getBot().sendMessage(event.getChannel(), "Quote Saved");
						Palebot.sendMessage();
					}

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

			if (event.getUser().getChannelsOpIn().contains(event.getChannel())
					&& event.getMessage().startsWith("!fkosays quote")) {

				String snip = event.getMessage().substring(15);
				int lineToSay = 0;
				if (isInteger(snip.trim())) {
					lineToSay = Integer.parseInt(snip);
					try {
						if (lineToSay <= count("quotes.txt") && lineToSay > 0) {

							BufferedReader bf = new BufferedReader(new FileReader("quotes.txt"));
							String currentLine;
							int linecount = 0;
							while ((currentLine = bf.readLine()) != null) {
								// trim newline when comparing with lineToRemove
								linecount++;
								if (lineToSay == linecount) {
									if (Palebot.getMessageCount() < 19) {
										event.getBot().sendMessage(event.getChannel(), "FKO says: " + currentLine);
										Palebot.sendMessage();
										break;
									}

								}

							}

							// Close the file after done searching
							bf.close();

						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			if (event.getUser().getChannelsOpIn().contains(event.getChannel())
					&& event.getMessage().startsWith("!fkosays search")) {

				String snip = event.getMessage().substring(16);
				try {
					// Open the file c:\test.txt as a buffered reader
					BufferedReader bf = new BufferedReader(new FileReader("quotes.txt"));

					// Start a line count and declare a string to hold our
					// current
					// line.
					int linecount = 0;
					String line;
					ArrayList<Integer> linesFound = new ArrayList<Integer>();

					// Let the user know what we are searching for
					System.out.println("Searching for " + snip + " in file...");

					// Loop through each line, stashing the line into our line
					// variable.
					while ((line = bf.readLine()) != null) {
						// Increment the count and find the index of the word
						linecount++;
						int indexfound = line.indexOf(snip);

						// If greater than -1, means we found the word
						if (indexfound > -1) {

							linesFound.add(linecount);

						}
					}
					if (linesFound.size() > 0) {
						if (Palebot.getMessageCount() < 19) {
							String msg = "Quote found on lines: ";
							for (Integer x : linesFound) {
								msg += String.valueOf(x) + " ";
							}
							event.getBot().sendMessage(event.getChannel(), msg);
							Palebot.sendMessage();
						}
					} else {
						if (Palebot.getMessageCount() < 19) {
							event.getBot().sendMessage(event.getChannel(), "Quote not found");
							Palebot.sendMessage();
						}
					}

					// Close the file after done searching
					bf.close();
				} catch (IOException e) {
					System.out.println("IO Error Occurred: " + e.toString());
				}
			}

			if (event.getUser().getChannelsOpIn().contains(event.getChannel())
					&& event.getMessage().startsWith("!fkosays delete")) {

				String snip = event.getMessage().substring(16);
				int lineToRemove = 0;
				if (isInteger(snip)) {
					lineToRemove = Integer.parseInt(snip);
					int currentLineNum = 0;

					try {
						if (lineToRemove <= count("quotes.txt") && lineToRemove > 0) {
							File inputFile = new File("quotes.txt");
							File tempFile = new File("temp.txt");

							// if file doesnt exists, then create it
							if (!tempFile.exists()) {
								tempFile.createNewFile();
							}

							BufferedReader reader = new BufferedReader(new FileReader(inputFile));
							BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

							String currentLine;

							while ((currentLine = reader.readLine()) != null) {
								// trim newline when comparing with lineToRemove
								currentLineNum++;
								if (lineToRemove == currentLineNum)
									continue;
								// if current line not start with
								// lineToRemove then write to file
								writer.write(currentLine);
								writer.newLine();
							}

							reader.close();
							writer.close();
							boolean deleteSuccess = inputFile.delete();
							boolean renameSuccess = tempFile.renameTo(inputFile);

							System.out.println("Quotes delete success: " + deleteSuccess);
							System.out.println("Temp rename success: " + renameSuccess);

							if (deleteSuccess && renameSuccess) {
								if (Palebot.getMessageCount() < 19) {
									event.getBot().sendMessage(event.getChannel(), "Quote Deleted");
									Palebot.sendMessage();
								}
							} else {
								if (Palebot.getMessageCount() < 19) {
									event.getBot().sendMessage(event.getChannel(), "Delete Failed");
									Palebot.sendMessage();
								}
							}

						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int count(String filename) throws IOException {

		BufferedReader bf = new BufferedReader(new FileReader("quotes.txt"));

		int lineCount = 0;
		while (bf.readLine() != null) {
			// trim newline when comparing with lineToRemove
			lineCount++;

		}

		// Close the file after done searching
		bf.close();
		return lineCount;

	}
}