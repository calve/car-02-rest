package rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        if (this.isDir(path)){
            /* List the directory ! */
            FTPFile[] files = this.ftp.listFiles(path);
            String output = "";
            for (FTPFile file : files) {
                output += file.getName()+"\n";
            }
            return output.getBytes();
        }
        else {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            boolean completed = this.ftp.retrieveFile(path, outputStream);
            return outputStream.toByteArray();
        }
    }

    /* Post ``data`` at ``path``
     */
    /**
     * Create the file 'path' containing the data 'data'
     * @param path The name of the new file
     * @param data The file's datas
     * @return True if the file was created, False otherwise
     */
    public byte[] post(String path, InputStream data){
		boolean status = false;
		try {
                    if (this.isDir(path)){
                        status = this.ftp.makeDirectory(path);
                    }
                    else {
                        status = this.ftp.storeFile(path, data);
                    }
		} catch (IOException e) {
			System.out.println("Failed to post to ftp server");
		}
		return "".getBytes();
    }

    /**
     * Delete the file or directory 'path'
     * @param path The file or directory to delete
     * @return True if the file or directory was deleted, False otherwise
     */
    public byte[] delete(String path){
		boolean status = false;
		try {
			status = this.ftp.deleteFile(path);
		} catch (IOException e) {
			System.out.println("Failed to delete the file from the server");
		}
		return "".getBytes();
    }

    private boolean isDir(String path){
        return path.substring(path.length() - 1).charAt(0) == '/';
    }
}
