package org.mule.module.jpa;

import org.mule.api.MuleException;
import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transaction.TransactionCoordination;

import javax.persistence.EntityManagerFactory;

public class JPAUtils {

    /**
     * Returns a <code>JPATransaction</code> or null depending on whether or not this module's message processors
     * are running inside a <transactional></transactional> block.  This code will return a <code>JPATransaction</code>
     * if the processors are running inside a <transactional></transactional> block and null if not.  A null
     * value signifies we need to manually create the JPATransaction with the JPATransactionFactory to "locally"
     * manage non-explicit transaction.  This essentially means that each of the processors in this module run in their
     * own transaction unless they are inside a <transactional>block</transactional>.
     */
    @SuppressWarnings({"unchecked"})
    static public <T> T getTransactionalResource(EntityManagerFactory entityManagerFactory) {
        Transaction currentTx = TransactionCoordination.getInstance().getTransaction();
        if (currentTx != null) {
            if (currentTx.hasResource(entityManagerFactory)) {
                return (T) currentTx.getResource(entityManagerFactory);
            } else {

                Object connectionResource = entityManagerFactory.createEntityManager();

                try {
                    if (currentTx.supports(entityManagerFactory, connectionResource)) {
                        currentTx.bindResource(entityManagerFactory, connectionResource);
                    } else {
                        throw new TransactionException(CoreMessages.createStaticMessage("Endpoint is transactional but transaction does not support it"));
                    }
                } catch (MuleException ex) {
                    throw new JPAException(ex);
                }
                return (T) connectionResource;
            }
        } else {
            return (T) entityManagerFactory.createEntityManager();
        }

    }
}
