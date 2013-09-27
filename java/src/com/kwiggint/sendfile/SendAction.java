package com.kwiggint.sendfile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/** Action that sends a file across a Socket. */
public class SendAction {
  private static final int SOCKET_TIMEOUT_MILLIS = 30000;

  /**
   * Sends the file referenced by this SendAction
   *
   * @return true if file was send successfully in its entirety.
   */
  public static boolean send(InetSocketAddress socketAddress, LocalFile file) {
    try {
      ServerSocket serverSocket = new ServerSocket(socketAddress.getPort());
      serverSocket.setSoTimeout(SOCKET_TIMEOUT_MILLIS);
      Socket socket = serverSocket.accept(); // Blocks until a connection is made on specified socket or until TIMEOUT reached.
      InputStream inputStream = file.getInputStream();
      OutputStream outputStream = socket.getOutputStream();
      copy(inputStream, outputStream);
      return true;
    } catch (IOException e) {
      System.err.println(e);
      return false;
    }
  }

  static void copy(InputStream in, OutputStream out) throws IOException {
    byte[] buf = new byte[8192];
    int len = 0;
    while ((len = in.read(buf)) != -1) {
      out.write(buf, 0, len);
    }
  }
}
