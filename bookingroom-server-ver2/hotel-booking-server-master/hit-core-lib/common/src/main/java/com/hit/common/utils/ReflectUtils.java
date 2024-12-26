package com.hit.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;

@Log4j2
@UtilityClass
public class ReflectUtils {

    public static Object invokeMethodByName(Object obj, String methodName, Object... args) {
        java.lang.reflect.Method method;
        try {
            method = obj.getClass().getMethod(methodName);
            if (args == null || args.length == 0)
                return method.invoke(obj);
            return method.invoke(obj, args);
        } catch (SecurityException | NoSuchMethodException e) {
            log.error("[method " + methodName + "not found] cause: {}", e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("[fail to invoke method] cause: {}", e.getMessage(), e);
        }
        return null;
    }
}
