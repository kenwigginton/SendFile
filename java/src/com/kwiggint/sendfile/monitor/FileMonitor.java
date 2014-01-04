package com.kwiggint.sendfile.monitor;

/**
 * The FileMonitor class provides several methods for communicating with the incoming/pending file
 * provider web service.
 */
public interface FileMonitor {
  /** Starts the FileMonitor's monitoring process. */
  public void start();

  /** Stops the FileMonitor's monitoring process. */
  public void stop();
}
