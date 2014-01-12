package com.kwiggint.sendfile.task;

import com.kwiggint.sendfile.action.ReceiveAction;
import com.kwiggint.sendfile.api.SendFileApi;

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
    if (sendFileApi.hasPendingTransfers()) {
      Thread receiveAction = new Thread(new ReceiveAction(sendFileApi.getNextPendingFile()));
      receiveAction.start();
    }
  }
}
