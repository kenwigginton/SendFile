package com.kwiggint.sendfile.task;

import com.kwiggint.sendfile.action.ReceiveAction;
import com.kwiggint.sendfile.api.ApiClient;

import javax.inject.Inject;

/** Runnable class that polls the SendFile API for pending transfers. */
public class MonitorTask implements Runnable {
  private final ApiClient apiClient;

  @Inject
  public MonitorTask(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  @Override
  public void run() {
    if (apiClient.hasPendingTransfers()) {
      Thread receiveAction = new Thread(new ReceiveAction(apiClient.getNextPendingFile()));
      receiveAction.start();
    }
  }
}
