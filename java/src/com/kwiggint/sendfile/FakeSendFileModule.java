package com.kwiggint.sendfile;

import com.google.inject.AbstractModule;
import com.kwiggint.sendfile.api.FakeSendFileApi;
import com.kwiggint.sendfile.api.SendFileApi;
import com.kwiggint.sendfile.monitor.FakeFileMonitor;
import com.kwiggint.sendfile.monitor.FileMonitor;
import com.kwiggint.sendfile.task.FakeReceiveTask;
import com.kwiggint.sendfile.task.ReceiveTask;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Class Documentation */
public class FakeSendFileModule extends AbstractModule {
  private static final String CONFIG_LOCATION = "java/src/resources/config.yaml";

  @Override
  protected void configure() {
    bind(FileMonitor.class).to(FakeFileMonitor.class);
    bind(SendFileApi.class).to(FakeSendFileApi.class);
    bind(ReceiveTask.class).to(FakeReceiveTask.class);

    // Load configuration files.
    Yaml yaml = new Yaml();
    InputStream inputStream;
    try {
      inputStream = new FileInputStream(new File(CONFIG_LOCATION));
    } catch (FileNotFoundException e) {
      throw new AssertionError(e.getMessage());
    }
    // Load all separate documents in yaml.
    for (Object data : yaml.loadAll(inputStream)) {
      LinkedHashMap<String, Object> map;
      map = (LinkedHashMap<String, Object>) data; // TODO: get rid of this nastiness!
      // Bind all configuration values from the documents.
      Set<Map.Entry<String, Object>> entrySet = map.entrySet();
      for (Map.Entry<String, Object> e : entrySet) {
        if (e.getValue() instanceof Integer)
          bindConstant().annotatedWith(new ConfigValueImpl(e.getKey())).to((int) e.getValue());
        else if (e.getValue() instanceof String)
          bindConstant().annotatedWith(new ConfigValueImpl(e.getKey())).to((String) e.getValue());
        else throw new AssertionError("Unknown Yaml type");
      }
    }
  }
}
