package com.kwiggint.sendfile;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** The Main app for SendFile. */
public class SendFileApp {
  private static int POOL_SIZE = 1; // TODO: increase pool size.
  private static long DELAY = 10;

  public static void main(String args[]) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(POOL_SIZE);
    scheduler.scheduleWithFixedDelay(new FakeFileMonitor(), DELAY, DELAY, TimeUnit.SECONDS);
  }
}
