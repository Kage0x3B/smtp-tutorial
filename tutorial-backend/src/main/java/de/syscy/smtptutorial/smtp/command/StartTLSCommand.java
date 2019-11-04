package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.StatusCode;

public class StartTLSCommand extends SMTPCommand {
	StartTLSCommand() {
		super("STARTTLS");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		if(session.isEncrypted()) {
			return SMTPResponse.create(StatusCode.STATUS_502, "Command not implemented, TLS encryption is already enabled");
		}

		session.setEncrypted(true);

		return SMTPResponse.create(StatusCode.STATUS_220, "Ready to start TLS", "(A normal server would now negotiate TLS and the connection is protected by TLS", "for every following command but this isn't support by this tutorial server)");
	}
}