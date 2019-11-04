package de.syscy.smtptutorial;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import de.syscy.smtptutorial.account.AccountManager;
import de.syscy.smtptutorial.listener.AccountListener;
import de.syscy.smtptutorial.listener.MainListener;
import de.syscy.smtptutorial.listener.SMTPServerListener;
import de.syscy.smtptutorial.packet.CreateAccountPacket;
import de.syscy.smtptutorial.packet.LoginPacket;
import de.syscy.smtptutorial.packet.SMTPMessagePacket;
import de.syscy.smtptutorial.smtp.SMTPServer;
import lombok.Getter;

import java.util.logging.Logger;

public class TutorialBackend {
	public static Logger LOGGER;

	public static final String SERVER_DOMAIN_NAME = "tutorial-smtp.de";

	public static final String SOCKET_HOSTNAME = "localhost";
	public static final int SOCKET_PORT = 7898;

	private static @Getter TutorialBackend instance;

	private @Getter AccountManager accountManager;
	private @Getter SMTPServer smtpServer;

	private @Getter SocketIOServer server;

	private TutorialBackend() {
		instance = this;
	}

	private void init() {
		LOGGER.info("Preparing backend server");

		accountManager = new AccountManager();
		smtpServer = new SMTPServer();

		Configuration config = new Configuration();
		config.setHostname(SOCKET_HOSTNAME);
		config.setPort(SOCKET_PORT);

		server = new SocketIOServer(config);

		MainListener mainListener = new MainListener(this);
		server.addConnectListener(mainListener);
		server.addDisconnectListener(mainListener);

		AccountListener accountListener = new AccountListener(this);
		server.addEventListener("create-account", CreateAccountPacket.class, accountListener::onAccountCreate);
		server.addEventListener("login", LoginPacket.class, accountListener::onLogin);

		SMTPServerListener smtpServerListener = new SMTPServerListener(this);
		server.addEventListener("smtp-message", SMTPMessagePacket.class, smtpServerListener::onSMTPMessage);
	}

	private void runServer() throws InterruptedException {
		server.start();

		Thread.sleep(Integer.MAX_VALUE);

		server.stop();
	}

	public static void main(String[] args) throws InterruptedException {
		TutorialBackend backend = new TutorialBackend();
		backend.init();
		backend.runServer();
	}

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		LOGGER = Logger.getLogger(TutorialBackend.class.getName());
	}
}