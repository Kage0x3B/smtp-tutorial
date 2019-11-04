package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequiredArgsConstructor
public abstract class SMTPCommand {
	private final @Getter String name;

	public abstract SMTPResponse execute(SMTPSession session, String parameter);

	/**
	 * Validates a mail address by checking for the common format and the correct domain ({@link TutorialBackend#SERVER_DOMAIN_NAME})
	 * @param mailAddress address to check
	 * @return an error smtp response if invalid or null if valid
	 */
	protected SMTPResponse validateMailAddress(String mailAddress) {
		mailAddress = mailAddress.trim();

		if(!mailAddress.contains("@")) {
			return SMTPResponse.parameterSyntaxError("Syntax error in parameters, the mail address is not valid, should be \"name@" + TutorialBackend.SERVER_DOMAIN_NAME + "\"");
		}

		/*if(!mailAddress.toLowerCase().endsWith(TutorialBackend.SERVER_DOMAIN_NAME)) {
			return SMTPResponse.parameterSyntaxError("Syntax error in parameters, the mail address is not valid, should be \"name@" + TutorialBackend.SERVER_DOMAIN_NAME + "\" (invalid domain, you can only use mail addresses of users of the tutorial)");
		}*/

		if(mailAddress.substring(0, mailAddress.indexOf('@')).length() < 1) {
			return SMTPResponse.parameterSyntaxError("Syntax error in parameters, the mail address is not valid, should be \"name@" + TutorialBackend.SERVER_DOMAIN_NAME + "\" (empty name!)");
		}

		if(!TutorialBackend.getInstance().getAccountManager().lookupAddress(mailAddress).isPresent()) {
			return SMTPResponse.parameterSyntaxError("Syntax error in parameters, the mail address does not belong to a registered user (check the address for typos?)");
		}

		return null;
	}

	protected String encodeBase64(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
	}

	protected String decodeBase64(String input) throws IllegalArgumentException {
		return new String(Base64.getDecoder().decode(input), StandardCharsets.UTF_8);
	}
}