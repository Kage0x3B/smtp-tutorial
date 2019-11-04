package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;

public class HelloCommand extends SMTPCommand {
	HelloCommand() {
		super("HELO");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		session.setState(SessionState.GREETING);

		return SMTPResponse.ok("Hi, welcome to the tutorial SMTP server!");
	}

}