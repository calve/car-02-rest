package rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import rest.FtpClient;

/**
 * Front end classes. Listen to incoming connexion, dispatch them to the FTP client
 */
public class RestBridge {
    public static final int LISTENING_PORT = 8080;
    private String ip;
    private int port;
    private String user;
    private String password;

    public RestBridge(String ip, int port, String user) throws Exception {
        this.ip = ip;
        this.port = port;
        this.user = user;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Password ? ");
        password = scanner.next();
        this.password = password;
        System.out.println();
        System.out.println("Listening on "+LISTENING_PORT);
        HttpServer server = HttpServer.create(new InetSocketAddress(LISTENING_PORT), 0);
        server.createContext("/", new FtpHandler(this.ip, this.port, this.user, this.password));
        server.setExecutor(null);
        server.start();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.out.println("Usage : restclient ip port username");
            return;
        }
        RestBridge restBrigde = new RestBridge(args[0], Integer.parseInt(args[1]), args[2]);
    }

    /**
     * Handles one HTTP requests
     */
    static class FtpHandler implements HttpHandler {
        private String ip;
        private int port;
        private String user;
        private String password;

        public FtpHandler(String ip, int port, String user, String password){
            this.ip = ip;
            this.port = port;
            this.user = user;
            this.password = password;
        }

        /* Handles one HttpExchange.
         * Should instanciate a FtpClient using the RequestURI as action,
         * and output the response of that FtpClient
         */
        public void handle(HttpExchange t) throws IOException {
            String httpVerb = t.getRequestMethod();
            String uri = ""+t.getRequestURI(); /* Bizarre : fails to compile without the empty string */
            FtpClient ftpClient = new FtpClient(this.ip, this.port, this.user, this.password);

            byte[] response = String.format("This is the response for %s (%s)", uri, httpVerb).getBytes();
            try {
                switch (httpVerb){
                case "GET":
                    response = ftpClient.get(uri);
                    break;
                case "POST":
                    response = ftpClient.post(uri, "".getBytes());
                    break;
                case "DELETE":
					response = ftpClient.delete(uri);
					break;
                }
                t.sendResponseHeaders(200, response.length);
            }
            catch (IOException e) {
                t.sendResponseHeaders(500, response.length);
                response = "Internal error. Maybe the REST bridge is not connected to an FTP server ?".getBytes();
            }
            /* Log this request on the server */
            System.out.println(httpVerb + ": "+uri);
            OutputStream os = t.getResponseBody();
            os.write(response);
            os.close();
        }
    }
}
