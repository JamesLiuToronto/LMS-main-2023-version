package com.cognifia.lms.tokenprocess;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.cognifia.lms.account.security.token.dto.TokenDTO;
import com.cognifia.lms.account.security.token.utility.TokenUtility;
import com.cognifia.lms.common.exception.AppMessageException;
import com.cognifia.lms.tokenprocess.process.Processor;
import com.cognifia.lms.tokenprocess.process.ProcessorFactory;
import com.cognifia.lms.tokenprocess.process.TokenType.Process;

@Service
@AllArgsConstructor
public class TokenProcessService implements TokenProcessPort {
    private TokenUtility tokenUtility;
    private ProcessorFactory factory;

    //@LogMethodData
    public void process(String token) {
        TokenDTO dto = tokenUtility.getTokenDTOFromToken(token);
        if (dto.isExpired()) {
            throw new AppMessageException("process.toke.expired");
        }
        Processor processor = factory.getProcessor(Process.valueOf(dto.getType()));
        if (processor != null) {
            processor.runProcess(dto);
        }
    }
}
