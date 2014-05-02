package com.kwiggint.sendfile.api;

import com.kwiggint.sendfile.model.PendingFile;
import org.bouncycastle.crypto.digests.SHA224Digest;

import javax.inject.Singleton;
import java.net.InetSocketAddress;

/**
 * Fake SendFile API that doesn't connect to the actual REST API endpoint.  Designed to permit
 * testing of sending files by circumventing the API.
 */
@Singleton
public class FakeApiClient implements ApiClient {
  private String fileName;
  private SHA224Digest fileHash;
  private long fileSize;
  private InetSocketAddress sender;
  private boolean hasPendingTransfer = false;

  public FakeApiClient setIncomingFileHash(SHA224Digest fileHash) {
    this.fileHash = fileHash;
    return this;
  }

  /**
   * Test functionality method which sets the incoming file name.
   * Also sets hasPendingTransfers to true.
   *
   * @param fileName
   * @return
   */
  public FakeApiClient setIncomingFileName(String fileName) {
    this.fileName = fileName;
    this.hasPendingTransfer = true;
    return this;
  }

  public FakeApiClient setIncomingFileSize(long fileSize) {
    this.fileSize = fileSize;
    return this;
  }

  @Override
  public PendingFile getNextPendingFile() {
    this.hasPendingTransfer = false;
    return new PendingFile(fileName, fileHash, fileSize, sender);
  }

  @Override public boolean removePendingFile(PendingFile pendingFile) {
    return true;
  }

  @Override
  public boolean hasPendingTransfers() {
    return hasPendingTransfer;
  }

  public FakeApiClient setSender(InetSocketAddress sender) {
    this.sender = sender;
    return this;
  }
}
