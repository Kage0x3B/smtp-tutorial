package de.syscy.smtptutorial.packet;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceivedMailPacket {
	private final String senderAddress;
	private final String data;

	private final String subject;

	private final LocalDateTime createdTime = LocalDateTime.now();
}