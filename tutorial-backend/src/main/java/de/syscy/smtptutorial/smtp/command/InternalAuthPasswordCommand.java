package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.account.TutorialAccount;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.SessionState;
import de.syscy.smtptutorial.smtp.StatusCode;

import java.util.Optional;

public class InternalAuthPasswordCommand extends SMTPCommand {
	InternalAuthPasswordCommand() {
		super("_AUTH_PASSWORD");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		String password;

		try {
			password = decodeBase64(parameter);
		} catch(IllegalArgumentException ex) {
			return SMTPResponse.create(StatusCode.STATUS_501, "Syntax error in parameters, invalid base64 encoded password");
		}

		session.setAuthPassword(password);
		session.setState(SessionState.GREETING);

		Optional<TutorialAccount> accountOptional = TutorialBackend.getInstance().getAccountManager().lookupAccount(session.getAuthUsername());

		if(!accountOptional.isPresent()) {
			return SMTPResponse.create(StatusCode.STATUS_535);
		}

		TutorialAccount account = accountOptional.get();

		//TODO: Intentionally bypassing the password check here!! (the rest was not fully implemented)
		/*if(!account.getPassword().equalsIgnoreCase(password)) {
			return SMTPResponse.create(StatusCode.STATUS_535);
		}*/

		session.setAuthenticatedAccount(account);
		session.setState(SessionState.GREETING);

		return SMTPResponse.create(StatusCode.STATUS_335);
	}
}