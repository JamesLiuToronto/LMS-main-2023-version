package com.cognifia.lms.common.transactionlog;

public class TransactionLogType {

    public enum TX_ACCOUNT {
        NEW_USER, UPDATE_USER, DELETE_USER, UPDATE_PERSON, CHANGE_EMAIL, DEACTIVATE_USER, ACTIVATE_USER,
        ASSIGN_USERGROUP, DISABLE_USERGROUP
    }

    public enum TX_LOGIN {
        LOGIN, CHANGE_PASSWORD, UNLOCK_USER, CHANGE_LOGIN_SOURCE
    }

    public enum STATUS {
        REQUEST(1), COMPLETE(2), FAIL(3);

        private final int value;

        STATUS(final int newValue) {
            value = newValue;
        }

        public static STATUS valueOf(int s) {
            for (STATUS v : values()) {
                if (v.getValue() == s) {
                    return v;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }
    }
}
