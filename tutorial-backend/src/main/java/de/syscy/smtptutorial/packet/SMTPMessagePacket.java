package de.syscy.smtptutorial.packet;

import lombok.Data;

@Data
public class SMTPMessagePacket {
	private String message;
}