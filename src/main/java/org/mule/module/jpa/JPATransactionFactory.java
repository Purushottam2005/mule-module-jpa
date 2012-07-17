package org.mule.module.jpa;

import org.mule.api.MuleContext;
import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionException;
import org.mule.api.transaction.TransactionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * <code>TransactionFactory</code> implementation used to create <code>JPATransactions</code>
 */
public class JPATransactionFactory implements TransactionFactory {

    EntityManagerFactory entityManagerFactory;

    public JPATransactionFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Transaction beginTransaction(MuleContext muleContext) throws TransactionException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return new JPATransaction(muleContext, entityManager);
    }

    public boolean isTransacted() {
        return true;
    }
}
