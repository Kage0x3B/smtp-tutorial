package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;
import de.syscy.smtptutorial.smtp.StatusCode;

import java.util.LinkedList;
import java.util.List;

public class DataCommand extends SMTPCommand {
	DataCommand() {
		super("DATA");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		if(session.getState() != SessionState.RCPT) {
			return SMTPResponse.invalidState();
		}

		session.setState(SessionState.DATA);

		return SMTPResponse.create(StatusCode.STATUS_354);
	}
}