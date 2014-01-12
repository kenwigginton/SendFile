package com.kwiggint.sendfile;

import com.google.inject.AbstractModule;
import com.kwiggint.sendfile.action.SendAction;
import com.kwiggint.sendfile.api.FakeSendFileApi;
import com.kwiggint.sendfile.api.SendFileApi;
import com.kwiggint.sendfile.monitor.FakeFileMonitor;
import com.kwiggint.sendfile.monitor.FileMonitor;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Fake Guice module that binds all necessary fake classes. */
public class FakeSendFileModule extends AbstractModule {
  private static final String CONFIG_LOCATION = "java/src/resources/config.yaml";

  @Override
  protected void configure() {
    bind(FileMonitor.class).to(FakeFileMonitor.class);
    bind(SendFileApi.class).to(FakeSendFileApi.class);

    // TODO: move configuration code to separate entity.
    // Load configuration files.
    Yaml yaml = new Yaml();
    InputStream inputStream;
    try {
      inputStream = new FileInputStream(new File(CONFIG_LOCATION));
    } catch (FileNotFoundException e) {
      throw new AssertionError(e.getMessage());
    }

    // TODO: get rid of all the nasty typecasting!
    // Load all separate documents in yaml.
    for (Object data : yaml.loadAll(inputStream)) {
      processYamlEntrySet(((LinkedHashMap<String, Object>) data).entrySet());
    }
    requestStaticInjection(SendAction.class);
  }

  private void processYamlEntrySet(Set<Map.Entry<String, Object>> entrySet) {
    // Bind all configuration values from the documents.
    for (Map.Entry<String, Object> e : entrySet) {
      final ConfigValueImpl annotation = new ConfigValueImpl(e.getKey());
      final Object value = e.getValue();
      if (value instanceof Integer)
        bindConstant().annotatedWith(annotation).to((int) value);
      else if (value instanceof String)
        bindConstant().annotatedWith(annotation).to((String) value);
      else if (value instanceof ArrayList)
        bind(ArrayList.class).annotatedWith(annotation).toInstance((ArrayList) value);
      else if (value instanceof LinkedHashMap)
        processYamlEntrySet(((LinkedHashMap<String, Object>) value).entrySet());
      else throw new AssertionError("Unknown Yaml type");
    }
  }
}
