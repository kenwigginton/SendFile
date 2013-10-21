package com.kwiggint.sendfile.monitor;

/**
 * The FileMonitor class provides several methods for communicating with the incoming/pending file
 * provider web service.
 */
public interface FileMonitor {
  /**
   * Polls the WS for pending files.
   *
   * @return <code>true</code> if there is a pending file transfer for this client.
   */
  public boolean checkForPendingFiles();

  /** Starts the FileMonitor's monitoring process. */
  public void start();
}
