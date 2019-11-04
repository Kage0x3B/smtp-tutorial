package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;

import java.util.LinkedList;
import java.util.List;

public class HelpCommand extends SMTPCommand {
	HelpCommand() {
		super("HELP");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		List<String> helpLines = new LinkedList<>();

		helpLines.add("You are currently in the " + session.getState().getName() + " state");
		switch(session.getState()) {
			case INITIAL:
				helpLines.add("Start by sending the HELO or EHLO command to \"greet\" the server and receive a response with information on the server");
				break;
			case GREETING:
				if(session.isAuthenticated()) {
					helpLines.add("You can now send the MAIL command to tell the server who you are");
				} else {
					helpLines.add("You need to log in with the AUTH command before sending messages");
				}

				if(!session.isEncrypted()) {
					helpLines.add("or you can use the STARTTLS command to encrypt all following commands and responses");
				}
				break;
			case AUTH_USERNAME:
				break;
			case AUTH_PASSWORD:
				break;
			case FROM:
				helpLines.add("You can now send the RCPT command to add one or more receivers to the mail");
				break;
			case RCPT:
				helpLines.add("You can now send the RCPT command to add more receivers to the mail or");
				helpLines.add("you can send the DATA command to start writing your mail");
				break;
			case DATA:
				//This should never occur as everything send in the data state is appended to the data buffer instead of
				//executed as a command
				break;
		}

		helpLines.add("-> or ask Moritz for help");

		return SMTPResponse.ok(helpLines.toArray(new String[0]));
	}
}