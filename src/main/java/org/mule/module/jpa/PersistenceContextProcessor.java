package org.mule.module.jpa;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mule.api.MuleContext;
import org.mule.api.context.MuleContextAware;
import org.mule.api.expression.RequiredValueException;
import org.mule.api.registry.InjectProcessor;
import org.mule.config.i18n.AnnotationsMessages;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;

/**
 * <code>PersistenceContextProcessor</code> is responsible for injecting the <code>MuleEntityManager</code>
 * into components using the @PersistenceContext annotation.
 */
public class PersistenceContextProcessor implements InjectProcessor, MuleContextAware {

    protected transient final Log logger = LogFactory.getLog(PersistenceContextProcessor.class);

    MuleContext muleContext;

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
                        EntityManagerFactory factory = muleContext.getRegistry().lookupObject(EntityManagerFactory.class);

                        if (factory == null) {
                            throw new JPAException("Couldn't find an EntityManagerFactory in the registry to inject");
                        }
                        field.set(object, new MuleEntityManager(factory));
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

    public void setMuleContext(MuleContext context) {
        muleContext = context;
    }
}
