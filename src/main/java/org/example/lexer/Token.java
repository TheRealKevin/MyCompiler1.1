package org.example.lexer;

public class Token {

    public final TokenType type;
    public final int line;
    public final int column;
    public final Object value;

    public Token(TokenType type, Object value, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(line).append(":").append(column).append(" ");

        switch(type){
            case ID:
                sb.append("id ").append(value);
                break;
            case INTEGER:
                sb.append("integer ").append(value);
                break;
            case CHARACTER:
                sb.append("character ").append(value);
                break;
            case STRING:
                sb.append("string ").append(value);
                break;
            case SYMBOL:
                sb.append(value);
                break;
            case KEYWORD:
                sb.append(value);
                break;
            case ERROR:
                sb.append("error:").append(value);
                break;
            default:
                throw new AssertionError("Unknown token type: " + type);
        }

        return sb.toString();
    }

}
