package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.account.TutorialAccount;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;
import de.syscy.smtptutorial.smtp.StatusCode;

public class AuthCommand extends SMTPCommand {
	AuthCommand() {
		super("AUTH");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		if(session.getState() != SessionState.GREETING) {
			return SMTPResponse.invalidState();
		}

		if(parameter.trim().equalsIgnoreCase("SKIP")) {
			session.setAuthenticatedAccount(session.getAccount());
			session.setState(SessionState.GREETING);

			return SMTPResponse.create(StatusCode.STATUS_335);
		}

		if(!parameter.trim().equalsIgnoreCase("LOGIN")) {
			return SMTPResponse.notImplementedParameter("\"" + parameter + "\" authentication method is not implemented, only these specified by the EHLO command");
		}

		session.setState(SessionState.AUTH_USERNAME);

		return SMTPResponse.create(StatusCode.STATUS_334, encodeBase64("Username:"));
	}
}