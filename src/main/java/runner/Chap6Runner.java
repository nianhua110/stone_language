/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package runner;

import chap6.BasicEvaluator;
import javassist.gluonj.util.Loader;

public class Chap6Runner {
  public static void main(String[] args) throws Throwable {
    Loader.run(BasicInterpreter.class, args, BasicEvaluator.class);
  }
}
