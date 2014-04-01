import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class FinestKO extends ListenerAdapter {
	String tweet = "https://twitter.com/FinestKO/status/427160218942722048";

	public void onMessage(MessageEvent event) {
		
			/**
			 * command to say tweet
			 */
			if (event.getMessage().equals("!tweet") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {

				if (Palebot.getMessageCount() < 19) {
					Palebot.sendMessage();
					event.getBot().sendMessage(event.getChannel(),
							"If you would like to support FinestKO you can do so by retweeting " + tweet);
				}
			}
			
			if (event.getMessage().startsWith("!set tweet") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {
					
					tweet = event.getMessage().substring(10);

					if (Palebot.getMessageCount() < 19) {
						Palebot.sendMessage();
						event.getBot().sendMessage(event.getChannel(),
								"Tweet Saved");
					}
				

			}

			/**
			 * command to post donation link
			 */
			if (event.getMessage().equals("!donate") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {

				if (Palebot.getMessageCount() < 19) {
					Palebot.sendMessage();
					event.getBot().sendMessage(event.getChannel(),
							"If you would like to donate to FinestKO you can do so here Tinyurl.com/CoopaTuition2");
				}

			}

			/**
			 * command to post FKO links
			 */
			if (event.getMessage().equals("!fko") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {
				if (Palebot.getMessageCount() < 19) {
					Palebot.sendMessage();
					event.getBot()
							.sendMessage(
									event.getChannel(),
									"Please follow FinestKo at http://www.twitch.tv/finestko, https://twitter.com/FinestKO, http://www.youtube.com/FinestkoProductions, and https://www.facebook.com/FinestKO");
				}
			}



		}
	}
