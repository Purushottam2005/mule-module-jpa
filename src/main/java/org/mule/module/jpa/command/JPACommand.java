package org.mule.module.jpa.command;

import javax.persistence.EntityManager;
import java.util.Map;

public interface JPACommand {


    Object execute(EntityManager entityManager, Object entity, Map<String,Object> parameters) throws Exception;
}
