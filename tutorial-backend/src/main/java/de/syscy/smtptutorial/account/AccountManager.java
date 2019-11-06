package de.syscy.smtptutorial.account;

import com.corundumstudio.socketio.SocketIOClient;
import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.util.PasswordUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountManager {
	private Map<String, TutorialAccount> accountMap = new HashMap<>();
	private Map<SocketIOClient, TutorialAccount> socketClientAccountLookup = new HashMap<>();

	/**
	 * Registers a new account
	 * @param name identifier for this account
	 * @return the created account or null if an error occured, most likely an invalid or already existing name
	 */
	public Optional<TutorialAccount> registerAccount(String name, boolean forceAuth) {
		if(lookupAccount(name).isPresent() && forceAuth) {
			TutorialBackend.LOGGER.info("Force auth'd into " + name);

			return lookupAccount(name);
		}

		if(name == null || lookupAccount(name).isPresent()) {
			return Optional.empty();
		}

		String password = PasswordUtil.generatePassword();

		TutorialAccount account = new TutorialAccount(name, password, name.trim().equalsIgnoreCase("moritz"));
		accountMap.put(name.toLowerCase(), account);

		TutorialBackend.LOGGER.info("Created account " + name + " (password: " + password + ")");

		return Optional.of(account);
	}

	public void storeSocketClientAssociation(SocketIOClient client, TutorialAccount account) {
		socketClientAccountLookup.put(client, account);
	}

	public Optional<TutorialAccount> lookupSocketClientAccount(SocketIOClient client) {
		return Optional.ofNullable(socketClientAccountLookup.get(client));
	}

	public Optional<TutorialAccount> lookupAddress(String mailAddress) {
		if(mailAddress == null || !mailAddress.contains("@")) {
			return Optional.empty();
		}

		String name = mailAddress.substring(0, mailAddress.indexOf('@')).trim();

		return lookupAccount(name);
	}

	public Optional<TutorialAccount> lookupAccount(String name) {
		if(name == null) {
			return Optional.empty();
		}

		return Optional.ofNullable(accountMap.get(name.toLowerCase()));
	}

	public Collection<TutorialAccount> getAllAccounts() {
		return accountMap.values();
	}
}