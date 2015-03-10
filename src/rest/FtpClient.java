package rest;

import java.io.IOException;
import java.net.SocketException;

public class FtpClient {
    /** Connects to the FTP serveur located at ip:port,
     * using (user, password) as credentials
     */

    private String ip;
    private int port;
    private String user;
    private org.apache.commons.net.ftp.FTPClient ftp;

    public FtpClient(String ip, int port, String user, String password){
        this.ftp = new org.apache.commons.net.ftp.FTPClient();
        try {
            this.ftp.connect(ip, port);
        }
        catch (SocketException e){
            System.out.println("Failed to connect to ftp server");
        }
        catch (IOException e){
            System.out.println("Failed to log to ftp server");
        }
        try {
            this.ftp.login(user, password);
        }
        catch (IOException e){
            System.out.println("Failed to log to ftp server");
        }
    }

    /* Retrieve the content of ``path``
     */
    byte[] get(String path){
        return "".getBytes();
    }

    /* Post ``data`` at ``path``
     */
    byte[] post(String path, byte[] data){
        return "".getBytes();
    }
}
