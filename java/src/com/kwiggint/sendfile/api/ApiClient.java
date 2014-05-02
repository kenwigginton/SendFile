package com.kwiggint.sendfile.api;

import com.kwiggint.sendfile.model.PendingFile;

/** Connects to the SendFile WS to perform REST requests. */
public interface ApiClient { // TODO: add tokens and UUIDs to requests.

  /**
   * Minimal check for pending files.
   *
   * @return <code>true</code> if there is a pending file transfer for this client.
   */
  public boolean hasPendingTransfers();

  /**
   * Retrieves the next pending file from the local pending file queue.
   * Should always be called <i>after</i> hasPendingTransfers().
   *
   * @return the next pending file for this user.
   */
  public PendingFile getNextPendingFile();

  /**
   * Removes the specified pending file from the user's pending file queue on the server.
   *
   * @return success of file removal.
   */
  public boolean removePendingFile(PendingFile pendingFile);
}
