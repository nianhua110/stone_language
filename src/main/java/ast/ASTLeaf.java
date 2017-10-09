/*
 * kyle
 *
 */
package ast;

import lexer.Token;

import java.util.ArrayList;
import java.util.Iterator;

public class ASTLeaf extends ASTree {
  private static ArrayList<ASTree> empty = new ArrayList<ASTree>();
  protected Token token;

  public ASTLeaf(Token token) {
    this.token = token;
  }

  public ASTree child(int i) {
    throw new IndexOutOfBoundsException();
  }

  public int numChildren() {
    return 0;
  }

  public Iterator<ASTree> children() {
    return empty.iterator();
  }

  public String location() {
    return "at line " + token.getLineNumber();
  }

  public Token token() {
    return token;
  }

  @Override
  public String toString() {
    return token.getText();
  }
}
