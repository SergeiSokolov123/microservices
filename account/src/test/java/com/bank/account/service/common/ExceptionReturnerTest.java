package com.bank.account.service.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionReturnerTest {

    @Test
    @DisplayName("Тест проброски исключения entityNotFoundException")
    public void getEntityNotFoundExceptionTest() {

        ExceptionReturner exceptionReturner = new ExceptionReturner();
        String message = "Entity not found";

        EntityNotFoundException exception = exceptionReturner.getEntityNotFoundException(message);

        assertThat(message).isEqualTo(exception.getMessage());
    }
}
