package rest;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPFile;

public class FtpClient {
    /** Connects to the FTP serveur located at ip:port,
     * using (user, password) as credentials
     */

    private String ip;
    private int port;
    private String user;
    private org.apache.commons.net.ftp.FTPClient ftp; /* We do not import it to not mess with our own class name */

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
            System.out.println("Successfully logged in");
        }
        catch (IOException e){
            System.out.println("Failed to log to ftp server");
        }
    }

    /* Retrieve the content of ``path``
     */
    byte[] get(String path) throws IOException{
        System.out.println(path);
        if (path.substring(path.length() - 1).charAt(0) == '/'){
            /* List the directory ! */
            FTPFile[] files = this.ftp.listFiles(path);
            String output = "";
            for (FTPFile file : files) {
                output += file.getName()+"\n";
            }
            return output.getBytes();
        }
        else {
            System.out.println("not a dir");
            /* Here we should RECV */
        }
        return "".getBytes();
    }

    /* Post ``data`` at ``path``
     */
    byte[] post(String path, byte[] data){
        return "".getBytes();
    }
}
