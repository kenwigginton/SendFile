package com.kwiggint.sendfile.api;

import com.kwiggint.sendfile.model.PendingFile;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;

import javax.inject.Singleton;
import java.net.InetSocketAddress;

/**
 * Fake SendFile API that doesn't connect to the actual REST API endpoint.  Designed to permit
 * testing of sending files by circumventing the API.
 */
@Singleton
public class FakeSendFileApi implements SendFileApi {
  private String fileName;
  private DigestSHA3 fileHash;
  private long fileSize;
  private InetSocketAddress sender;
  private boolean hasPendingTransfer = false;

  @Override
  public DigestSHA3 getIncomingFileHash() {
    return fileHash;
  }

  public FakeSendFileApi setIncomingFileHash(DigestSHA3 fileHash) {
    this.fileHash = fileHash;
    return this;
  }

  @Override
  public String getIncomingFileName() {
    return fileName;
  }

  /**
   * Test functionality method which sets the incoming file name.
   * Also sets hasPendingTransfers to true.
   *
   * @param fileName
   * @return
   */
  public FakeSendFileApi setIncomingFileName(String fileName) {
    this.fileName = fileName;
    this.hasPendingTransfer = true;
    return this;
  }

  @Override
  public long getIncomingFileSize() {
    return fileSize;
  }

  public FakeSendFileApi setIncomingFileSize(long fileSize) {
    this.fileSize = fileSize;
    return this;
  }

  @Override
  public PendingFile getNextPendingFile() {
    this.hasPendingTransfer = false;
    return new PendingFile(fileName, fileHash, fileSize, sender);
  }

  @Override
  public boolean hasPendingTransfers() {
    return hasPendingTransfer;
  }

  public FakeSendFileApi setSender(InetSocketAddress sender) {
    this.sender = sender;
    return this;
  }
}
