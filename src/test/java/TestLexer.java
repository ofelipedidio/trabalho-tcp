import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import tcp.trab_1.parse.Lexer;
import tcp.trab_1.parse.Token;

import java.util.ArrayList;

public class TestLexer {
    @Test
    public void testNotes() {
        Token[] tokens = lex("ABCDEFG");

        Assertions.assertArrayEquals(
                new Token[]{Token.A, Token.B, Token.C, Token.D, Token.E, Token.F, Token.G},
                tokens);
    }

    private static Token[] lex(String input) {
        Iterable<Token> iterable = new Lexer(input.toCharArray());

        ArrayList<Token> tokenList = new ArrayList<>();
        iterable.forEach(tokenList::add);

        return tokenList.toArray(new Token[0]);
    }
}
