package org.mule.module.jpa.command;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.module.jpa.JPAException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public class Query implements JPACommand {

    protected transient Log logger = LogFactory.getLog(getClass());

    public Object execute(EntityManager entityManager, Object entity, Map<String, Object> parameters) throws Exception {

        if (entity instanceof CriteriaQuery) {
            return entityManager.createQuery((CriteriaQuery) entity).getResultList();
        } else if (parameters.containsKey("namedQuery")) {
            javax.persistence.Query query = entityManager.createNamedQuery((String) parameters.get("namedQuery"));
            if (entity != null) {
                setParametersFromPayload(entity, query);
            }
            return query.getResultList();
        } else if (parameters.containsKey("statement")) {
            javax.persistence.Query query = entityManager.createQuery((String) parameters.get("statement"));
            if (entity != null) {
                setParametersFromPayload(entity, query);
            }
            return query.getResultList();
        }
        throw new JPAException("Couldn't resolve query from either the payload or the statement attribute");
    }

    void setParametersFromPayload(Object entity, javax.persistence.Query query) {
        if (entity instanceof Map) {
            Map<String, Object> parameters = (Map<String, Object>) entity;
            for (String key : parameters.keySet()) {
                query.setParameter(key, parameters.get(key));
            }
        } else if (entity instanceof List) {
            List parameters = (List) entity;
            for (int i = 0; i < parameters.size(); i++) {
                query.setParameter(i + 1, parameters.get(i));
            }
        } else {
            logger.warn("Payload should be a Map of parameters, List of parameters or null " +
                    "when using JPA-QL or named queries");
        }
    }
}

