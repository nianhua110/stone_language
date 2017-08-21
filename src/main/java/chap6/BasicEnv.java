/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package chap6;

import java.util.HashMap;

public class BasicEnv implements Environment {
  protected HashMap<String, Object> values;
  public BasicEnv(){
    values = new HashMap<>();
  }
  @Override
  public void put(String name, Object value) {
    values.put(name, value);
  }

  @Override
  public Object get(String name) {
    return values.get(name);
  }
}
