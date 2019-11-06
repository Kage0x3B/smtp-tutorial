package de.syscy.smtptutorial.smtp.command;

import java.util.HashMap;
import java.util.Map;

public class SMTPCommandRegistry {
	private static final Map<String, SMTPCommand> commandMap = new HashMap<>();

	static {
		//Internal commands. These are not real SMTP commands, just used internally to reuse the existing command system
		//for similar events like a connection state change or when the "." for ending data input which is similar to a command is sent
		register(new InternalConnectCommand());
		register(new InternalDisconnectCommand());
		register(new InternalAuthUsernameCommand());
		register(new InternalAuthPasswordCommand());
		register(new InternalDataFinishCommand());

		//Utility commands
		register(new ListUsersCommand());

		register(new HelloCommand());
		register(new ExtendedHelloCommand());
		register(new HelpCommand());
		register(new StartTLSCommand());
		register(new AuthCommand());
		register(new MailCommand());
		register(new ReceiptCommand());
		register(new DataCommand());
		register(new ResetCommand());
		register(new QuitCommand());
		register(new NoopCommand());

		//Secret commands for testing
		register(new RestartCommand());
	}

	private static void register(SMTPCommand command) {
		commandMap.put(command.getName().toUpperCase(), command);
	}

	public static SMTPCommand lookup(String commandName) {
		return commandMap.get(commandName.toUpperCase());
	}
}