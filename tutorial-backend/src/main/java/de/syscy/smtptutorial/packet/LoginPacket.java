package de.syscy.smtptutorial.packet;

import lombok.Data;

@Data
public class LoginPacket {
	private String name;
	private String password;
}