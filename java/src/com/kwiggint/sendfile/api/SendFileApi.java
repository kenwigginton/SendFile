package com.kwiggint.sendfile.api;

import com.kwiggint.sendfile.model.PendingFile;
import org.bouncycastle.jcajce.provider.digest.SHA3;

/** Connects to the SendFile WS to perform REST requests. */
public interface SendFileApi { // TODO: add tokens and UUIDs to requests.

  /**
   * Polls for pending files.
   *
   * @return <code>true</code> if there is a pending file transfer for this client.
   */
  public boolean hasPendingTransfers();

  /**
   * Retrieves the specified file's SHA3 hash.
   *
   * @return the unique SHA3 digest that the incoming file should match.
   */
  public SHA3.DigestSHA3 getIncomingFileHash();

  /**
   * Retrieves the specified file's name.
   *
   * @return the file name of the incoming file.
   */
  public String getIncomingFileName();

  /**
   * Retrieves the specified file's size.
   *
   * @return the size of the incoming file.
   */
  public long getIncomingFileSize();

  /**
   * Retrieves the next pending file from the server's pending file queue.
   *
   * @return the next pending file for this user.
   */
  public PendingFile getNextPendingFile();
}
