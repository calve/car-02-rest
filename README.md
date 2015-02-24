README
======

Architecture
------------

```
                +-----------------------------+
                |       REST Bridge           |
                |                             |
+-----------+   +--------------++-------------+  +----------+
|HTTPBrowser|   | HTTP Serveur || FTP Client  |  |FTP Server|
+-----------+   +--------------++-------------+  +----------+
     |  GET path/to/r  |               |             |
     |---------------->|               |             |
     |                 |               |             |
     |                 |-------------->|             |
     |                 |               |  USER, PASS |
     |                 |               |------------>|
     |                 |               |             |
     |                 |               |<------------|
     |                 |               |             |
     |                 |               |  RETR       |
     |                 |               |------------>|
     |                 |               |             |
     |                 |               |<------------|
     |                 |               |   0101011   |
     |                 |<--------------|             |
     |                 |    0101011    |             |
     |<----------------|               |             |
     |   0101011       |               |             |
     |                 |               |             |
     |                 |               |             |
     V                 V               V             V

```


Usage
-----

Compile and execute the rest bridge :

    ant run


Todo
----

An ``FtpClient`` class with the following methods :

  + ``FtpClient(String ip, int port, String user, String password)`` which connect to an FtpServer located at ``ip:port`` with user ``user`` and password ``password``
  + ``bytes get(String path)`` which retrieves the content of a directory or a file
  + ``bytes post(String path, bytes data)`` which create the file ``path`` with the data ``data``
  + Find a good name for this restbridge
