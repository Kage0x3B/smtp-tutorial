package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;

public class NoopCommand extends SMTPCommand {
	NoopCommand() {
		super("NOOP");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		return SMTPResponse.ok();
	}
}