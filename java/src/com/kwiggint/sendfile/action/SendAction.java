package com.kwiggint.sendfile.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/** Action that sends a file across a Socket. */
public class SendAction {
  private static final int SOCKET_TIMEOUT_MILLIS = 30000;
  private static final int DATA_CHUNK_SIZE = 8192;

  /**
   * Attempts to send the specified file to the specified <code>InetSocketAddress</code>.
   *
   * @param socketAddress the InetSocketAddress at which bytes will be sent.
   * @param file          the file from which bytes will be read.
   * @return true if <code>file</code> was sent successfully in its entirety.
   */
  public static boolean send(InetSocketAddress socketAddress, RandomAccessFile file) {
    try {
      ServerSocket serverSocket = new ServerSocket(socketAddress.getPort());
      serverSocket.setSoTimeout(SOCKET_TIMEOUT_MILLIS);
      // Blocks until a connection is made on specified socket or until TIMEOUT is reached.
      Socket socket = serverSocket.accept();
      OutputStream outputStream = socket.getOutputStream();
      sendByteArray(file, outputStream);
      outputStream.close();
      return true;
    } catch (IOException e) {
      System.err.println(e);
      return false;
    }
  }

  /**
   * Attempts to send <code>file</code>'s data in byte array chunks to the
   * <code>InetSocketAddress</code> that <code>OutputStream out</code> is currently connected to.
   *
   * @param file the file from which byte array chunks will be read.
   * @param out  the <code>OutputStream</code> to which byte array chunks will be written.
   * @throws IOException if an I/O error occurs. In particular, an <code>IOException</code> is
   *                     thrown if the output stream is closed.
   */
  static void sendByteArray(RandomAccessFile file, OutputStream out) throws IOException {
    byte[] buf = new byte[DATA_CHUNK_SIZE];
    int len = 0;
    while ((len = file.read(buf)) != -1) {
      out.write(buf, 0, len);
    }
  }
}
