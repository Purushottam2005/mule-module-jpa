package org.mule.module.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.expression.RequiredValueException;
import org.mule.api.registry.InjectProcessor;
import org.mule.config.i18n.AnnotationsMessages;

import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;

/**
 * <code>PersistenceContextProcessor</code> is responsible for injecting the <code>MuleEntityManager</code>
 * into components using the @PersistenceContext annotation.
 */
public class PersistenceContextProcessor implements InjectProcessor {

    protected transient final Log logger = LogFactory.getLog(PersistenceContextProcessor.class);

    public Object process(Object object) {
        Field[] fields;
        try {
            fields = object.getClass().getDeclaredFields();
        } catch (NoClassDefFoundError e) {
            if (logger.isDebugEnabled()) {
                logger.warn(e.toString());
            }
            return object;
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(PersistenceContext.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(object) == null) {
                        field.set(object, new MuleEntityManager());
                    } else {
                        logger.warn("The PersistenceContext has already been injected");
                    }
                } catch (RequiredValueException e) {
                    throw e;
                } catch (Exception e) {
                    throw new RequiredValueException(AnnotationsMessages.lookupFailedSeePreviousException(object), e);
                }
            }
        }
        return object;
    }
}
