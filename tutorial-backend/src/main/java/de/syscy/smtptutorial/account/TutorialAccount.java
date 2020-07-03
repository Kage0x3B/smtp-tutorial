package de.syscy.smtptutorial.account;

import com.corundumstudio.socketio.SocketIOClient;
import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.packet.ReceivedMailPacket;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class TutorialAccount {
	private final String name;
	private final String password;
	private final boolean admin;

	private final MailStorage mailStorage = new MailStorage();

	private SocketIOClient socketClient = null;

	public TutorialAccount(String name, String password, boolean admin) {
		this.name = name;
		this.password = password;
		this.admin = admin;

		String sender = "moritz@smtp-tutorial.de";
		List<String> receiver = Collections.singletonList(name + "@" + TutorialBackend.SERVER_DOMAIN_NAME);

		String data = "From: Moritz Hein <moritz@" + TutorialBackend.SERVER_DOMAIN_NAME + ">\n" + "To: " + name + " <" + name + "@" + TutorialBackend.SERVER_DOMAIN_NAME + ">\n" + "Subject: Willkommen zum SMTP Tutorial\n" + "\n" + "Hi " + name + "!\n" + "Willkommen zu meinem interaktiven SMTP Tutorial.\n" + "Hiermit kannst du, während du meiner Erklärung zum SMTP Protokoll folgst, direkt alles gelernte ausprobieren\n" + "und allen anderen aus dem Kurs Mails schreiben.\n" + "Bei Fragen kannst du immer das HELP Kommando benutzen und sonst mich fragen!\n" + "\n" + "LG Moritz";

		MailData welcomeMail = new MailData(sender, receiver, data);
		receiveMail(welcomeMail);
	}

	public void receiveMail(MailData mailData) {
		mailStorage.storeMail(mailData);

		if(socketClient != null) {
			ReceivedMailPacket packet = new ReceivedMailPacket(mailData.getSenderAddress(), mailData.getData(), mailData.getSubject());
			socketClient.sendEvent("received-mail", packet);
		}
	}
}