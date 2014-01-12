package com.kwiggint.sendfile.model;

import java.io.File;
import java.net.InetSocketAddress;

import static org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;

/**
 * Model that represents a pending file transfer. Is applicable to both sender and recipient, but
 * not identical for both. Consistency across sender and recipient must be maintained only in the
 * final file name (the sender's file path excluding the path), hash, size, and sender InetAddress.
 */
public class PendingFile {
  /** String representation of the pending file's name stripped of any directories. */
  private final String fileName;
  /**
   * Host and port representation of the sender's location.  Should define a physically valid
   * and reachable address that accurately connects to the sender's location despite NAT and
   * firewall restrictions.
   */
  private final InetSocketAddress sender;
  /**
   * SHA3 Hash of the original source file.  Intended for comparison to the final received file
   * for validity.
   */
  private DigestSHA3 fileHash;
  /** Long representation of the file's intended size in KiloBytes. */
  private long fileSize;

  public PendingFile(String fileName, InetSocketAddress sender) {
    this.fileName = fileName;
    this.sender = sender;
  }

  public PendingFile(String fileName, DigestSHA3 fileHash, long fileSize,
                     InetSocketAddress sender) {
    this.fileName = fileName;
    this.fileHash = fileHash;
    this.fileSize = fileSize;
    this.sender = sender;
  }

  public String getFileName() {
    return fileName;
  }

  public DigestSHA3 getFileHash() {
    return fileHash;
  }

  public long getFileSize() {
    return fileSize;
  }

  public InetSocketAddress getSender() {
    return sender;
  }

  /**
   *
   *
   * @return true if the file exists.
   */
  public boolean exists() {
    return new File(fileName).exists();
  }

  @Override
  public String toString() {
    return "PendingFile{" +
        "fileName='" + fileName + '\'' +
        ", fileSize=" + fileSize +
        ", sender=" + sender +
        '}';
  }
}
