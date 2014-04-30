import java.io.*;
import java.net.*;
import com.sun.nio.sctp.*;
import java.nio.*;
import java.util.Map;

public class SCTPClient implements Runnable {
	public static final int MESSAGE_SIZE = 1024;
	public static String server;
	public static int port;
	

	@SuppressWarnings("static-access")
	public SCTPClient(String server, int port) {

		this.server = server;
		this.port = port;
		Thread thread = new Thread(this, "SCTPClient");
		thread.start();
	}

	public void go(String server, int port, int inetPort) throws IOException, InterruptedException {
		// Buffer to hold messages in byte format
		ByteBuffer byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);
		String message = "";

		try {
			// int i = 0;
			// Create a socket address for server at net01 at port 5000
			SocketAddress socketAddress = new InetSocketAddress(server, port);
			// Open a channel. NOT SERVER CHANNEL
			SctpChannel sctpChannel = SctpChannel.open();
			// Bind the channel's socket to a local port. Again this is not a
			// server bind
			sctpChannel.bind(new InetSocketAddress(inetPort));
			// Connect the channel's socket to the remote server
			sctpChannel.connect(socketAddress);
			// Before sending messages add additional information about the
			// message
			MessageInfo messageInfo = MessageInfo.createOutgoing(null, 0);
			// convert the string message into bytes and put it in the byte
			// buffer
			int[] vector = Project1.getVector();
			System.out.println("Process-" +""+ Project1.getProcessID()
					+ "with  previous vector value: "
					+ ""+ Message.vectorToString(vector));
			message = Message.createMessage(vector, Project1.getProcessID());
			Project1.setVector(Message.makeVector(message));
			System.out.println("Process-" + ""+ Project1.getProcessID()
					+ "sending message to:  " + server + ":" + message);
			byteBuffer.clear();
			byteBuffer.put(message.getBytes());
			// Reset a pointer to point to the start of buffer
			byteBuffer.flip();
			// Send a message in the channel (byte format)
			sctpChannel.send(byteBuffer, messageInfo);
			byteBuffer.clear();
			Thread.sleep(1000);
			sctpChannel.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int localPort = 33158;
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(Map.Entry<String, Integer> connection: Project1.parameters.entrySet())
		{
			try {
				go(connection.getKey(), connection.getValue(), localPort);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Process- " + " " +Project1.getProcessID() +" " + "  is done");

	}
}
