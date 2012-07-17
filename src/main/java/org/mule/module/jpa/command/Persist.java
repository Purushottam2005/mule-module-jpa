package org.mule.module.jpa.command;

import javax.persistence.EntityManager;
import java.util.Map;

public class Persist implements JPACommand {

    public Object execute(EntityManager entityManager, Object entity, Map<String, Object> parameters)
            throws Exception {
        entityManager.persist(entity);
        return entity;
    }
}
