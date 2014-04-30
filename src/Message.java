public class Message {
	int[] oldVector;
	int[] newVector;

	public Message(String message) {
		oldVector = Project1.getVector();
		newVector = makeVector(message);
	}

	public static int[] makeVector(String message) {
		// TODO Auto-generated method stub
		String[] entry = message.split(" ");
		int length = Project1.numberProcesses;
		int[] vector = new int[length];
		int j = 0;
		for (int i = 0; i < entry.length; i++) {
			if (entry[i].equals("[")) {
				continue;
			}
			if (entry[i].contains("]")) {
				break;
			}
			vector[j] = Integer.parseInt(entry[i]);
			j++;
		}
		for (int i = 0; i < vector.length; i++) {

		}

		return vector;

	}

	public int[] receiveAction(int[] vector1, int[] vector2, int processID) {
		if (vector1.length != vector2.length) {
			System.err.println("Not equal");
		}
		for (int i = 0; i < vector1.length; i++) {
			if (i == processID) {
				continue;
			} else {
				if (vector1[i] <= vector2[i]) {
					vector1[i] = vector2[i];
				}
			}

		}
		vector1[processID]++;
		return vector1;

	}

	public static String vectorToString(int[] vector) {
		// TODO Auto-generated method stub
		String vectorString = "[ ";
		for (int i = 0; i < vector.length; i++) {
			vectorString += vector[i] + " ";
		}
		vectorString += "]";
		return vectorString;
	}

	public static String createMessage(int[] vector, int process) {
		vector[process]++;
		String vec = vectorToString(vector);
		return vec;
	}

}
