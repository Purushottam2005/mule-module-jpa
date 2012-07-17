package org.mule.module.jpa.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Perform a JPA merge operation
 */
public class Merge implements JPACommand {

    protected transient Log logger = LogFactory.getLog(getClass());

    public Object execute(EntityManager entityManager, Object entity, Map<String, Object> parameters)
            throws Exception {

        logger.debug("Merging entity: " + entity);

        entityManager.merge(entity);
        return entity;
    }
}
