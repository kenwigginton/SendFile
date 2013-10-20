package com.kwiggint.sendfile;

import org.bouncycastle.crypto.digests.SHA3Digest;

/**
 * The File monitor provides several methods for communicating with the incoming/pending file
 * provider web service.
 */
public interface FileMonitor extends Runnable {
  /**
   * Polls the WS for pending files.
   *
   * @return <code>true</code> if there is a pending file transfer for this client.
   */
  public boolean checkForPendingFiles();

  /**
   * Retrieves the incoming file's SHA3 hash.
   *
   * @return the unique SHA3 digest that the incoming file should match.
   */
  public SHA3Digest getIncomingFileHash();

  /**
   * Retrieves the incoming file's name.
   *
   * @return the file name of the incoming file.
   */
  public String getIncomingFileName();

  /**
   * Retrieves the incoming file's size.
   *
   * @return the size of the incoming file.
   */
  public long getIncomingFileSize();
}
