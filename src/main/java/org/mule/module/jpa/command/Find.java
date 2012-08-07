package org.mule.module.jpa.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleMessage;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Perform a JPA find operation
 */
public class Find implements JPACommand {

    protected transient Log logger = LogFactory.getLog(getClass());

    public Object execute(EntityManager entityManager, Object message, Map<String, Object> parameters)
            throws Exception {
        Object primaryKey = parameters.get("id");
        logger.debug(String.format("Finding entities of type: %s with primary key: %s", parameters.get("entityClass"),
                primaryKey));
        return entityManager.find(Class.forName((String) parameters.get("entityClass")), primaryKey);
    }
}
