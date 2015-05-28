import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable {

    private static final String         // server diagnostic messages
            WAITING = "Waiting for a new connection...",
            RECEIVED_REQUEST = "Received request: ",
            OUTPUT_SENT = "Output sent to client from thread ",
            END_OF_SESSION = "Session Closed: ",
            PROCESS_DESTROYED = "Process destroyed: ",
            ERROR_IN_REQUEST = "ERROR in client request";
    private int connectionNumber, threadNumber, port;    // Track number of connections/threads
    private Socket connectionSocket;

    public ServerThread(int threadNumber, int connectionNumber, Socket connectionSocket) {
        this.threadNumber = threadNumber;
        this.connectionSocket = connectionSocket;
        this.connectionNumber = connectionNumber;
        this.port = connectionSocket.getLocalPort();
    }

    public void run() {
        String commands[] = {   // Array of executables with most verbose(?) arguments
                "date",
                "uptime",
                "free -l",
                "netstat -v",
                "who -a",
                "ps aux"
        };
        BufferedReader inFromClient = null;
        DataOutputStream outToClient = null;
        String outputToClient, clientRequest;
        int clientRequestValue;

        try {
            // establish input/output streams
            inFromClient = new BufferedReader(
                    new InputStreamReader(connectionSocket.getInputStream()));
            outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            // get command from the client
            clientRequest = inFromClient.readLine();
            System.out.println("Thread " + threadNumber + " on Port " + port + "  "
                    + RECEIVED_REQUEST + clientRequest);

            try {       // First make sure the request is an integer
                clientRequestValue = Integer.parseInt(clientRequest);
            } catch (NumberFormatException e) {
                clientRequestValue = -1;
            }

            // get output from the requested process if request is between (0 and 7)
            if (clientRequestValue > 0 && clientRequestValue < 7) {
                outputToClient = runProcess(commands[clientRequestValue - 1]);

                // return output from process to the client
                outToClient.writeBytes(outputToClient + '\n');
                System.out.println("Thread " + threadNumber + " on Port " + port + "  " + OUTPUT_SENT);
                System.out.println("Thread " + threadNumber + " on Port " + port + "  " +
                        END_OF_SESSION + connectionNumber);
            } else {
                // return ERROR to the client
                outToClient.writeBytes("Thread " + threadNumber + " " +
                        ERROR_IN_REQUEST + '\n');
                System.out.println("Thread " + threadNumber + " on Port " + port + "  " + ERROR_IN_REQUEST);
                System.out.println("Thread " + threadNumber + " on Port " + port + "  " + OUTPUT_SENT);
                System.out.println("Thread " + threadNumber + " on Port " + port + "  " +
                        END_OF_SESSION + connectionNumber);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {                     // Clean up
            if (inFromClient != null) {
                try {
                    inFromClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outToClient != null) {
                try {
                    outToClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                connectionSocket.close();
                System.out.println("Threads remaining: " + --Server.threadCount);
                if ((Server.threadCount) == 0) {
                    System.out.println(WAITING);    // So WAITING is last line displayed
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Runs the Linux command passed in and returns a single string with the output.
     * @param cmd Shell command to be executed
     * @return String, return from the command
     */
    private String runProcess(String cmd) {
        Process process = null;
        BufferedReader stdoutBR = null;
        String stdoutNextLine = null, outputToClient = "";

        try {
            // execute the command
            process = Runtime.getRuntime().exec(cmd);
            // buffer to catch the stdout from the process
            stdoutBR = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // read in each line of the process's stdout
            while ((stdoutNextLine = stdoutBR.readLine()) != null) {
                // append each line to final output for client
                outputToClient += stdoutNextLine + "\n";
                // Uncomment to display process output
                // System.out.println(RECEIVED_LINE + stdoutNextLine);
            }
            outputToClient += "\0\n";

        } catch (IOException e) {         // uh oh - error
            e.printStackTrace();
        } finally {                     // clean up
            if (stdoutBR != null)
                try {
                    stdoutBR.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (process != null) {
                process.destroy();
                System.out.println("Thread " + threadNumber + " on Port " + port + "  " + PROCESS_DESTROYED + cmd);
            }
        }
        return outputToClient;
    }
}
