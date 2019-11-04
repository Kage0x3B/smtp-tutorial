package de.syscy.smtptutorial.account;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
public class MailStorage {
	private final List<MailData> storedMails = new LinkedList<>();

	public void storeMail(MailData mailData) {
		storedMails.add(mailData);
	}
}