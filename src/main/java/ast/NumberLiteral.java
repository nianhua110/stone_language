/*
 * kyle
 *
 */
package ast;

import lexer.Token;

public class NumberLiteral extends ASTLeaf {
  public NumberLiteral(Token t){
    super(t);
  }

  public int val(){
    return token().getNumber();
  }
}
