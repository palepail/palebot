import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

public class Window extends JFrame {

	private static final int WIDTH = 1024;
	private static final int HEIGHT = 650;
	private static ArrayList<String> channels;

	JTextArea display;
	Timer timer = new Timer();
	JScrollPane scroll;

	static int port = 6667;

	private JLabel usernameLabel, passwordLabel, serverLabel, channelLabel, bannerLabel, finestLabel, utilitiesLabel,
			funLabel;
	private JTextField usernameTF, passwordTF, serverTF, channelTF, messageTF;
	private JCheckBox bannerBox, finestBox, utilitiesBox, funBox;
	private JButton cButton, dButton, sendButton;
	private JLabel connectedLabel = new JLabel("Disconnected", SwingConstants.CENTER);

	FinestKO fko = new FinestKO();
	BotBanner banner = new BotBanner();
	Utilities utilities = new Utilities();
	FunThings fun = new FunThings();
	BoxListener boxListener = new BoxListener();
	PircBotX bot;

	public Window() {

		usernameLabel = new JLabel("UserName: ", SwingConstants.RIGHT);
		passwordLabel = new JLabel("Password: ", SwingConstants.RIGHT);
		serverLabel = new JLabel("Server: ", SwingConstants.RIGHT);
		channelLabel = new JLabel("Channel: ", SwingConstants.RIGHT);
		bannerLabel = new JLabel("Bot-Banner: ", SwingConstants.RIGHT);
		finestLabel = new JLabel("FinestKO: ", SwingConstants.RIGHT);
		utilitiesLabel = new JLabel("Utilities: ", SwingConstants.RIGHT);
		funLabel = new JLabel("FunStuff: ", SwingConstants.RIGHT);

		usernameTF = new JTextField("palebot", 15);
		passwordTF = new JPasswordField(15);
		messageTF = new JTextField("", 15);

		serverTF = new JTextField("irc.twitch.tv", 15);
		channelTF = new JTextField("#finestko", 15);

		cButton = new JButton("Connect");
		dButton = new JButton("Disconnect");
		sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonHandler());
		cButton.addActionListener(new ConnectButtonHandler());
		dButton.addActionListener(new DisconnectButtonHandler());

		bannerBox = new JCheckBox();
		finestBox = new JCheckBox();
		utilitiesBox = new JCheckBox();
		funBox = new JCheckBox();
		bannerBox.addItemListener(boxListener);
		finestBox.addItemListener(boxListener);
		utilitiesBox.addItemListener(boxListener);
		funBox.addItemListener(boxListener);

		bot = Palebot.getBot();

		setTitle("PaleBot");
		bot.setVerbose(true);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));
		middlePanel.setLayout(new BorderLayout());

		display = new JTextArea(25, 80);
		display.setEditable(false); // set textArea non-editable

		display.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret) display.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);

		Document document = display.getDocument();
		document.addDocumentListener(new ScrollingDocumentListener());

		redirectSystemStreams();

		scroll = new JScrollPane(display);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Add Textarea in to middle panel
		middlePanel.add(scroll, BorderLayout.CENTER);

		Container pane = getContentPane();

		// Create textedit

		// Set the layout.

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;

		pane.add(usernameLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.5;
		pane.add(usernameTF, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.5;
		pane.add(bannerLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.5;
		pane.add(bannerBox, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.5;
		pane.add(passwordLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.5;
		pane.add(passwordTF, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.weightx = 0.5;
		pane.add(finestLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		c.weightx = 0.5;
		pane.add(finestBox, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0.5;
		pane.add(serverLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.5;
		pane.add(serverTF, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		c.weightx = 0.5;
		pane.add(utilitiesLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 2;
		c.weightx = 0.5;
		pane.add(utilitiesBox, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0.5;
		pane.add(channelLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 0.5;
		pane.add(channelTF, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		c.weightx = 0.5;
		pane.add(funLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 3;
		c.weightx = 0.5;
		pane.add(funBox, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.weightx = 0.5;
		pane.add(new JLabel(""), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		c.weightx = 0.5;
		pane.add(cButton, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 4;
		c.weightx = 0.5;
		pane.add(connectedLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 4;
		c.weightx = 0.5;
		pane.add(dButton, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.weightx = 0.5;
		c.ipady = 400;
		c.gridwidth = 4;
		pane.add(middlePanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		c.ipady = 0;
		c.weightx = 0.5;
		c.gridwidth = 3;
		pane.add(messageTF, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 6;
		c.weightx = 0.5;
		c.gridwidth = 1;
		pane.add(sendButton, c);

	}

	private void updateTextArea(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				display.append(text);

			}
		});
	}

	private void redirectSystemStreams() {
		OutputStream out = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextArea(String.valueOf((char) b));
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				updateTextArea(new String(b, off, len));
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};

		System.setOut(new PrintStream(out, true));
		System.setErr(new PrintStream(out, true));
	}

	public void setConnectedLabel(String status) {
		connectedLabel.setText(status);

	}

	public void connect() {

		try {
			bot.setName(usernameTF.getText());
			bot.connect(serverTF.getText(), port, passwordTF.getText());
			bot.joinChannel(channelTF.getText());
			channels.add(channelTF.getText());
			setConnectedLabel("Connected");
			Palebot.setShouldBeConnected(true);
		} catch (IOException | IrcException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			setConnectedLabel("Disconnected");
		}

	}

	public class ConnectButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (bot == null) {
				bot = Palebot.getBot();
			}
			if (!bot.isConnected()) {
				connect();
				Palebot.setShouldBeConnected(true);
			}

		}

	}

	public class SendButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (bot == null) {
				bot = Palebot.getBot();
			}
			if (bot.isConnected() && messageTF.getText() != "") {
				bot.sendMessage(channelTF.getText(), messageTF.getText());
				Palebot.sendMessage();
				messageTF.setText("");
			}

		}

	}

	// ScrollingDocumentListener takes care of re-scrolling when appropriate
	class ScrollingDocumentListener implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {
			maybeScrollToBottom();
		}

		public void insertUpdate(DocumentEvent e) {
			maybeScrollToBottom();
		}

		public void removeUpdate(DocumentEvent e) {
			maybeScrollToBottom();
		}

		private void maybeScrollToBottom() {
			JScrollBar scrollBar = scroll.getVerticalScrollBar();
			boolean scrollBarAtBottom = isScrollBarFullyExtended(scrollBar);
			boolean scrollLock = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_SCROLL_LOCK);
			if (scrollBarAtBottom && !scrollLock) {
				// Push the call to "scrollToBottom" back TWO PLACES on the
				// AWT-EDT queue so that it runs *after* Swing has had an
				// opportunity to "react" to the appending of new text:
				// this ensures that we "scrollToBottom" only after a new
				// bottom has been recalculated during the natural
				// revalidation of the GUI that occurs after having
				// appending new text to the JTextArea.
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								scrollToBottom(display);
							}
						});
					}
				});
			}
		}
	}

	public static boolean isScrollBarFullyExtended(JScrollBar vScrollBar) {
		BoundedRangeModel model = vScrollBar.getModel();
		return (model.getExtent() + model.getValue()) == model.getMaximum();
	}

	public static void scrollToBottom(JComponent component) {
		Rectangle visibleRect = component.getVisibleRect();
		visibleRect.y = component.getHeight() - visibleRect.height;
		component.scrollRectToVisible(visibleRect);
	}

	public class DisconnectButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (bot != null && bot.isConnected()) {
				bot.disconnect();
				setConnectedLabel("Disconnected");
				Palebot.setShouldBeConnected(false);
			}

		}

	}

	public void addQuotes() {
		bot.getListenerManager().addListener(new QuoteCommand());
	}

	public void removeQuotes() {
		bot.getListenerManager().addListener(new QuoteCommand());
	}

	public class BoxListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent e) {

			if (e.getSource() == finestBox) {

				if (e.getStateChange() == 1) {
					bot.getListenerManager().addListener(fko);
				}
				if (e.getStateChange() == 2) {
					bot.getListenerManager().removeListener(fko);
				}
			} else if (e.getSource() == bannerBox) {

				if (e.getStateChange() == 1) {
					bot.getListenerManager().addListener(banner);
				}
				if (e.getStateChange() == 2) {
					bot.getListenerManager().removeListener(banner);
				}
			} else if (e.getSource() == utilitiesBox) {
				if (e.getStateChange() == 1) {
					bot.getListenerManager().addListener(utilities);
				}
				if (e.getStateChange() == 2) {
					bot.getListenerManager().removeListener(utilities);
				}
			} else if (e.getSource() == funBox) {

				if (e.getStateChange() == 1) {
					bot.getListenerManager().addListener(fun);
				}
				if (e.getStateChange() == 2) {
					bot.getListenerManager().removeListener(fun);
				}

			}

		}

	}
}
