package com.kwiggint.sendfile.monitor;

import com.kwiggint.sendfile.ConfigValue;
import com.kwiggint.sendfile.task.MonitorTask;

import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Fake implementation of FileMonitor for testing purposes. */
public class FakeFileMonitor implements FileMonitor {
  private final MonitorTask monitorTask;
  private final int schedulingDelay;
  private ScheduledExecutorService scheduler;
  private final int poolSize;

  @Inject
  public FakeFileMonitor(MonitorTask monitorTask,
                         @ConfigValue("monitor_pool_size") int poolSize,
                         @ConfigValue("monitor_delay") int schedulingDelay) {
    this.monitorTask = monitorTask;
    this.schedulingDelay = schedulingDelay;
    this.poolSize = poolSize;
  }

  @Override
  public void start() {
    scheduler = Executors.newScheduledThreadPool(poolSize);
    scheduler.scheduleAtFixedRate(monitorTask, 0, schedulingDelay, TimeUnit.SECONDS);
  }

  @Override public void stop() {
    scheduler.shutdown();
  }
}
