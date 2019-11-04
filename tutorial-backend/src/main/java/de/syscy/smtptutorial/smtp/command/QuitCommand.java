package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.StatusCode;

public class QuitCommand extends SMTPCommand {
	QuitCommand() {
		super("QUIT");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		//TODO: Update frontend when quitting!

		return SMTPResponse.create(StatusCode.STATUS_221);
	}
}