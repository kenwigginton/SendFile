package com.kwiggint.sendfile;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kwiggint.sendfile.monitor.FileMonitor;

/** The Fake main app for SendFile. */
public class FakeSendFileApp {
  public static void main(String args[]) {
    Injector injector = Guice.createInjector(new FakeSendFileModule());
    FileMonitor fileMonitor = injector.getInstance(FileMonitor.class);
    fileMonitor.start();
  }
}
