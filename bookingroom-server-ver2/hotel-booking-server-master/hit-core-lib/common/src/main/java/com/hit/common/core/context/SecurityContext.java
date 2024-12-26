package com.hit.common.core.context;

import com.hit.common.core.domain.model.SimpleSecurityUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContext {

    private static final InheritableThreadLocal<Map<String, Object>> contextManage = new InheritableThreadLocal<>();

    private static final String AUTHENTICATION = "AUTHENTICATION";

    public static void setSimpleSecurityUser(SimpleSecurityUser simpleSecurityUser) {
        if (contextManage.get() != null) {
            Map<String, Object> dataMap = contextManage.get();
            dataMap.put(AUTHENTICATION, simpleSecurityUser);
        } else {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put(AUTHENTICATION, simpleSecurityUser);
            contextManage.set(dataMap);
        }
    }

    public static SimpleSecurityUser getSimpleSecurityUser() {
        if (contextManage.get() != null &&
                contextManage.get().containsKey(AUTHENTICATION)) {
            return (SimpleSecurityUser) contextManage.get().get(AUTHENTICATION);
        }
        return null;
    }

    public static void clearContext() {
        contextManage.remove();
    }

}