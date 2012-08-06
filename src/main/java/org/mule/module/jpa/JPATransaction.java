package org.mule.module.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleContext;
import org.mule.api.transaction.TransactionException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transaction.AbstractSingleResourceTransaction;
import org.mule.transaction.IllegalTransactionStateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * <code>SingleResourceTransaction</code> implementation for JPA.  This class essentially provides a wrapper around
 * an <code>EntityTransaction</code>.
 */
public class JPATransaction extends AbstractSingleResourceTransaction
{

    protected transient Log logger = LogFactory.getLog(getClass());

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
    public void bindResource(Object key, Object resource) throws TransactionException
    {
        logger.debug("Binding JPA transaction: " + super.getId());
        if (!(key instanceof EntityManagerFactory) || !(resource instanceof EntityManager))
        {
            throw new IllegalTransactionStateException(
                CoreMessages.transactionCanOnlyBindToResources("javax.persistence.EntityManagerFactory/javax.persistence.EntityManager"));
        }
        super.bindResource(key, resource);
    }

    @Override
    protected void doBegin() throws TransactionException {
        //Do nothing
    }

    @Override
    protected void doCommit() throws TransactionException {
        logger.debug("Committing JPA transaction: " + super.getId());
        transaction.commit();
    }

    @Override
    protected void doRollback() throws TransactionException {
        logger.debug("Rolling back JPA transaction: " + super.getId());
        transaction.rollback();
    }

    void doClose() {
        entityManager.close();
    }
}
