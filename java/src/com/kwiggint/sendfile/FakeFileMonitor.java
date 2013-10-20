package com.kwiggint.sendfile;

import org.bouncycastle.crypto.digests.SHA3Digest;

/** Fake pending file monitor. */
public class FakeFileMonitor implements FileMonitor {
  private volatile boolean hasIncomingFile;

  @Override
  public boolean checkForPendingFiles() {
    return hasIncomingFile;
  }

  @Override
  public SHA3Digest getIncomingFileHash() {
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

  /** Run method will check for incoming files. */
  @Override
  public void run() {
    hasIncomingFile = true;
  }
}
