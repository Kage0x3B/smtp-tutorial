package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;

import java.util.LinkedList;
import java.util.List;

public class MailCommand extends SMTPCommand {
	MailCommand() {
		super("MAIL");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		if(session.getState() != SessionState.GREETING) {
			return SMTPResponse.invalidState();
		}

		if(!session.isAuthenticated()) {
			return SMTPResponse.notAuthenticated();
		}

		if(!parameter.toUpperCase().contains("FROM:")) {
			return SMTPResponse.parameterSyntaxError("Syntax error in parameters, missing a valid sender address in the format \"FROM: name@" + TutorialBackend.SERVER_DOMAIN_NAME + "\"");
		}

		String fromAddress = parameter.substring(5).trim();

		SMTPResponse addressValidationResponse = validateMailAddress(fromAddress);

		if(addressValidationResponse != null) {
			return addressValidationResponse;
		}

		session.setState(SessionState.FROM);
		session.setSenderAddress(fromAddress);

		return SMTPResponse.ok();
	}
}