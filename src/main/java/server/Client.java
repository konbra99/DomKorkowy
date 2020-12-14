package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client{
	Scanner scanner = new Scanner(System.in);
	Socket socket = new Socket(InetAddress.getLocalHost(), 7117);

	DataInputStream input = new DataInputStream(socket.getInputStream());
	DataOutputStream output = new DataOutputStream(socket.getOutputStream());

	public Client() throws IOException {
		while(true) {
			System.out.print("client: ");
			int num = scanner.nextInt();
			System.out.println();

			output.writeInt(num);
			num = input.readInt();
			System.out.println("server: " + num);
		}
	}

	public static void main(String[] args) {
		try {
			new Client();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}