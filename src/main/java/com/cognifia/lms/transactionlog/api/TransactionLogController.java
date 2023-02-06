package com.cognifia.lms.transactionlog.api;

import com.cognifia.lms.transactionlog.dto.TransactionLogDTO;
import com.cognifia.lms.transactionlog.service.TransactionLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transactionlog")
@Tag(name = "Peak Training loging service Interface", description = "the API is for login with documentation annotations")
public class TransactionLogController {

    TransactionLogService service ;

    public TransactionLogController(TransactionLogService service) {
        this.service = service;
    }

    @GetMapping (value = "/{uuid}",
                 //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<TransactionLogDTO> getByuuid(
            @Parameter(description="uuId.",
                    required=true)
            @PathVariable String uuid ){

        return service.getTransactionlogsByUuid(uuid) ;
    }


    @PostMapping(value = "",
            //consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Save Transactionlog", description = "This will save transaction log")
    public void persistAudit(
            @Parameter(description="uuid.", required=true)
            @RequestParam("uuid") String uuid ,
            @RequestParam("typeCode") String typeCode ,
            @RequestParam("message") String message ,
            @RequestParam("statusCode") String statusCode ,
            @Parameter(description="userId", required=true)
            @RequestHeader("updateUserId") int  updateUserId ) {

        service.persistTransactionLog(uuid,typeCode,message,statusCode,updateUserId);
    }
}
