/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package runner;

import chap7.FuncEvaluator;
import javassist.gluonj.util.Loader;

public class FuncRunner {
  public static void main(String[] args) throws Throwable {
    Loader.run(FuncInterpretre.class, args, FuncEvaluator.class);
  }
}
