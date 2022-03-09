package com.amur.secondservice.controller;

import com.amur.secondservice.enums.MusicGenre;
import com.amur.secondservice.generated.GetNominateRequest;
import com.amur.secondservice.generated.GetNominateResponse;
import com.amur.secondservice.generated.GetRewardRequest;
import com.amur.secondservice.generated.GetRewardResponse;
import com.amur.secondservice.service.GrammyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;


@Endpoint
public class GrammyEndpoint {
    private static final String NAMESPACE_URI = "http://www.amur.com/secondservice/generated";

    private final GrammyService grammyService;

    public GrammyEndpoint(GrammyService grammyService){
        this.grammyService = grammyService;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getNominateRequest")
    @ResponsePayload
    public GetNominateResponse nominateBand(@RequestPayload GetNominateRequest request) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        grammyService.nominateBand(request.getId().intValue(), request.getGenre().toString());
        GetNominateResponse response = new GetNominateResponse();
        response.setResponseCode(BigInteger.valueOf(200));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRewardRequest")
    @ResponsePayload
    public GetRewardResponse rewardBand(@RequestPayload GetRewardRequest request) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        grammyService.rewardBand(request.getId().intValue(), request.getGenre().toString());
        GetRewardResponse response = new GetRewardResponse();
        response.setResponseCode(BigInteger.valueOf(200));
        return response;
    }
}
