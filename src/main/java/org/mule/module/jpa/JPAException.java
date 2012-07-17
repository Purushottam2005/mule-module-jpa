package org.mule.module.jpa;

public class JPAException extends RuntimeException {

    public JPAException() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    public JPAException(String s) {
        super(s);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public JPAException(String s, Throwable throwable) {
        super(s, throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public JPAException(Throwable throwable) {
        super(throwable);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
