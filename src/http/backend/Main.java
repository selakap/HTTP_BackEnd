package http.backend;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try {
            //curl http://localhost:7000
            // listening the port.
            ServerSocket ss = new ServerSocket(7000);
            // infinite loop, waiting for a request
            for (;;) {
                // accept when there is a request
                Socket client1 = ss.accept();
                //Socket client2 = ss.accept();



                // Get input and output streams to talk to the client
                BufferedReader in = new BufferedReader(new InputStreamReader(client1.getInputStream()));
                System.out.println("test");

/*                int b = -1;
                while ((b = client.getInputStream().read()) != 1) {
                    System.out.print((char)b);
                }*/

/*                StringBuilder result = new StringBuilder();
                do {
                    result.append((char) client2.getInputStream().read());
                } while (client2.getInputStream().available() > 0);*/


                char[] buf = new char[10];
                StringBuilder outt = new StringBuilder();
                while (true) {
                    try{
                        int read = in.read(buf);
                        outt.append(buf, 0, read);
                        //if (read < 100)
                        break;
                    }

                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                PrintWriter out = new PrintWriter(client1.getOutputStream());

                // Start sending our reply, using the HTTP 1.1 protocol
                //Headers
                out.print("HTTP/1.1 200 OK\r\n");
                out.print("Content-Type: application/json\r\n");
                //out.print("Content-Length: 11\r\n");
                out.print("\r\n");
                // headers

                // Now, read the HTTP request from the client, and send it
                // right back to the client as part of the body of our
                // response. The client doesn't disconnect, so we never get
                // an EOF. It does sends an empty line at the end of the
                // headers, though. So when we see the empty line, we stop
                // reading. This means we don't mirror the contents of POST
                // requests, for example. Note that the readLine() method
                // works with Unix, Windows, and Mac line terminators.

                String body = "{\"dfc\":\"rr\"}";
                out.print(body);

                // closing the input stream, output streams and socket
                out.close();
                in.close();
                client1.close();
                //client2.close();
            }
        }
        // If anything goes wrong, print an error message
        catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java HttpMirror <port>");
        }    }
}

