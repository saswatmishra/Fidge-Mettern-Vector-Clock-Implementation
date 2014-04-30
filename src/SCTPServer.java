//import java.io.*;
import java.net.*;
import com.sun.nio.sctp.*;
import java.nio.*;
//import java.util.ArrayList;

//import sun.awt.windows.ThemeReader;

public class SCTPServer implements Runnable {
	public String hostName;
	public int portNumber;
	public static final int MESSAGE_SIZE = 1024;

	public SCTPServer(String hostName, int portNumber) {
		// TODO Auto-generated constructor stub
		this.hostName = hostName;
		this.portNumber = portNumber;
		Thread thread = new Thread(this, "SCTPServer");
		thread.start();

	}

	public String byteToString(ByteBuffer byteBuffer) {
		byteBuffer.position(0);
		byteBuffer.limit(MESSAGE_SIZE);
		byte[] bufArr = new byte[byteBuffer.remaining()];
		byteBuffer.get(bufArr);
		return new String(bufArr);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			int count = Project1.numberProcesses - 1;
			ByteBuffer byteBuffer = ByteBuffer.allocate(MESSAGE_SIZE);
			String message = "";
			SctpServerChannel sctpServerChannel = SctpServerChannel.open();
			InetSocketAddress serverAddr = new InetSocketAddress(hostName,
					portNumber);
			// Bind the channel's socket to the server in the current machine at
			// port 5000
			System.out.println("Process-" + "" + serverAddr.getPort() + "ProcessName: " + serverAddr.getHostName()+ "running");
			sctpServerChannel.bind(serverAddr);
			// Server goes into a perma nent loop accepting connections from
			// clients
			while (true) {
				if (count <= 0) {
					Thread.sleep(15000);
					System.out.println("Process-" +""+ Project1.getProcessID()
							+ "has final vector: "
							+""+ Message.vectorToString(Project1.getVector()));
					break;
				}

				// Listen for a connection to be made to this socket and accept
				// it
				// The method blocks until a connection is made
				// Returns a new SCTPChannel between the server and client
				SctpChannel sctpChannel = sctpServerChannel.accept();
				// Receive message in the channel (byte format) and store it in
				// buf
				// Note: Actual message is in byre format stored in buf
				// MessageInfo has additional details of the message
				@SuppressWarnings("unused")
				MessageInfo messageInfo = sctpChannel.receive(byteBuffer, null,
						null);
				int[] mes = Project1.getVector();

				System.out.println("Process-" +""+ Project1.getProcessID()
						+ "has vector before processing: "
						+""+ Message.vectorToString(mes));
				// Just seeing what gets stored in messageInfo
				// System.out.println(messageInfo);
				// Converting bytes to string. This looks nastier than in TCP
				// So better use a function call to write once and forget it :)
				message = byteToString(byteBuffer);
				System.out.println("Message received from-"
						+""+ sctpChannel.getRemoteAddresses() + ":" + message);

				// Finally the actual message
				Message updateVector = new Message(message);
				int[] updatedVector = updateVector.receiveAction(
						Project1.getVector(), updateVector.newVector,
						Project1.getProcessID());
				Project1.setVector(updatedVector);
				System.out.println("After receiving vector value is:"
						+ ""+Message.vectorToString(Project1.getVector()));
				System.out.println(message);
				count--;
				byteBuffer.clear();
				sctpChannel.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}