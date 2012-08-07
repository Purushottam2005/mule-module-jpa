package org.mule.module.jpa;

import org.mule.api.MuleException;
import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionException;
import org.mule.config.i18n.CoreMessages;
import org.mule.transaction.TransactionCoordination;

import javax.persistence.EntityManagerFactory;

/**
 * Utility methods for JPA.
 */
public class JPAUtils {

    /**
     * Fetches an EntityManager in a transaction-aware manner.
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
