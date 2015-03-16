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

First, start an ftp server somewhere on your computer. You can do it with python using

    python -m pyftpdlib

Compile and execute the rest bridge. It will try to connect to ``127.0.0.1`` at port ``2121``, with user ``anonymous``. :

    $ ant run
    Buildfile: /home/goudale/Cours/S8/car/tp2-rest/build.xml

    compile:
        [javac] Compiling 1 source file to /home/goudale/Cours/S8/car/tp2-rest/classes

    run:
        [java] Password ?

Now type a dump password, like ``plopplop``. Then the bridge tells you on which port it is listening.

        [java]
        [java] Listening on 8080

Open ``127.0.0.1:8080`` in your browser to list files under your ftp server.


Tests
-----

    ./test.sh

Will start the ftp server, and do some POST, GET and DELETE

Todo
----

An ``FtpClient`` class with the following methods :

  + ``bytes post(String path, bytes data)`` which create the file ``path`` with the data ``data``
  + Find a good name for this restbridge

