package com.kwiggint.sendfile.action;

import com.kwiggint.sendfile.model.PendingFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

/** Action that receives a <code>PendingFile</code>. */
public class ReceiveAction {
  /**
   * Attempts to receive the specified <code>PendingFile</code>.
   *
   * @param pendingFile the <code>PendingFile</code> to be received.
   * @return true if <code>file</code> was received successfully in its entirety.
   */
  public static boolean receive(PendingFile pendingFile) {
    try {
      Socket socket = new Socket(pendingFile.getSender().getHostName(),
          pendingFile.getSender().getPort());

      // Blocks until a connection is made on specified socket or until TIMEOUT is reached.
      InputStream inputStream = socket.getInputStream();
      RandomAccessFile file = new RandomAccessFile(pendingFile.getFileName(), "rw");
      System.out.println("Receiving file " + pendingFile.getFileName());
      receiveByteArray(file, inputStream, socket.getReceiveBufferSize());
      socket.close();
      return true;
    } catch (IOException e) {
      System.err.println(e);
      return false;
    }
  }

  /**
   * Attempts to receive <code>file</code>'s data in byte array chunks from the
   * <code>InetSocketAddress</code> that <code>InputStream in</code> is currently connected to.
   *
   * @param file       the file from which byte array chunks will be read.
   * @param in         the <code>OutputStream</code> to which byte array chunks will be written.
   * @param bufferSize <link>in</link>'s corresponding <code>Socket</code> buffer size.
   * @throws IOException if an I/O error occurs. In particular, an <code>IOException</code> is
   *                     thrown if the output stream is closed.
   */
  static void receiveByteArray(RandomAccessFile file, InputStream in,
                               int bufferSize) throws IOException {
    byte[] buf = new byte[bufferSize];
    int i = 0;
    while (in.read(buf) != -1) {
      System.out.println("Receive While #" + i);
      file.write(buf);
    }
    System.out.println("Receive While Completed");
  }
}
