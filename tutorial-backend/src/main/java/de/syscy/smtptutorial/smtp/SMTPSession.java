package de.syscy.smtptutorial.smtp;

import de.syscy.smtptutorial.account.TutorialAccount;
import de.syscy.smtptutorial.smtp.command.SMTPCommand;
import de.syscy.smtptutorial.smtp.command.SMTPCommandRegistry;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SMTPSession {
	private final TutorialAccount account;
	private SessionState state;

	private String authUsername;
	private String authPassword;
	private TutorialAccount authenticatedAccount;
	/**
	 * If STARTTLS was used
	 */
	private boolean encrypted;
	private String senderAddress;
	private final List<String> receiverAddresses = new ArrayList<>();
	private StringBuilder data;

	public SMTPResponse handleRequest(String line) {
		if(state == SessionState.DATA) {
			if(line.trim().equals(".")) {
				return executeCommand("_DATA_FINISH"); //Execute internal command to reuse existing code infrastructure
			} else {
				data.append(line).append(System.lineSeparator());

				return null;
			}
		}

		if(state == SessionState.AUTH_USERNAME) {
			return executeCommand("_AUTH_USERNAME " + line);
		}

		if(state == SessionState.AUTH_PASSWORD) {
			return executeCommand("_AUTH_PASSWORD " + line);
		}

		return executeCommand(line);
	}

	public SMTPResponse executeCommand(String commandLine) {
		String[] commandParts = commandLine.split(" ", 2);
		String commandName = commandParts[0];
		String parameters = commandParts.length > 1 ? commandParts[1] : "";

		SMTPCommand command = SMTPCommandRegistry.lookup(commandName);

		if(command == null) {
			return SMTPResponse.create(StatusCode.STATUS_500);
		}

		return command.execute(this, parameters);
	}

	public void resetSession() {
		this.state = SessionState.INITIAL;

		this.authUsername = null;
		this.authPassword = null;
		this.authenticatedAccount = null;

		this.encrypted = false;
		this.senderAddress = null;
		this.receiverAddresses.clear();
		this.data = new StringBuilder();
	}

	public boolean isAuthenticated() {
		return authenticatedAccount != null;
	}
}