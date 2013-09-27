package com.kwiggint.sendfile;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

/** Tests for the class LocalFile */
public class LocalFileTest {
  private static final String TXT_FILE_PATH_STRING = "test.txt";
  private static final File TXT_FILE = new File(TXT_FILE_PATH_STRING);
  private static final Path TXT_FILE_PATH = TXT_FILE.toPath();
  private static final String TXT_FILE_INPUT = "Hello World";
  private static final byte TXT_DATA[] = TXT_FILE_INPUT.getBytes();
  // TODO: Test with larger data, i.e. picture or movie data.

  @Test
  public void testCreateEmpty() {
    assertTrue(LocalFile.Factory.create().file(TXT_FILE_PATH_STRING).build().create());
  }

  @Test
  public void testCreateWithData() {
    assertTrue(LocalFile.Factory.create().file(TXT_FILE).data(TXT_DATA).build().create());
  }

  @After
  public void tearDown() throws Exception {
    Files.deleteIfExists(TXT_FILE_PATH);
  }
}
