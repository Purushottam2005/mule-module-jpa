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
public class JPATransactionFactory implements UniversalTransactionFactory
{

    EntityManagerFactory entityManagerFactory;

    public JPATransactionFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public Transaction beginTransaction(MuleContext muleContext) throws TransactionException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        JPATransaction jpaTransaction = new JPATransaction(muleContext, entityManager);
        jpaTransaction.begin();
        return jpaTransaction;
    }

    public boolean isTransacted() {
        return true;
    }

    public Transaction createUnboundTransaction(MuleContext muleContext) throws TransactionException
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return new JPATransaction(muleContext, entityManager);
    }
}
