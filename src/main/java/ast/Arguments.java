/*
 * kyle
 *
 */
package ast;

import java.util.List;

public class Arguments extends Postifix {
  public Arguments(List<ASTree> list) {
    super(list);
  }

  protected int size() {
    return numChildren();
  }
}
