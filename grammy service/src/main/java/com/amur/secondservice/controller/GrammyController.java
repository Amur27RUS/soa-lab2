package com.amur.secondservice.controller;

import com.amur.secondservice.enums.MusicGenre;
import com.amur.secondservice.exception.BadRequestException;
import com.amur.secondservice.exception.NotFoundException;
import com.amur.secondservice.service.GrammyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@RestController
@RequestMapping(value = "/grammy", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@CrossOrigin
public class GrammyController {
    private final GrammyService grammyService;

    public GrammyController(GrammyService grammyService){
        this.grammyService = grammyService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot";
    }

    @GetMapping("/band/{band-id}/nominate/{genre}")
    public ResponseEntity<?> nominateBand(@PathVariable("band-id") Integer id, @PathVariable("genre") String genre) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        System.out.println("TEST TEST TEsT TEST TEST TEST");
        grammyService.nominateBand(id, genre);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/band/{band-id}/reward/{genre}")
    public ResponseEntity<?> rewardBand(@PathVariable("band-id") Integer id, @PathVariable("genre") MusicGenre gerne) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        grammyService.rewardBand(id, gerne);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(NotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
