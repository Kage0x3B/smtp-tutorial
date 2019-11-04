package de.syscy.smtptutorial.packet;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateAccountPacket {
	private String name;
}