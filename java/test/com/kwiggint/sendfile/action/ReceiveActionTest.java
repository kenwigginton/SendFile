package com.kwiggint.sendfile.action;

import com.kwiggint.sendfile.FakeSendFileModule;
import com.kwiggint.sendfile.api.FakeSendFileApi;
import com.kwiggint.sendfile.api.SendFileApi;
import com.kwiggint.sendfile.model.PendingFile;
import com.kwiggint.sendfile.monitor.FileMonitor;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;


import javax.inject.Inject;
import java.io.File;
import java.net.InetSocketAddress;

/** Test suite for ReceiveAction. */
@Guice(modules = FakeSendFileModule.class)
public class ReceiveActionTest {
  private static final String LOCALHOST = "127.0.0.1";
  private static final int LOCALPORT = 60705;
  private static final String TEST_PATH = "java/test/resources/";
  private static final String TEST_FILE_NAME = "test.txt";
  @Inject SendFileApi sendFileApi;
  @Inject FileMonitor fileMonitor;

  @BeforeMethod
  public void setUp() throws Exception {
    ((FakeSendFileApi) sendFileApi).setSender(new InetSocketAddress(LOCALHOST,
        LOCALPORT)).setIncomingFileName(TEST_FILE_NAME);
  }

  @AfterMethod
  public void tearDown() throws Exception {
    fileMonitor.stop();
  }

  @Test
  public void testReceive() throws Exception {
    PendingFile pendingFile = new PendingFile(TEST_PATH + TEST_FILE_NAME,
        new InetSocketAddress(LOCALHOST, LOCALPORT));
    SendAction sendAction = new SendAction(pendingFile);

    new Thread(sendAction).start();
    fileMonitor.start();
    final File file = new File(TEST_FILE_NAME + 1);
    assertTrue(file.exists());
    assertTrue(file.isFile());
    assertTrue(file.canRead());
    assertTrue(file.delete());
  }
}
