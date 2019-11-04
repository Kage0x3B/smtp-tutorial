package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.StatusCode;

public class ResetCommand extends SMTPCommand {
	ResetCommand() {
		super("RSET");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		session.resetSession();

		return SMTPResponse.ok();
	}
}