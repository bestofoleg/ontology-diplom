package catcher.processor;

import catcher.annotation.TryToResolve;
import catcher.annotation.TryToResolveMethod;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class TryToResolveAnnotationBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> beanNameToClassMap;

    public TryToResolveAnnotationBeanPostProcessor() {
        this.beanNameToClassMap = new HashMap<>();
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(TryToResolve.class)) {
            beanNameToClassMap.put(beanName, beanClass);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = beanNameToClassMap.get(beanName);

        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object object, Method method, Object[] args) throws Throwable {
                    Object result = null;
                    if (method.getAnnotation(TryToResolveMethod.class) != null) {
                        try {
                            result = method.invoke(bean, args);
                        } catch (Exception exception) {
                            ClassPathXmlApplicationContext context =
                                    new ClassPathXmlApplicationContext("context.xml");
                            Parameter [] parameters = method.getParameters();
                            for (Parameter parameter : parameters) {
                                Object newValueParam = context.getBean(parameter.getType());
                                result = method.invoke(bean, newValueParam);
                            }
                        }
                    } else {
                        result = method.invoke(bean, args);
                    }
                    return result;
                }
            });
        }
        return bean;
    }
}
