package com.kwiggint.sendfile.api;

import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;

/** Class Documentation */
public class FakeSendFileApi implements SendFileApi {
  @Override
  public DigestSHA3 getIncomingFileHash() {
    return null;
  }

  @Override
  public String getIncomingFileName() {
    return "Test.txt";
  }

  @Override
  public long getIncomingFileSize() {
    return 200;
  }

  @Override
  public boolean hasPendingTransfers() {
    return true;
  }
}
