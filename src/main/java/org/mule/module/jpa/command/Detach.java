package org.mule.module.jpa.command;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Perform a JPA detach operation
 */
public class Detach implements JPACommand {

    protected transient Log logger = LogFactory.getLog(getClass());

    public Object execute(EntityManager entityManager, Object entity, Map<String, Object> parameters)
            throws Exception {
        logger.debug("Detaching entity: " + entity);
        entityManager.detach(entity);
        return entity;
    }
}
