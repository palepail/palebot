import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

public class Utilities extends ListenerAdapter {
	PircBotX bot = Palebot.getBot();
	public void onMessage(MessageEvent event) {
		/**
		 * palebot info
		 */
		if (event.getMessage().equals("!palebot") && event.getUser().getChannelsOpIn().contains(event.getChannel())) {
			event.getBot().sendMessage(event.getChannel(), "Hi, I'm palebot! I'm run and operated by palepail so if I mess up he's to blame.");
	
		}

	
	}
	public void onDisconnect(){
		if(Palebot.shouldBeConnected()&&!bot.isConnected()){
			try {
				Thread.sleep(5000);
				bot.reconnect();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NickAlreadyInUseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IrcException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
			
		}
	} 
}