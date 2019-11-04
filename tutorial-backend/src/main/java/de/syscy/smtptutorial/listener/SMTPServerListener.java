package de.syscy.smtptutorial.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.account.TutorialAccount;
import de.syscy.smtptutorial.packet.ErrorResponsePacket;
import de.syscy.smtptutorial.packet.SMTPMessagePacket;
import de.syscy.smtptutorial.packet.SMTPResponsePacket;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPServer;
import de.syscy.smtptutorial.smtp.SMTPSession;

import java.util.Optional;

public class SMTPServerListener {
	private final TutorialBackend backend;
	private final SMTPServer smtpServer;

	public SMTPServerListener(TutorialBackend backend) {
		this.backend = backend;
		this.smtpServer = backend.getSmtpServer();
	}

	public void onSMTPMessage(SocketIOClient client, SMTPMessagePacket data, AckRequest ackSender) throws Exception {
		Optional<TutorialAccount> account = backend.getAccountManager().lookupSocketClientAccount(client);

		if(account.isPresent()) {
			SMTPSession smtpSession = smtpServer.getSession(account.get());

			SMTPResponse response = smtpSession.handleRequest(data.getMessage());

			if(response != null) {
				for(String line : response.toResponseList()) {
					if(line != null) {
						client.sendEvent("smtp-response", new SMTPResponsePacket(response.getStatusCode(), line));
					}
				}
			}
		} else {
			client.sendEvent("error-response", new ErrorResponsePacket("Could not execute SMTP command, there is a problem with your account, try reloading the page."));
		}
	}
}