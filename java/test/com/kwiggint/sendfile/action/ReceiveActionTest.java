package com.kwiggint.sendfile.action;

import com.kwiggint.sendfile.ConfigValue;
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
import java.util.ArrayList;

import static org.testng.Assert.*;

/** Test suite for ReceiveAction. */
@Guice(modules = FakeSendFileModule.class)
public class ReceiveActionTest {
  private static final String LOCALHOST = "127.0.0.1";
  private static final int LOCALPORT = 60705;
  private static final String TEST_PATH = "java/test/resources/";
  private static final String FAKE_FILE_1 = "non-extant1.txt";
  private static final InetSocketAddress FAKE_SENDER_1 = new InetSocketAddress(LOCALHOST,
      LOCALPORT);
  private static final String FAKE_FILE_2 = "non-extant2.txt";
  private static final InetSocketAddress FAKE_SENDER_2 = new InetSocketAddress(LOCALHOST,
      LOCALPORT + 1);
  private static long TIMEOUT_MS = 2000;
  @Inject SendFileApi sendFileApi;
  @Inject FileMonitor fileMonitor;
  @Inject @ConfigValue("test-files") ArrayList testFiles;
  private int fileAppend = -1;

  @AfterMethod
  public void tearDown() {
    fileMonitor.stop();
  }

  @BeforeMethod
  public void setup() {
    fileMonitor.start();
  }

  @Test
  public void testSendFileApiHasPendingFile() {
    ((FakeSendFileApi) sendFileApi).setIncomingFileName(FAKE_FILE_1).setSender(FAKE_SENDER_1);
    assertTrue(sendFileApi.hasPendingTransfers());
    assertEquals(sendFileApi.getNextPendingFile(), new PendingFile(FAKE_FILE_1, FAKE_SENDER_1));
    assertFalse(sendFileApi.hasPendingTransfers());
    ((FakeSendFileApi) sendFileApi).setIncomingFileName(FAKE_FILE_2).setSender(FAKE_SENDER_2);
    assertTrue(sendFileApi.hasPendingTransfers());
    assertEquals(sendFileApi.getNextPendingFile(), new PendingFile(FAKE_FILE_2, FAKE_SENDER_2));
    assertFalse(sendFileApi.hasPendingTransfers());
  }

  @Test
  public void testSendReceiveAll() throws Exception {
    for (int i = 0; i < testFiles.size(); i++) {
      String s = (String) testFiles.get(i);
      ((FakeSendFileApi) sendFileApi).setSender(new InetSocketAddress(LOCALHOST,
          LOCALPORT)).setIncomingFileName(s + incrAppend());
      PendingFile pendingFile = new PendingFile(TEST_PATH + s, new InetSocketAddress(LOCALHOST,
          LOCALPORT));
      SendAction sendAction = new SendAction(pendingFile);

      new Thread(sendAction).start();
      Thread.sleep(TIMEOUT_MS);
      File file = new File(s + fileAppend);
      assertTrue(file.exists());
      assertTrue(file.isFile());
      assertTrue(file.canRead());
      assertTrue(file.delete());
    }
  }

//  @Test
//  public void testReceiveBasic() throws Exception {
//      ((FakeSendFileApi) sendFileApi).setSender(new InetSocketAddress(LOCALHOST,
//          LOCALPORT)).setIncomingFileName(BASIC_FILE_NAME + incrAppend());
//      PendingFile pendingFile = new PendingFile(TEST_PATH + BASIC_FILE_NAME,
//          new InetSocketAddress(LOCALHOST, LOCALPORT));
//      SendAction sendAction = new SendAction(pendingFile);
//
//      new Thread(sendAction).start();
//      Thread.sleep(TIMEOUT_MS);
//      File file = new File(BASIC_FILE_NAME + fileAppend);
//      assertTrue(file.exists());
//      assertTrue(file.isFile());
//      assertTrue(file.canRead());
//      assertTrue(file.delete());
//    }

  private int incrAppend() {
    return ++fileAppend;
  }
}
