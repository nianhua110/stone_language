/*
 * kyle
 *
 */
package ast;

import lexer.Token;

public class StringLiteral extends ASTLeaf {
  public StringLiteral(Token token) {
    super(token);
  }

  public String value() {
    return token().getText();
  }
}
