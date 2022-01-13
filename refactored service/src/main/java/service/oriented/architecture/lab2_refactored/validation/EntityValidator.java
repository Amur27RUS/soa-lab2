package service.oriented.architecture.lab2_refactored.validation;


import org.springframework.stereotype.Service;
import service.oriented.architecture.lab2_refactored.entity.*;
import service.oriented.architecture.lab2_refactored.exceptions.EntityIsNotValidException;

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

    public void validateMusicBand(MusicBand musicBand) throws EntityIsNotValidException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(musicBand);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }

    public void validateAlbum(Album album) throws EntityIsNotValidException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(album);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }

    public void validateCoordinates(Coordinates movie) throws EntityIsNotValidException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(movie);
        if (!constraintViolations.isEmpty())
            throw new EntityIsNotValidException(formExceptionMsg(constraintViolations));
    }

}
