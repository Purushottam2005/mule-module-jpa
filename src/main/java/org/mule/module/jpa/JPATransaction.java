package org.mule.module.jpa;

import org.mule.api.MuleContext;
import org.mule.api.transaction.TransactionException;
import org.mule.transaction.AbstractSingleResourceTransaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class JPATransaction extends AbstractSingleResourceTransaction
{
    EntityManager entityManager;
    EntityTransaction transaction;

    public JPATransaction(MuleContext muleContext, EntityManager entityManager) {
        super(muleContext);
        this.entityManager = entityManager;
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected void doBegin() throws TransactionException {
        transaction.begin();
    }

    @Override
    protected void doCommit() throws TransactionException {
        transaction.commit();
    }

    @Override
    protected void doRollback() throws TransactionException {
        transaction.rollback();
    }

    void doClose() {
        entityManager.close();
    }
}
