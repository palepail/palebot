import org.pircbotx.PircBotX;


public class Palebot {
	
	private static boolean SHOULD_BE_CONNECTED;
	static PircBotX bot = new PircBotX();
	public static void main(String[] args) throws Exception {

		Window window = new Window();
		
	}

	/**
	 * @return the cONNECTED
	 */
	public static boolean shouldBeConnected() {
		return SHOULD_BE_CONNECTED;
	}

	/**
	 * @param cONNECTED the cONNECTED to set
	 */
	public static void setShouldBeConnected(boolean cONNECTED) {
		SHOULD_BE_CONNECTED = cONNECTED;
	}

	public static PircBotX getBot() {
		// TODO Auto-generated method stub
		return bot;
	}

}