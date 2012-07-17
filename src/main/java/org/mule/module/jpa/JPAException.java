package org.mule.module.jpa;

/**
 * Runtime exception for the JPA module.
 */
public class JPAException extends RuntimeException {

    public JPAException() {
        super();
    }

    public JPAException(String s) {
        super(s);
    }

    public JPAException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public JPAException(Throwable throwable) {
        super(throwable);
    }
}
