package com.kwiggint.sendfile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** The Main app for SendFile. */
public class SendFileApp {
  private static final NewFileMonitor NEW_FILE_MONITOR = new NewFileMonitor();

  public static void main(String args[]) {
    ExecutorService executor = Executors.newCachedThreadPool();
    executor.execute(NEW_FILE_MONITOR);
    // TODO: Send File Thread that checks for incomplete sends.
  }
}
