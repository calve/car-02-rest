package rest;

public class FtpClient {
    /** Connects to the FTP serveur located at ip:port,
     * using (user, password) as credentials
     */

    private String ip;
    private int port;
    private String user;

    public FtpClient(String ip, int port, String user, String password){
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
