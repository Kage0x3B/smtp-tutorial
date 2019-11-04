package de.syscy.smtptutorial.packet;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignInResponsePacket {
	private String name;
	private String password;
	private boolean admin;
}