package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.account.TutorialAccount;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;
import de.syscy.smtptutorial.smtp.command.SMTPCommand;

import java.util.LinkedList;
import java.util.List;

public class ListUsersCommand extends SMTPCommand {
	ListUsersCommand() {
		super("LIST_USERS");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		List<String> responseLines = new LinkedList<>();

		TutorialBackend.getInstance().getAccountManager().getAllAccounts().stream().map(TutorialAccount::getName).forEach(responseLines::add);

		return SMTPResponse.ok(responseLines.toArray(new String[0]));
	}

}