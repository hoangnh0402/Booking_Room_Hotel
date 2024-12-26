package com.hit.common.core.tracker;

import com.hit.common.utils.CollectionUtils;
import com.hit.common.utils.StringUtils;
import com.hit.common.utils.TimeUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectChangeTracker {

    public static List<FieldChange> getFieldChanges(Object originalObject, Object modifiedObject) {
        Field[] originalFields = originalObject.getClass().getDeclaredFields();
        Field[] modifiedFields = modifiedObject.getClass().getDeclaredFields();
        Map<String, Field> originalFieldMap = CollectionUtils.toMap(originalFields, Field::getName);
        List<FieldChange> changes = new ArrayList<>();
        for (Field modifiedField : modifiedFields) {
            Field originalField = originalFieldMap.getOrDefault(modifiedField.getName(), null);
            if (!isChangeTrackerIgnore(modifiedField) && originalField != null && modifiedField.getType().equals(originalField.getType())) {
                addFieldChange(originalObject, modifiedObject, modifiedField, originalField, changes);
            }
        }
        return changes;
    }

    private static void addFieldChange(Object originalObject, Object modifiedObject,
                                       Field modifiedField, Field originalField,
                                       List<FieldChange> changes) {
        try {
            originalField.setAccessible(true);
            modifiedField.setAccessible(true);
            Object originalValue = originalField.get(originalObject);
            Object modifiedValue = modifiedField.get(modifiedObject);
            if (!isEqual(originalValue, modifiedValue)) {
                String fieldName = StringUtils.camelToSnake(originalField.getName()).toUpperCase();
                changes.add(new FieldChange(fieldName, toStringOfValue(originalValue), toStringOfValue(modifiedValue)));
            }
        } catch (Exception e) {
            log.error("addFieldChange with Object = {}, Field = {} ERROR",
                    originalObject.getClass().getName(), originalField.getName());
        }
    }

    private static boolean isChangeTrackerIgnore(Field originalField) {
        Annotation[] annotations = originalField.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(ChangeTrackerIgnore.class)) {
                return ((ChangeTrackerIgnore) annotation).value();
            }
        }
        return false;
    }

    private static boolean isEqual(Object originalValue, Object modifiedValue) {
        if (originalValue instanceof String originValueStr && modifiedValue instanceof String modifiedValueStr) {
            return (originValueStr).trim().equals((modifiedValueStr).trim());
        } else if (originalValue instanceof Date originalValueDate && modifiedValue instanceof Date modifiedValueDate) {
            return (originalValueDate).compareTo(modifiedValueDate) == 0;
        } else if (originalValue instanceof LocalDate originalValueDate && modifiedValue instanceof LocalDate modifiedValueDate) {
            return (originalValueDate).isEqual(modifiedValueDate);
        } else if (originalValue instanceof LocalDateTime originalValueDateTime && modifiedValue instanceof LocalDateTime modifiedValueDateTime) {
            return (originalValueDateTime).isEqual(modifiedValueDateTime);
        } else {
            return Objects.equals(originalValue, modifiedValue);
        }
    }

    private static String toStringOfValue(Object value) {
        if (value instanceof Date date) {
            return TimeUtils.dateToString(date, TimeUtils.DATE_TIME_PATTERN);
        } else if (value instanceof LocalDate localDate) {
            return TimeUtils.formatLocalDate(localDate, TimeUtils.DATE_PATTERN);
        } else if (value instanceof LocalDateTime localDateTime) {
            return TimeUtils.formatLocalDateTime(localDateTime, TimeUtils.DATE_TIME_PATTERN);
        } else {
            return value == null ? "NULL" : value.toString();
        }
    }
}
