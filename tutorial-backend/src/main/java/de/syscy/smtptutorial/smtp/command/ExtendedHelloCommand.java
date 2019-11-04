package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;

import java.util.LinkedList;

public class ExtendedHelloCommand extends SMTPCommand {
	ExtendedHelloCommand() {
		super("EHLO");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		session.setState(SessionState.GREETING);

		LinkedList<String> extensionList = new LinkedList<>();

		//Add the extended hello message before the extensions
		extensionList.add("Hi, welcome to the tutorial SMTP server! Here is a list of extensions I support:");

		extensionList.add("SIZE 1337");
		extensionList.add("AUTH LOGIN");

		if(!session.isEncrypted()) {
			extensionList.add("STARTTLS");
		}

		extensionList.add("HELP");
		//TODO: Add extensions? Auth? TLS?

		return SMTPResponse.ok(extensionList.toArray(new String[0]));
	}
}