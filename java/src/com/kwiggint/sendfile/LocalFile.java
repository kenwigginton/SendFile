package com.kwiggint.sendfile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/** LocalFile class to facilitate writing/reading local files. */
public class LocalFile {
  private final File file;
  private byte data[];

  /** Private Constructor for a LocalFile with data. */
  private LocalFile(File file, byte data[]) {
    this.file = file;
    this.data = data;
  }

  /** Private Constructor for an empty LocalFile. */
  private LocalFile(File file) {
    this.file = file;
  }

  public InputStream getInputStream() throws FileNotFoundException {
    return new FileInputStream(file);
  }

  public boolean create() {
    try {
      createEmpty();
      if (data != null) populateFileWithData();
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  private void populateFileWithData() throws IOException {
    OutputStream out = new BufferedOutputStream(Files.newOutputStream(file.toPath()));
    out.write(data, 0, data.length);
    out.flush();
    out.close();
  }

  private boolean createEmpty() {
    try {
      if (!file.getParentFile().mkdirs()) return false;
      return file.createNewFile(); // TODO: append a 1 to end of file name if exact file already exists.
    } catch (IOException e) {
      return false;
    }
  }

  public static class Factory {
    private File file;
    private byte data[];

    private Factory() {
    }

    public static Factory create() {
      return new Factory();
    }

    public Factory file(File file) {
      this.file = file.getAbsoluteFile();
      return this;
    }

    public Factory file(String path) {
      this.file = new File(path).getAbsoluteFile();
      return this;
    }

    public Factory file(Path path) {
      this.file = path.toFile().getAbsoluteFile();
      return this;
    }

    public Factory data(byte data[]) {
      this.data = data;
      return this;
    }

    public LocalFile build() {
      return data == null ? new LocalFile(file) : new LocalFile(file, data);
    }
  }
}
