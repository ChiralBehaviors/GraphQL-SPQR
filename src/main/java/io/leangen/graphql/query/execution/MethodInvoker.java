package io.leangen.graphql.query.execution;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import io.leangen.graphql.util.ClassUtils;

/**
 * Created by bojan.tomic on 7/20/16.
 */
public class MethodInvoker extends Executable {

    protected AnnotatedType enclosingType;
    protected AnnotatedType returnType;

    public MethodInvoker(Method resolverMethod, AnnotatedType enclosingType) {
        this.delegate = resolverMethod;
        this.enclosingType = enclosingType;
        this.returnType = resolveReturnType(enclosingType);
    }

    @Override
    public Object execute(Object target, Object[] args) throws InvocationTargetException, IllegalAccessException {
        return ((Method) delegate).invoke(target, args);
    }

    @Override
    public AnnotatedType getReturnType() {
        return returnType;
    }


    public AnnotatedType resolveReturnType(AnnotatedType enclosingType) {
        return ClassUtils.getReturnType(((Method) delegate), enclosingType);
    }

    @Override
    public int getParameterCount() {
        return ((Method) delegate).getParameterCount();
    }

    @Override
    public AnnotatedType[] getAnnotatedParameterTypes() {
        return ClassUtils.getParameterTypes((Method) delegate, enclosingType);
    }

    @Override
    public Parameter[] getParameters() {
        return ((Method) delegate).getParameters();
    }

    @Override
    public String toString() {
        return ((Method) delegate).getDeclaringClass().getName() + "#"
                + ((Method) delegate).getName()
                + " -> " + returnType.toString();
    }
}
