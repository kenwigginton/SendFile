package com.kwiggint.sendfile.task;

import com.kwiggint.sendfile.action.ReceiveAction;
import com.kwiggint.sendfile.api.SendFileApi;
import com.kwiggint.sendfile.model.PendingFile;

import javax.inject.Inject;

/** Runnable class that polls the SendFile API for pending transfers. */
public class MonitorTask implements Runnable {
  private final SendFileApi sendFileApi;

  @Inject
  public MonitorTask(SendFileApi sendFileApi) {
    this.sendFileApi = sendFileApi;
  }

  @Override
  public void run() {
    System.out.println("Running MonitorTask");
    if (sendFileApi.hasPendingTransfers()) {
      PendingFile pendingFile = sendFileApi.getNextPendingFile();
      ReceiveAction.receive(pendingFile);
    }
  }
}
