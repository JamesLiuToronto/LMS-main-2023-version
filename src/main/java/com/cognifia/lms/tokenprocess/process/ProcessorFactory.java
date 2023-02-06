package com.cognifia.lms.tokenprocess.process;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ProcessorFactory {

    private final Map<TokenType.Process, Processor> creators;

    public ProcessorFactory(Processor familyProcess, Processor accountProcess) {
        creators = new HashMap<TokenType.Process, Processor>() {
            {
                put(TokenType.Process.ACTIVATE, accountProcess);
                put(TokenType.Process.FAMILY_SETUP, familyProcess);
            }
        };
    }

    public Processor getProcessor(TokenType.Process type) {
        return type == null ? null : creators.get(type);
    }
}

