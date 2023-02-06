package com.cognifia.lms.tokenprocess;

import static com.cognifia.lms.tokenprocess.process.TokenType.Process.ACTIVATE;
import static com.cognifia.lms.tokenprocess.process.TokenType.Process.FAMILY_SETUP;
import static com.cognifia.lms.tokenprocess.process.TokenType.Process.RESET_PASSWORD;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.cognifia.lms.account.security.token.dto.TokenDTO;
import com.cognifia.lms.account.security.token.utility.TokenUtility;

@Service
@AllArgsConstructor
public class TokenService implements TokenServicePort {

    private TokenUtility tokenUtility;
    private PasswordEncoder passwordEncoder;

    @Override
    public String getActivateAccountToken(int userId, int updateUserId) {
        String keyValue = userId + "," + updateUserId;
        TokenDTO dto = TokenDTO.builder().type(ACTIVATE.name()).keyValue(keyValue).keyName("UserId").build();
        return tokenUtility.createJwtSignedHMAC(dto);
    }

    public String getResetPasswordToken(int userId, String password, int updateUserId) {
        String keyValue = userId + "," + password + "," + updateUserId;
        TokenDTO dto = TokenDTO.builder().type(RESET_PASSWORD.name()).keyValue(keyValue).keyName("UserId").build();
        return tokenUtility.createJwtSignedHMAC(dto);
    }

    @Override
    public String getFamilyMemberToken(int askedForUserId, int requestedByUserId) {
        String keyValue = askedForUserId + "," + requestedByUserId;
        TokenDTO dto = TokenDTO.builder().type(FAMILY_SETUP.name()).keyValue(keyValue)
                               .keyName(Integer.toString(requestedByUserId)).build();
        return tokenUtility.createJwtSignedHMAC(dto);
    }

    public String getEncryptedMessage(String message) {
        return passwordEncoder.encode(message);
    }
}
