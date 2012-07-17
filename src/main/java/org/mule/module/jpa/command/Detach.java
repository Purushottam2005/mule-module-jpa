package org.mule.module.jpa.command;


import javax.persistence.EntityManager;
import java.util.Map;

public class Detach implements JPACommand {

    public Object execute(EntityManager entityManager, Object entity, Map<String, Object> parameters)
            throws Exception {
        entityManager.detach(entity);
        return entity;
    }
}
