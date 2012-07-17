package org.mule.module.jpa.command;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Abstraction around the EntityManager method's exposed in this module.
 */
public interface JPACommand {

    /**
     * Performs the JPA operations exposed by implementing classes (ie, persist, merge, etc.)
     *
     * @param entityManager the entityManager
     * @param payload       the message's payload.  Typically an instance of an entity class, but could also be a primary key
     *                      or query parameters depending on the operation.
     * @param parameters    additional parameters to pass to the request
     * @return the result of the JPA operation
     * @throws Exception
     */
    Object execute(EntityManager entityManager, Object payload, Map<String, Object> parameters) throws Exception;
}
