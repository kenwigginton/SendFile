package com.kwiggint.sendfile.action;

import com.kwiggint.sendfile.FakeSendFileModule;
import com.kwiggint.sendfile.api.FakeSendFileApi;
import com.kwiggint.sendfile.api.SendFileApi;
import com.kwiggint.sendfile.model.PendingFile;
import com.kwiggint.sendfile.monitor.FileMonitor;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.io.File;
import java.net.InetSocketAddress;

import static org.testng.Assert.assertTrue;

/** Test suite for ReceiveAction. */
@Guice(modules = FakeSendFileModule.class)
public class ReceiveActionTest {
  private static final String LOCALHOST = "127.0.0.1";
  private static final int LOCALPORT = 60705;
  private static final String TEST_PATH = "java/test/resources/";
  private static final String BASIC_FILE_NAME = "basic.txt";
  private static final String IMG_FILE_NAME = "img.png";
  private static long TIMEOUT_MS = 3000;
  @Inject SendFileApi sendFileApi;
  @Inject FileMonitor fileMonitor;
  private int fileAppend = -1;

  @AfterMethod
  public void tearDown() throws Exception {
    fileMonitor.stop();
  }

  @BeforeMethod
  public void setup() throws Exception {
    fileMonitor.start();
  }

  @Test
  public void testReceiveBasic() throws Exception {
    ((FakeSendFileApi) sendFileApi).setSender(new InetSocketAddress(LOCALHOST,
        LOCALPORT)).setIncomingFileName(BASIC_FILE_NAME + incrAppend());
    PendingFile pendingFile = new PendingFile(TEST_PATH + BASIC_FILE_NAME,
        new InetSocketAddress(LOCALHOST, LOCALPORT));
    SendAction sendAction = new SendAction(pendingFile);

    new Thread(sendAction).start();
    Thread.sleep(TIMEOUT_MS);
    File file = new File(BASIC_FILE_NAME + fileAppend);
    assertTrue(file.exists());
    assertTrue(file.isFile());
    assertTrue(file.canRead());
    assertTrue(file.delete());
  }

  @Test
  public void testReceiveIMG() throws Exception {
    ((FakeSendFileApi) sendFileApi).setSender(new InetSocketAddress(LOCALHOST,
        LOCALPORT + 10)).setIncomingFileName(IMG_FILE_NAME + incrAppend());
    PendingFile pendingFile = new PendingFile(TEST_PATH + IMG_FILE_NAME,
        new InetSocketAddress(LOCALHOST, LOCALPORT + 10));
    SendAction sendAction = new SendAction(pendingFile);

    new Thread(sendAction).start();
    Thread.sleep(TIMEOUT_MS + 2000);
    File file = new File(IMG_FILE_NAME + fileAppend);
    assertTrue(file.exists());
    assertTrue(file.isFile());
    assertTrue(file.canRead());
    assertTrue(file.delete());
  }

  private int incrAppend() {
    return ++fileAppend;
  }
}
