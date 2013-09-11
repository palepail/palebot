import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import org.pircbotx.PircBotX;

public class Palebot {

	private static boolean SHOULD_BE_CONNECTED;
	static PircBotX bot = new PircBotX();

	public static void main(String[] args) throws Exception {

		Window window = new Window();
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (bot != null) {
					if (bot.isConnected()) {
						bot.disconnect();
					}
				}
			}
		});

	}

	/**
	 * @return the cONNECTED
	 */
	public static boolean shouldBeConnected() {
		return SHOULD_BE_CONNECTED;
	}

	/**
	 * @param cONNECTED
	 *            the cONNECTED to set
	 */
	public static void setShouldBeConnected(boolean cONNECTED) {
		SHOULD_BE_CONNECTED = cONNECTED;
	}

	public static PircBotX getBot() {
		// TODO Auto-generated method stub
		return bot;
	}

}