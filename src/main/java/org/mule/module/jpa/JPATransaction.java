package org.mule.module.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleContext;
import org.mule.api.transaction.TransactionException;
import org.mule.transaction.AbstractSingleResourceTransaction;

import javax.persistence.EntityManager;
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
    protected void doBegin() throws TransactionException {
        logger.debug("Beginning JPA transaction: " + super.getId());
        transaction.begin();
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
