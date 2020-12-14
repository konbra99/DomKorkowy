package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(7117);

		System.out.println("Waiting for client...");
		Socket socket = serverSocket.accept();
		System.out.println("Client connected");

		DataInputStream input = new DataInputStream(socket.getInputStream());
		DataOutputStream output = new DataOutputStream(socket.getOutputStream());

		while (true) {
			int num = input.readInt();
			output.writeInt(num*2);
		}
	}
}
