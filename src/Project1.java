import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;

//import com.sun.java_cup.internal.runtime.Scanner;

@SuppressWarnings("unused")
public class Project1 {
	public static int[] processes;
	public static int processID;
	public static SCTPClient sctpClient;
	public static SCTPServer sctpServer;
	public static int numberProcesses;

	// static ArrayList<Integer> ports = new ArrayList<>();
	// static ArrayList<String> hostnames = new ArrayList<String>();
	public static HashMap<String, Integer> parameters = new HashMap<String, Integer>();
	

	public Project1(int pId, String host, int port, String filePath)
			throws IOException {
		setProcessID(pId);
		parseFile(filePath);
		sctpClient = new SCTPClient(host, port);
		sctpServer = new SCTPServer(host, port);
		processes = new int[numberProcesses];

	}

	public static int getProcessID() {
		return processID;
	}

	@SuppressWarnings("static-access")
	public void setProcessID(int processID) {
		this.processID = processID;
	}

	public static void parseFile(String file) throws IOException {
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(file));
			String line = buffer.readLine();
			String process = buffer.readLine();
			String[] procesToken = process.split(" ");
			numberProcesses = Integer.parseInt(procesToken[0]);

			System.out.println(procesToken[0]);

			String unusedLine = buffer.readLine();
			String currentLine;
			String[] nodeParameter = null;
			while ((currentLine = buffer.readLine()) != null) {
				nodeParameter = currentLine.split(" ");
				int index = Integer.parseInt(nodeParameter[0]);
				String hostnames = nodeParameter[1];
				int portNumbers = Integer.parseInt(nodeParameter[2]);
				if (index == getProcessID()) {
					continue;
				} else {
					if (!parameters.containsKey(hostnames)) {
						parameters.put(hostnames, portNumbers);
					}
				}

			}
			buffer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) throws IOException {
		Project1 parent = new Project1(Integer.parseInt(args[0]), args[1],
				Integer.parseInt(args[2]), args[3]);

	}

	public static int[] getVector() {
		// TODO Auto-generated method stub
		return processes;
	}

	public static void setVector(int[] newVector) {
		for (int i = 0; i < newVector.length; i++) {
			processes[i] = newVector[i];
		}
	}

}
