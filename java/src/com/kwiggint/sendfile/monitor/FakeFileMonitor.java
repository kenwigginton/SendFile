package com.kwiggint.sendfile.monitor;

import com.kwiggint.sendfile.ConfigValue;
import com.kwiggint.sendfile.task.MonitorTask;

import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Fake implementation of FileMonitor for testing purposes. */
public class FakeFileMonitor implements FileMonitor {
  private final MonitorTask monitorTask;
  private final int poolSize;
  private final int schedulingDelay;

  @Inject
  public FakeFileMonitor(MonitorTask monitorTask,
                         @ConfigValue("monitor_pool_size")int poolSize,
                         @ConfigValue("monitor_delay")int schedulingDelay) {
    this.monitorTask = monitorTask;
    this.poolSize = poolSize;
    this.schedulingDelay = schedulingDelay;
  }

  @Override
  public boolean checkForPendingFiles() {
    return true;
  }

  @Override
  public void start() {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(poolSize);
    scheduler.scheduleWithFixedDelay(monitorTask, schedulingDelay, schedulingDelay,
        TimeUnit.SECONDS);
  }
}
