package tcp.trab_1.parse;

import tcp.trab_1.OptionalIterator;

import java.util.Optional;

public class Lexer implements OptionalIterator<Token> {
    private final char[] chars;
    private int index;

    public Lexer(char[] chars) {
        this.chars = chars;
        this.index = 0;
    }

    @Override
    public Optional<Token> next() {
        if (index >= chars.length)
            return Optional.empty();

        char c = chars[index];
        index++;

        Token token = switch (c) {
            case 'A' -> Token.A;
            case 'B' -> Token.B;
            case 'C' -> Token.C;
            case 'D' -> Token.D;
            case 'E' -> Token.E;
            case 'F' -> Token.F;
            case 'G' -> Token.G;
            case ' ' -> Token.SPACE;
            case '!' -> Token.EXCLAMATION;
            case 'O', 'o', 'I', 'i', 'U', 'u' -> Token.OTHER_VOWEL;
            case '0' -> Token.DIGIT_0;
            case '1' -> Token.DIGIT_1;
            case '2' -> Token.DIGIT_2;
            case '3' -> Token.DIGIT_3;
            case '4' -> Token.DIGIT_4;
            case '5' -> Token.DIGIT_5;
            case '6' -> Token.DIGIT_6;
            case '7' -> Token.DIGIT_7;
            case '8' -> Token.DIGIT_8;
            case '9' -> Token.DIGIT_9;
            case '?' -> Token.QUESTION_MARK;
            case '\n' -> Token.NEW_LINE;
            case ';' -> Token.SEMICOLON;
            case ',' -> Token.COMMA;
            default -> Token.OTHER_CHARACTERS;
        };

        return Optional.of(token);
    }

}
