package com.kwiggint.sendfile;

import java.lang.annotation.Annotation;

/** Class Documentation */
public class ConfigValueImpl implements ConfigValue {
  private final String value;

  public ConfigValueImpl(String value) {
    this.value = value;
  }

  @Override
  public String value() {
    return value;
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return ConfigValue.class;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ConfigValueImpl)) return false;
    return ((ConfigValueImpl) o).value().equals(value);
  }

  @Override
  public int hashCode() {
    return value != null ? ((127 * "value".hashCode()) ^ value.hashCode()) : 0;
  }
}
