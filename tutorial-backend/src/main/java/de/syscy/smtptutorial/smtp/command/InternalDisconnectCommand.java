package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;

public class InternalDisconnectCommand extends SMTPCommand {
	InternalDisconnectCommand() {
		super("_DISCONNECT");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		return null;
	}
}