package com.kwiggint.sendfile;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kwiggint.sendfile.monitor.FileMonitor;

import java.io.Console;

/** The Main app for SendFile. */
public class SendFileApp {
  public static String USER;
  public static char[] PASSWORD;

  static { //TODO: Remove this awful practice. Store pass (or don't!) securely during a session.
    Console console = System.console();
    while (USER != null && PASSWORD != null) {
      System.out.println("Username:\n");
      USER = console.readLine();
      System.out.println("Password:\n");
      PASSWORD = console.readPassword();
    }
  }

  public static void main(String args[]) {
    Console console = System.console();
    Injector injector = Guice.createInjector(new SendFileModule());
    FileMonitor fileMonitor = injector.getInstance(FileMonitor.class);
    fileMonitor.start();
  }
}
