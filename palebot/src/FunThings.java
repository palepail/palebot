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

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class FunThings extends ListenerAdapter {
	String tweet = "";

	public void onMessage(MessageEvent event) {
		
		if (event.getMessage().equalsIgnoreCase("!suicide")) {
			if (Palebot.getMessageCount() < 18) {
				Palebot.sendMessage();
				event.getBot().sendMessage(event.getChannel(), ".timeout "+event.getUser().getNick()+" 1");
				
			}
		}
		
		
		/**
		 * command to ban Uberpro
		 */
		if (event.getMessage().equalsIgnoreCase("!banuber")) {
			if (Palebot.getMessageCount() < 17) {
				Palebot.sendMessage();
				Palebot.sendMessage();
				Palebot.sendMessage();
				event.getBot().sendMessage(event.getChannel(), ".timeout Uberpro 1");
				event.getBot().sendMessage(event.getChannel(), ".timeout Freeuber 1");
				event.getBot().sendMessage(event.getChannel(), ".timeout MiserableUber 1");
			}
		}

		/**
		 * command to ban Dojima_nanako
		 */
		if (event.getMessage().equalsIgnoreCase("!groundnanako")) {
			if (Palebot.getMessageCount() < 19) {
				Palebot.sendMessage();
				event.getBot().sendMessage(event.getChannel(), ".timeout Dojima_nanako 1");
			}
		}

		/**
		 * command to ban tears
		 */
		if (event.getMessage().equalsIgnoreCase("!bantears")) {
			if (Palebot.getMessageCount() < 19) {
				Palebot.sendMessage();
				event.getBot().sendMessage(event.getChannel(), ".timeout Xxtearsxx 1");
			}

		}
	}
}
