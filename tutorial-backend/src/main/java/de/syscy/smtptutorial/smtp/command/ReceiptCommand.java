package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;

public class ReceiptCommand extends SMTPCommand {
	ReceiptCommand() {
		super("RCPT");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		//Allow multiple receivers
		if(session.getState() != SessionState.FROM && session.getState() != SessionState.RCPT) {
			return SMTPResponse.invalidState();
		}

		if(!parameter.toUpperCase().contains("TO:")) {
			return SMTPResponse.parameterSyntaxError("Syntax error in parameters, missing a valid receiver address in the format \"TO: name@" + TutorialBackend.SERVER_DOMAIN_NAME + "\"");
		}

		String receiverAddress = parameter.substring(3).trim();

		SMTPResponse addressValidationResponse = validateMailAddress(receiverAddress);

		if(addressValidationResponse != null) {
			return addressValidationResponse;
		}

		session.setState(SessionState.RCPT);
		session.getReceiverAddresses().add(receiverAddress);

		return SMTPResponse.ok();
	}
}