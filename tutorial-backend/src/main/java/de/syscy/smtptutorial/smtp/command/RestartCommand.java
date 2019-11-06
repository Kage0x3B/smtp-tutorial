package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;
import de.syscy.smtptutorial.smtp.StatusCode;

public class RestartCommand extends SMTPCommand {
	RestartCommand() {
		super("RESTART");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		if(parameter.toLowerCase().contains("doit")) {
			TutorialBackend.getInstance().setRunning(false);

			return SMTPResponse.create(StatusCode.STATUS_250);
		}

		return SMTPResponse.create(StatusCode.STATUS_502);
	}
}