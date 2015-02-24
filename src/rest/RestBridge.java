package rest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Front end classes. Listen to incoming connexion, dispatch them to the FTP client
 */
public class RestBridge {
    public static final int LISTENING_PORT = 8080;

    public static void main(String[] args) throws Exception {
        System.out.println("I am a rest bridge, which unfortunately does nothing yet");
        HttpServer server = HttpServer.create(new InetSocketAddress(LISTENING_PORT), 0);
        server.createContext("/", new FtpHandler());
        server.setExecutor(null);
        server.start();
    }

    /**
     * Handles one HTTP requests
     */
    static class FtpHandler implements HttpHandler {

        /* Handles one HttpExchange.
         * Should instanciate a FtpClient using the RequestURI as parameters,
         * and output the response of that FtpClient
         */
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response for "+t.getRequestURI();
            /* Log this request on the server */
            System.out.println(t.getRequestMethod() + ": "+t.getRequestURI());
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
