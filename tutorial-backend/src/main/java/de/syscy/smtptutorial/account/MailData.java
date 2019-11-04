package de.syscy.smtptutorial.account;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MailData implements Comparable<MailData> {
	private final String senderAddress;
	private final List<String> receiverAddresses;
	private final String data;

	private String subject;

	private final LocalDateTime createdTime = LocalDateTime.now();

	public MailData(String senderAddress, List<String> receiverAddresses, String data) {
		this.senderAddress = senderAddress;
		this.receiverAddresses = receiverAddresses;
		this.data = data;

		this.subject = "Unknown";

		for(String line : data.split("\\r?\\n")) {
			if(line.trim().toLowerCase().startsWith("subject:")) {
				this.subject = line.trim().substring(8).trim();
			}
		}
	}

	@Override
	public int compareTo(MailData o) {
		return createdTime.compareTo(o.createdTime);
	}
}