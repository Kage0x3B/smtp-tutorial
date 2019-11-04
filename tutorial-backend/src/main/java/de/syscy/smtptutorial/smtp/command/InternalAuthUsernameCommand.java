package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;
import de.syscy.smtptutorial.smtp.StatusCode;

public class InternalAuthUsernameCommand extends SMTPCommand {
	InternalAuthUsernameCommand() {
		super("_AUTH_USERNAME");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		String username;

		try {
			username = decodeBase64(parameter);
		} catch(IllegalArgumentException ex) {
			return SMTPResponse.create(StatusCode.STATUS_501, "Syntax error in parameters, invalid base64 encoded username");
		}

		session.setAuthUsername(username);
		session.setState(SessionState.AUTH_PASSWORD);

		return SMTPResponse.create(StatusCode.STATUS_334, encodeBase64("Password:"));
	}
}