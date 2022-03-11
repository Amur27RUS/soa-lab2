package com.amur.secondservice.exception;


import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "404")
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

}

