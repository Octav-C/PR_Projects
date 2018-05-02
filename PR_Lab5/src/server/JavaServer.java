package server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.*;


public class JavaServer {
    static List<String> commands = Arrays.asList("help", "hello", "random", "horoscope", "time");
    static String serverMessage = "";
    static List<String> parsedString;

    public static void main(String[] args) {
        int portNumber = 1234;
        OutputStream clientOut;
        PrintWriter pw;

        ServerSocket server = null;


        try {
            server = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true) {
            try {
                //Listent for a connection to be made to this socket
                //and accepts it. The method blocks until a connection is made
                System.out.println("Waiting for connect request...");
                Socket client = server.accept();

                System.out.println("Connection established...");
                String clientAddress = client.getInetAddress().getHostAddress();
                int port = client.getPort();
                System.out.println("Client addres: [" + clientAddress + "]\nCliet port: [" + port + "]");

                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String clientMessage = br.readLine();
                System.out.println("Mesage received: " + clientMessage);
                parsedString = parseString(clientMessage.toLowerCase());
                System.out.println(parsedString.get(0));
                switch (parsedString.get(0)) {
                    case "time":
                        if (parsedString.size() > 1) {
                            clientOut = client.getOutputStream();
                            pw = new PrintWriter(clientOut, true);
                            pw.println("Incorrect Format [/time]");

                        } else {
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            serverMessage = sdf.format(cal.getTime());
                            clientOut = client.getOutputStream();
                            pw = new PrintWriter(clientOut, true);
                            pw.println(serverMessage);
                        }

                        break;
                    case "horoscope":
                        String zodie = parsedString.get(1) + ".txt";
                        String content = readFile(zodie);
                        if (content == null || parsedString.size() > 2) {
                            clientOut = client.getOutputStream();
                            pw = new PrintWriter(clientOut, true);
                            pw.println("Incorrect Format [/horoscope sign]");
                        } else {
                            clientOut = client.getOutputStream();
                            pw = new PrintWriter(clientOut, true);
                            System.out.println(content);
                            pw.println(content);
                        }
                        break;

                    case "help":
                        serverMessage = "[/hello name] - Show the Name" + "\t" +
                                "[/help] - Show available commands" + "\t" +
                                "[/horoscope sign] - Horoscope for current date" + "\t" +
                                "[/random Number1 Number2] - Random a number between Number1 and Number2" + "\t" +
                                "[/time] - Show Current Time";



                        clientOut = client.getOutputStream();
                        pw = new PrintWriter(clientOut, true);
                        pw.println(serverMessage);
                        break;
                    case "random":
                        if (parsedString.size() == 3) {
                            if (isInteger(parsedString.get(1)) && isInteger(parsedString.get(2)) && (Integer.parseInt(parsedString.get(1)) <= Integer.parseInt(parsedString.get(2)))) {
                                Random r = new Random();
                                int high = Integer.parseInt(parsedString.get(2));
                                int low = Integer.parseInt(parsedString.get(1));
                                int result = r.nextInt(high - low + 1) + low;
                                serverMessage = String.valueOf(result);
                                clientOut = client.getOutputStream();
                                pw = new PrintWriter(clientOut, true);
                                pw.println(serverMessage);
                            } else {
                                serverMessage = "Incorrect Format [/random Number1 Number2]";
                                clientOut = client.getOutputStream();
                                pw = new PrintWriter(clientOut, true);
                                pw.println(serverMessage);
                            }
                        } else {
                            serverMessage = "Incorrect Format [/random Number1 Number2]";
                            clientOut = client.getOutputStream();
                            pw = new PrintWriter(clientOut, true);
                            pw.println(serverMessage);
                        }

                        break;
                    case "hello":
                        serverMessage = "";
                        for (int i = 1; i < parsedString.size(); i++) {
                            serverMessage += parsedString.get(i) + " ";
                        }
                        clientOut = client.getOutputStream();
                        pw = new PrintWriter(clientOut, true);
                        pw.println(serverMessage);
                        break;

                    default:
                        if (findSimilarCommands() == false) {
                            serverMessage = "Such command doesn't exist";
                            clientOut = client.getOutputStream();
                            pw = new PrintWriter(clientOut, true);
                            pw.println(serverMessage);
                        } else {
                            clientOut = client.getOutputStream();
                            pw = new PrintWriter(clientOut, true);
                            pw.println(serverMessage);
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<String> parseString(String sentence) {
        List<String> words = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getWordInstance();
        breakIterator.setText(sentence);
        int lastIndex = breakIterator.first();
        while (BreakIterator.DONE != lastIndex) {
            int firstIndex = lastIndex;
            lastIndex = breakIterator.next();
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(sentence.charAt(firstIndex))) {
                words.add(sentence.substring(firstIndex, lastIndex));
            }
        }
        return words;
    }

    public static String readFile(String filename) throws IOException {
        String content = null;
        File file = new File(filename); // For example, foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        System.out.println(content);
        return content;
    }

    public static boolean isInteger(Object object) {
        if (object instanceof Integer) {
            return true;
        } else {
            String string = object.toString();

            try {
                Integer.parseInt(string);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    public static boolean findSimilarCommands() {
        boolean found = false;
        serverMessage = "Ouch! \"/" + String.valueOf(parsedString.get(0)) + "\" is an invalid command.";
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).startsWith(String.valueOf(parsedString.get(0)))) {
                serverMessage += " Did you mean \"/" + commands.get(i) + "\" ?";
                found = true;
            }
        }
        return found;
    }

}





