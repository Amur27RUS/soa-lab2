package com.amur.secondservice.validation;


import com.amur.secondservice.entity.*;
import com.amur.secondservice.exception.EntityIsNotValidException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Service
public class EntityValidator {
    private Validator validator;

    public EntityValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private String formExceptionMsg(Set<ConstraintViolation<Object>> constraintViolations) {
        String errorMessage = "";
        for (ConstraintViolation<Object> violation : constraintViolations) {
            errorMessage = errorMessage.concat(violation.getMessage() + "\n");
        }
        return errorMessage;
    }

    public void validateNominations(Nominations nominations) throws EntityIsNotValidException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(nominations);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }
}
