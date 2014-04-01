
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

@SuppressWarnings("rawtypes")
public class FunThings extends ListenerAdapter {
	String tweet = "";

	public void onMessage(MessageEvent event) {

		if (event.getMessage().startsWith("!")) {

			if (event.getMessage().equalsIgnoreCase("!suicide")) {
				if (Palebot.getMessageCount() < 18) {
					Palebot.sendMessageTime();
					event.getChannel().send().message(".timeout " + event.getUser().getNick() + " 1");

				}
			}

			/**
			 * command to ban Uberpro
			 */
			if (event.getMessage().equalsIgnoreCase("!banuber")) {
				if (Palebot.getMessageCount() < 19) {
					Palebot.sendMessageTime();
					event.getChannel().send().message(".timeout Uberpro 1");

				}
			}

			/**
			 * command to ban Dojima_nanako
			 */
			if (event.getMessage().equalsIgnoreCase("!groundnanako")) {
				if (Palebot.getMessageCount() < 19) {
					Palebot.sendMessageTime();
					event.getChannel().send().message(".timeout Dojima_nanako 1");
				}
			}

			/**
			 * command to ban tears
			 */
			if (event.getMessage().equalsIgnoreCase("!bantears")) {
				if (Palebot.getMessageCount() < 19) {
					Palebot.sendMessageTime();
					event.getChannel().send().message(".timeout Xxtearsxx 1");
				}

			}
		}
	}
}
