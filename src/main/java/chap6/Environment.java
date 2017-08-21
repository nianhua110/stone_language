/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package chap6;

public interface Environment {
  void put(String name, Object value);

  Object get(String name);
}
