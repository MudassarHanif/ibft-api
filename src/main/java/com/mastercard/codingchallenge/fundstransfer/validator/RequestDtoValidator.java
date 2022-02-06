package com.mastercard.codingchallenge.fundstransfer.validator;

import com.mastercard.codingchallenge.fundstransfer.exception.InvalidDataException;
import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.util.Strings;

public class RequestDtoValidator {

  private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public static <T> void validate(T obj) throws InvalidDataException {
    String message = getValidationMessage(obj);
    if (!Strings.isNullOrEmpty(message))
      throw new InvalidDataException(message);
  }

  public static <T> String getValidationMessage(T obj) {
    Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
    StringBuilder message = new StringBuilder();
    if (!constraintViolations.isEmpty()) {
      Iterator<ConstraintViolation<T>> it = constraintViolations.iterator();
      message.append("Invalid data: ");
      while (it.hasNext()) {
        message.append(it.next().getMessage());
        if (it.hasNext()) {
          message.append(" ");
        }
      }
    }
    return message.toString();
  }
}
