import java.util.Date;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
public class TimeCommand extends ListenerAdapter {
        public void onMessage(MessageEvent event) {
                if (event.getMessage().equals("!time"))
                        event.respond("The current time is " + new Date());
        }
}