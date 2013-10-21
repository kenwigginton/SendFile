package com.kwiggint.sendfile.monitor;

/** States which describe the current state of a monitor class. */
public enum MonitorState {
  POLLING,
  RECEIVING,
  STOPPING;
}
