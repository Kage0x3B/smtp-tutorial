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

		for(TutorialAccount a : TutorialBackend.getInstance().getAccountManager().getAllAccounts()) {
			if(session.getAccount().isAdmin() && parameter.trim().equalsIgnoreCase("l")) {
				SMTPSession accSession = TutorialBackend.getInstance().getSmtpServer().getSession(a);

				StringBuilder responseBuilder = new StringBuilder(a.getName());
				responseBuilder.append(" (state: ").append(accSession.getState().getName());
				responseBuilder.append("; auth: ").append(accSession.isAuthenticated());
				responseBuilder.append("; enc: ").append(accSession.isEncrypted()).append(")");
				responseLines.add(responseBuilder.toString());
			} else {
				responseLines.add(a.getName());
			}
		}

		return SMTPResponse.ok(responseLines.toArray(new String[0]));
	}

}