package com.kwiggint.sendfile.monitor;

import com.kwiggint.sendfile.ConfigValue;
import com.kwiggint.sendfile.task.MonitorTask;

import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Class Documentation */
public class RealFileMonitor implements FileMonitor {
  private final MonitorTask monitorTask;
  private final int schedulingDelay;
  private final int poolSize;
  private ScheduledExecutorService scheduler;

  @Inject
  public RealFileMonitor(MonitorTask monitorTask,
                         @ConfigValue("real_monitor_pool_size") int poolSize,
                         @ConfigValue("real_monitor_delay") int schedulingDelay) {
    this.monitorTask = monitorTask;
    this.schedulingDelay = schedulingDelay;
    this.poolSize = poolSize;
  }

  @Override public void start() {
    scheduler = Executors.newScheduledThreadPool(poolSize);
    scheduler.scheduleAtFixedRate(monitorTask, 0, schedulingDelay, TimeUnit.SECONDS);
  }

  @Override public void stop() {
    scheduler.shutdown();
  }
}
