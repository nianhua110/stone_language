/*
 * Copyright (c) 2017, CipherGateway and/or its affiliates. All rights  reserved.
 *
 */
package runner;

import chap7.NestedEnv;
import ex.ParseException;
import parser.FuncParser;

public class FuncInterpretre extends BasicInterpreter {
  public static void main(String[] args) throws ParseException {

    /**
     def fib (n) {
         if n < 2 {
            n
         } else {
           fib(n-1)+fib(n-2)
        }
     }
     fib(10)

     */
    run(new FuncParser(), new NestedEnv());
  }
}
