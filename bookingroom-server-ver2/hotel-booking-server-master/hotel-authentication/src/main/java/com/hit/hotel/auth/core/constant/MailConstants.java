package com.hit.hotel.auth.core.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MailConstants {

    public static final String SIGNUP_SUBJECT = "Confirm account registration";
    public static final String SIGNUP_TEMPLATE = "SignUp.html";
    public static final String FORGOT_PASSWORD_SUBJECT = "Forgot password";
    public static final String FORGOT_PASSWORD_TEMPLATE = "ForgotPassword.html";
    public static final String ACCOUNT_LOCK_NOTICE_SUBJECT = "Account is locked";
    public static final String ACCOUNT_LOCK_NOTICE_TEMPLATE = "AccountLockNotice.html";

}
