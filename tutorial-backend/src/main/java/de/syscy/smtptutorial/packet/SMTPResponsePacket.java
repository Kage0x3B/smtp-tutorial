package de.syscy.smtptutorial.packet;

import de.syscy.smtptutorial.smtp.SMTPResponse;
import de.syscy.smtptutorial.smtp.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SMTPResponsePacket {
	private int statusCode;
	private String message;

	public SMTPResponsePacket(StatusCode statusCode, String responseLine) {
		this.statusCode = statusCode.getCode();
		this.message = responseLine;
	}
}