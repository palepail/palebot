import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Timer;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;

public class Palebot {

	private static boolean SHOULD_BE_CONNECTED;
	private static int floodBarrier=15;
	

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

		ActionListener task = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(floodBarrier<15)
				{
					upFloodBarrier();
					System.out.println("FloodBarrier: "+ getFloodBarrier());
				}
				if(Palebot.shouldBeConnected()&&!bot.isConnected()){
					try {
						bot.reconnect();
					} catch (IOException | IrcException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};

		Timer timer = new Timer(5000, task); // fire every 5 seconds
		timer.setRepeats(true);
		timer.start();
		
		window.revalidate();

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
	/**
	 * @return the floodBarrier
	 */
	public static int getFloodBarrier() {
		return floodBarrier;
	}

	/**
	 * @param floodBarrier the floodBarrier to set
	 */
	public static void upFloodBarrier() {
		Palebot.floodBarrier++;
	}
	
	public static void downFloodBarrier() {
		Palebot.floodBarrier--;
	}

}