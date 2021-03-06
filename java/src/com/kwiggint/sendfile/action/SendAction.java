package com.kwiggint.sendfile.action;

import com.kwiggint.sendfile.ConfigValue;
import com.kwiggint.sendfile.model.PendingFile;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Action that sends a file across a Socket. Fails to be constructed if the sender does not
 * actually possess the file to be sent.
 */
public class SendAction implements Runnable {
  @Inject @ConfigValue("send_timeout")
  private static int socketTimeoutMillis;
  @Inject @ConfigValue("send_chunk_size")
  private static int dataChunkSize;
  private final PendingFile pendingFile;
  private boolean isSending;
  private final Thread thread;

  /**
   * Constructor for a new SendAction that takes a {@link PendingFile} as argument. Since the
   *
   *
   * @param pendingFile the file to be sent wrapped with sending information.
   *                    The file path must be specified completely either absolutely or relatively.
   * @throws AssertionError if the pending file does not actually exist for this sender.
   */
  public SendAction(PendingFile pendingFile) throws FileNotFoundException {
    if (!pendingFile.exists())
      throw new FileNotFoundException("Cannot send file " + pendingFile.getFileName() +
          " because it doesn't exist.");
    this.pendingFile = pendingFile;
    this.thread = new Thread(this);
    this.isSending = true;
    this.thread.start();
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
    byte[] buf = new byte[dataChunkSize];
    int len = 0;
    int i = 0;
    while ((len = file.read(buf)) != -1) {
      out.write(buf, 0, len);
    }
  }

  /**
   * Attempts to send the specified file to the specified <code>InetSocketAddress</code>.
   * The file's path must be specified completely either absolutely or relatively.
   *
   * @return true if <code>pendingFile</code> was sent successfully in its entirety.
   */
  public synchronized void run() {
    try {
      isSending = true;
      ServerSocket serverSocket = new ServerSocket(pendingFile.getSender().getPort());
      serverSocket.setSoTimeout(socketTimeoutMillis);
      // Blocks until a connection is made on specified socket or until TIMEOUT is reached.
      Socket socket = serverSocket.accept();
      System.out.println("Sending file " + pendingFile.getFileName());
      OutputStream outputStream = socket.getOutputStream();
      sendByteArray(new RandomAccessFile(pendingFile.getFileName(), "r"), outputStream);
      serverSocket.close();
    } catch (IOException e) {
      System.err.println(e); // TODO log error appropriately
    } finally {
      notifyAll();
      isSending = false;
    }
  }

  public boolean isSending() {
    return isSending;
  }
}
