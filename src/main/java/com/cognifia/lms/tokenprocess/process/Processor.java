package com.cognifia.lms.tokenprocess.process;

import com.cognifia.lms.account.security.token.dto.TokenDTO;

public interface Processor {
    void runProcess(TokenDTO dto);
}
