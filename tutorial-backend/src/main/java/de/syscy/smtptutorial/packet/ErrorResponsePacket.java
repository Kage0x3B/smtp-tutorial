package de.syscy.smtptutorial.packet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponsePacket {
	private String message;
}