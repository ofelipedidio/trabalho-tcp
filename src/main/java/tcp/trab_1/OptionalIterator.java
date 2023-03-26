package tcp.trab_1;

import java.util.Iterator;
import java.util.Optional;

public interface OptionalIterator<T> extends Iterable<T> {
    Optional<T> next();

    @Override
    default Iterator<T> iterator() {
        OptionalIterator<T> lexerIterator = this;
        return new Iterator<>() {
            private Optional<T> currentToken = lexerIterator.next();

            @Override
            public boolean hasNext() {
                return currentToken.isPresent();
            }

            @Override
            public T next() {
                T token = currentToken.get();
                currentToken = lexerIterator.next();
                return token;
            }
        };
    }
}
