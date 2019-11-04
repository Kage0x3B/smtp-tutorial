package de.syscy.smtptutorial.smtp;

import de.syscy.smtptutorial.account.TutorialAccount;

import java.util.HashMap;
import java.util.Map;

public class SMTPServer {
	private final Map<TutorialAccount, SMTPSession> sessionMap = new HashMap<>();

	public SMTPSession getSession(TutorialAccount account) {
		if(!sessionMap.containsKey(account)) {
			sessionMap.put(account, new SMTPSession(account));
		}

		return sessionMap.get(account);
	}
}