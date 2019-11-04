package de.syscy.smtptutorial.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.account.AccountManager;
import de.syscy.smtptutorial.account.MailData;
import de.syscy.smtptutorial.account.TutorialAccount;
import de.syscy.smtptutorial.packet.*;

import java.util.Optional;

public class AccountListener {
	private final TutorialBackend backend;
	private final AccountManager accountManager;

	public AccountListener(TutorialBackend backend) {
		this.backend = backend;
		this.accountManager = backend.getAccountManager();
	}

	public void onAccountCreate(SocketIOClient client, CreateAccountPacket data, AckRequest ackSender) throws Exception {
		Optional<TutorialAccount> accountOptional = accountManager.registerAccount(data.getName());

		if(accountOptional.isPresent()) {
			TutorialAccount account = accountOptional.get();

			account.setSocketClient(client);
			accountManager.storeSocketClientAssociation(client, account);

			syncMails(account, client);

			client.sendEvent("signin-response", new SignInResponsePacket(account.getName(), account.getPassword(), account.isAdmin()));
		} else {
			client.sendEvent("error-response", new ErrorResponsePacket("Could not create account, check if the name is valid and no one else has the same name."));
		}
	}

	public void onLogin(SocketIOClient client, LoginPacket data, AckRequest ackSender) throws Exception {
		Optional<TutorialAccount> accountOptional = accountManager.lookupAccount(data.getName());

		if(accountOptional.isPresent()) {
			TutorialAccount account = accountOptional.get();

			//TODO: Intentionally disabled password check here
			//if(account.getPassword().equals(data.getPassword())) {
				account.setSocketClient(client);
				accountManager.storeSocketClientAssociation(client, account);

				syncMails(account, client);

				client.sendEvent("signin-response", new SignInResponsePacket(account.getName(), account.getPassword(), account.isAdmin()));
			//} else {
			//	client.sendEvent("error-response", new ErrorResponsePacket("Could not sign in to your existing account, clear your cookies and reload the page."));
			//}
		} else {
			//Automatically create a new account if the login doesn't work and the account doesn't exist
			accountOptional = accountManager.registerAccount(data.getName());

			if(accountOptional.isPresent()) {
				TutorialAccount account = accountOptional.get();

				account.setSocketClient(client);
				accountManager.storeSocketClientAssociation(client, account);

				client.sendEvent("signin-response", new SignInResponsePacket(account.getName(), account.getPassword(), account.isAdmin()));
			} else {
				client.sendEvent("error-response", new ErrorResponsePacket("Could not create account, check if the name is valid and no one else has the same name."));
			}
		}
	}

	private void syncMails(TutorialAccount account, SocketIOClient client) {
		for(MailData mailData : account.getMailStorage().getStoredMails()) {
			ReceivedMailPacket packet = new ReceivedMailPacket(mailData.getSenderAddress(),mailData.getData(), mailData.getSubject());
			client.sendEvent("received-mail", packet);
		}
	}
}