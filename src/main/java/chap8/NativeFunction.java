/**/
package chap8;

import ast.ASTree;
import ex.StoneException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

/**
 * @author kyle
 */
public class NativeFunction {

  protected Method method;
  protected String name;
  protected int numParams;

  public NativeFunction( String name,Method method) {
    this.method = method;
    this.name = name;
    numParams = method.getParameterTypes().length;
  }

  /**
   * Getter for property 'numParams'.
   *
   * @return Value for property 'numParams'.
   */
  public int getNumParams() {
    return numParams;
  }

  public Object invoke(Object[] args, ASTree tree) {
    try {
      return method.invoke(null, args);
    } catch (Exception e) {
      throw new StoneException("bad native function call: " + name, tree);
    }
  }

  @Override
  public String toString() {
    return "<native: " + hashCode() + ">";
  }
}
