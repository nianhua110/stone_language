/*
 *
 */
package chap8;

import chap6.Environment;
import ex.StoneException;

import java.lang.reflect.Method;

/**
 * @author kyle
 */
public class Natives {
  public Environment environment(Environment env) {
    appendNatives(env);
    return env;
  }

  protected void appendNatives(Environment env) {
    append(env, "print", Natives.class, "print", Object.class);
    append(env, "read", Natives.class, "read");
    append(env, "length", Natives.class, "length", String.class);
    append(env, "toInt", Natives.class, "toInt", Object.class);
    append(env, "cvurrentTime", Natives.class, "cvurrentTime");
  }

  protected void append(Environment env, String name, Class<?> clazz,
                        String methodName, Class<?>... params) {
    Method m;
    try {
      m = clazz.getMethod(methodName, params);
    } catch (NoSuchMethodException e) {
      throw new StoneException(" cannot find a native function: " + methodName);
    }
    env.put(name, new NativeFunction(methodName, m));
  }

  public static int print(Object obj) {
    System.out.println(obj.toString());
    return 0;
  }

  public static int length(String s) {
    return s.length();
  }

  public static int toInt(Object value) {
    if (value instanceof String) {
      return Integer.parseInt((String) value);
    } else if (value instanceof Integer) {
      return ((Integer) value).intValue();
    } else {
      throw new NumberFormatException(value.toString());
    }

  }

  private static long startTime = System.currentTimeMillis();

  private static int currentTime() {
    return (int) (System.currentTimeMillis() - startTime);
  }
}
