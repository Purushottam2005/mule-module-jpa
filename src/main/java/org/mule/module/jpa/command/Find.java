package org.mule.module.jpa.command;

import javax.persistence.EntityManager;
import java.util.Map;

public class Find implements JPACommand {

    public Object execute(EntityManager entityManager, Object entity, Map<String, Object> parameters)
            throws Exception {
        return entityManager.find(Class.forName((String) parameters.get("entityClass")), entity);
    }
}
