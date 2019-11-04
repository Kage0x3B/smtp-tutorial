package de.syscy.smtptutorial.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import de.syscy.smtptutorial.TutorialBackend;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MainListener implements ConnectListener, DisconnectListener {
	private final TutorialBackend backend;

	@Override
	public void onConnect(SocketIOClient client) {
		TutorialBackend.LOGGER.info(client.getRemoteAddress().toString() + " connected!");
	}

	@Override
	public void onDisconnect(SocketIOClient client) {

	}
}