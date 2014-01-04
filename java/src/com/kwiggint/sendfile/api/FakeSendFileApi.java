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

  @Override
  public DigestSHA3 getIncomingFileHash() {
    return fileHash;
  }

  @Override
  public String getIncomingFileName() {
    return fileName;
  }

  @Override
  public long getIncomingFileSize() {
    return fileSize;
  }

  @Override
  public PendingFile getNextPendingFile() {
    return new PendingFile(fileName, fileHash, fileSize, sender);
  }

  @Override
  public boolean hasPendingTransfers() {
    return true;
  }

  public FakeSendFileApi setIncomingFileName(String fileName) {
    this.fileName = fileName + 1;
    return this;
  }

  public FakeSendFileApi setIncomingFileHash(DigestSHA3 fileHash) {
    this.fileHash = fileHash;
    return this;
  }

  public FakeSendFileApi setIncomingFileSize(long fileSize) {
    this.fileSize = fileSize;
    return this;
  }

  public FakeSendFileApi setSender(InetSocketAddress sender) {
    this.sender = sender;
    return this;
  }
}
