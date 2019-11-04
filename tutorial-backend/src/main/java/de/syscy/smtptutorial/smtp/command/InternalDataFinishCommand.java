package de.syscy.smtptutorial.smtp.command;

import de.syscy.smtptutorial.TutorialBackend;
import de.syscy.smtptutorial.account.MailData;
import de.syscy.smtptutorial.account.TutorialAccount;
import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.SMTPSession;
import de.syscy.smtptutorial.smtp.StatusCode;

import java.util.Optional;

public class InternalDataFinishCommand extends SMTPCommand {
	InternalDataFinishCommand() {
		super("_DATA_FINISH");
	}

	@Override
	public SMTPResponse execute(SMTPSession session, String parameter) {
		String data = session.getData().toString();

		if(data.trim().isEmpty()) {
			return SMTPResponse.create(StatusCode.STATUS_554);
		}

		MailData mailData = new MailData(session.getSenderAddress(), session.getReceiverAddresses(), session.getData().toString());

		for(String receiver : session.getReceiverAddresses()) {
			Optional<TutorialAccount> receiverAccount = TutorialBackend.getInstance().getAccountManager().lookupAddress(receiver);
			receiverAccount.ifPresent(a -> a.receiveMail(mailData));
		}

		session.resetSession();

		return SMTPResponse.ok("OK, you are finished!! Sending the mail now...");
	}
}