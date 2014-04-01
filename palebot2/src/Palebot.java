import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.Timer;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.output.OutputChannel;
import org.pircbotx.output.OutputIRC;

public class Palebot {

	private static ArrayList<Long> messageTimes = new ArrayList<Long>();
	private static int port = 6667;

	public static PircBotX bot = new PircBotX(null);
	private static OutputIRC out = new OutputIRC(bot);
	private static OutputChannel outChan;
	final static Window window = new Window();

	public static void main(String[] args) throws Exception {

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (bot != null) {
					if (bot.isConnected()) {
						out.quitServer();
					}
				}
			}
		});

		ActionListener task = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (messageTimes.size() > 0 && System.currentTimeMillis() - messageTimes.get(0) > 30000) {
					messageTimes.remove(0);
					System.out.println("Message Count: " + getMessageCount());
				}
			}
		};

		Timer timer = new Timer(1000, task); // fire every 1 seconds
		timer.setRepeats(true);
		timer.start();

		window.revalidate();

	}
	public static void disconnectBot(){
		out.quitServer();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void connectBot() {
		Configuration configuration;
		if (!bot.isConnected()) {
			configuration = new Configuration.Builder().setName("palebot").setAutoNickChange(true).setCapEnabled(true)
					// Enable CAP features
					.addListener(new FunThings()).addAutoJoinChannel(window.getChannel())
					.buildForServer("irc.twitch.tv", port, "oauth:pdoyak9fapd7t45fm47xas2ple7d4y7");
			bot = new PircBotX(configuration);

			// Connect to server
			try {
				bot.startBot();
				outChan = new OutputChannel(bot, bot.getUserBot().getChannels().first());

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static Window getWindow() {
		return window;
	}

	public static int getMessageCount() {
		return messageTimes.size();
	}

	public static void addMessageTime(long time) {
		messageTimes.add(time);
	}

	public static void sendMessageTime() {
		addMessageTime(System.currentTimeMillis());
		System.out.println("Message sent at: " + System.currentTimeMillis());
	}

	public static void sendMessage(String text) {
		if (Palebot.getMessageCount() < 19) {
			outChan.message(text);
			sendMessageTime();
		}
	}

}