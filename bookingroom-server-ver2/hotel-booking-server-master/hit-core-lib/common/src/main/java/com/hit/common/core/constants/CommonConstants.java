package com.hit.common.core.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstants {

    public static final String SORT_TYPE_ASC = "ASC";
    public static final String SORT_TYPE_DESC = "DESC";
    public static final Integer PAGE_SIZE_DEFAULT = 10;
    public static final Integer ZERO_INT_VALUE = 0;
    public static final Integer ONE_INT_VALUE = 1;
    public static final Long ZERO_VALUE = 0L;
    public static final Long ONE_VALUE = 1L;

    public static final Integer DAYS_TO_DELETE_RECORDS = 30;
    public static final Integer HOURS_IN_A_DAY = 24;
    public static final Long LATE_CHECKIN_HOURS = 8L;
    public static final Integer MAX_MESSAGE_CHAT_BOT = 10;

    public static final String EMPTY_STRING = "";
    public static final String CREATE_SUCCESS = "Create successfully";
    public static final String UPDATE_SUCCESS = "Update successfully";
    public static final String DELETE_SUCCESS = "Delete successfully";
    public static final String RESTORE_SUCCESS = "Restore successfully";
    public static final String LOCK_SUCCESS = "Lock successfully";
    public static final String UNLOCK_SUCCESS = "Unlock successfully";

}
