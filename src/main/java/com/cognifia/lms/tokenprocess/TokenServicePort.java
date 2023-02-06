package com.cognifia.lms.tokenprocess;

public interface TokenServicePort {

    String getFamilyMemberToken(int askedForUserId, int requestedByUserId);

    String getEncryptedMessage(String message);

    String getResetPasswordToken(int userId, String password, int updateUerId);

    String getActivateAccountToken(int userId, int updateUserId);
}
