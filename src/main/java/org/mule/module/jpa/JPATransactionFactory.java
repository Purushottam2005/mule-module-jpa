package org.mule.module.jpa;

import org.mule.api.MuleContext;
import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionException;
import org.mule.api.transaction.UniversalTransactionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * <code>TransactionFactory</code> implementation used to create <code>JPATransactions</code>
 */
public class JPATransactionFactory implements UniversalTransactionFactory {

    public JPATransactionFactory() {
    }

    public Transaction beginTransaction(MuleContext muleContext) throws TransactionException {
        return new JPATransaction(muleContext);
    }

    public boolean isTransacted() {
        return true;
    }

    public Transaction createUnboundTransaction(MuleContext muleContext) throws TransactionException {
        return new JPATransaction(muleContext);
    }
}
