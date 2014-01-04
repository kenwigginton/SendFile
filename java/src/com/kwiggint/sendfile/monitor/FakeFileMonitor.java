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
  private final ScheduledExecutorService scheduler;
  private Future<?> future;

  @Inject
  public FakeFileMonitor(MonitorTask monitorTask,
                         @ConfigValue("monitor_pool_size") int poolSize,
                         @ConfigValue("monitor_delay") int schedulingDelay) {
    this.monitorTask = monitorTask;
    this.schedulingDelay = schedulingDelay;
    scheduler = Executors.newScheduledThreadPool(poolSize);
  }

  @Override
  public void start() {
    future = scheduler.scheduleWithFixedDelay(monitorTask, 0, schedulingDelay, TimeUnit.SECONDS);
  }

  @Override public void stop() {
    future.cancel(true);
  }
}
