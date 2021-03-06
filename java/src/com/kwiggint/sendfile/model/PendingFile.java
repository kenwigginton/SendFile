package com.kwiggint.sendfile.model;

import org.bouncycastle.crypto.digests.SHA224Digest;

import java.io.File;
import java.net.InetSocketAddress;

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
  private SHA224Digest fileHash;
  /** Long representation of the file's intended size in KiloBytes. */
  private long fileSize;

  public PendingFile(String fileName, InetSocketAddress sender) {
    this.fileName = fileName;
    this.sender = sender;
  }

  public PendingFile(String fileName, SHA224Digest fileHash, long fileSize,
                     InetSocketAddress sender) {
    this.fileName = fileName;
    this.fileHash = fileHash;
    this.fileSize = fileSize;
    this.sender = sender;
  }

  public String getFileName() {
    return fileName;
  }

  public SHA224Digest getFileHash() {
    return fileHash;
  }

  public long getFileSize() {
    return fileSize;
  }

  public InetSocketAddress getSender() {
    return sender;
  }

  /** @return true if the file exists. */
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PendingFile that = (PendingFile) o;

    if (fileSize != that.fileSize) return false;
    if (fileHash != null ? !fileHash.equals(that.fileHash) : that.fileHash != null) return false;
    if (!fileName.equals(that.fileName)) return false;
    if (!sender.equals(that.sender)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = fileName.hashCode();
    result = 31 * result + sender.hashCode();
    result = 31 * result + (fileHash != null ? fileHash.hashCode() : 0);
    result = 31 * result + (int) (fileSize ^ (fileSize >>> 32));
    return result;
  }
}
