package com.kwiggint.sendfile.api;

import org.bouncycastle.jcajce.provider.digest.SHA3;

/**
 * Connects to the SendFile WS to perform REST requests.
 */
public interface SendFileApi {
  public boolean hasPendingTransfers();
  /**
   * Retrieves the incoming file's SHA3 hash.
   *
   * @return the unique SHA3 digest that the incoming file should match.
   */
  public SHA3.DigestSHA3 getIncomingFileHash();

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
