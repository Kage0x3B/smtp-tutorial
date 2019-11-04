package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.StatusCode;

public class InternalConnectCommand extends SMTPCommand {
	InternalConnectCommand() {
		super("_CONNECT");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		session.resetSession();

		return SMTPResponse.create(StatusCode.STATUS_220);
	}
}