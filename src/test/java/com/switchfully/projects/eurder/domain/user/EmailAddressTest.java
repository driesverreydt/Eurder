package com.switchfully.projects.eurder.domain.user;

import com.switchfully.projects.eurder.domain.exception.InvalidEmailStructureException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailAddressTest {

    @Test
    void givenInvalidEmailFormat_whenValidatingEmail_thenThrowInvalidEmailStructureException(){
        Assertions.assertThatExceptionOfType(InvalidEmailStructureException.class)
                .isThrownBy(() -> EmailAddress.parseEmailAddressString(""));
        Assertions.assertThatExceptionOfType(InvalidEmailStructureException.class)
                .isThrownBy(() -> EmailAddress.parseEmailAddressString("@example.com"));
        Assertions.assertThatExceptionOfType(InvalidEmailStructureException.class)
                .isThrownBy(() -> EmailAddress.parseEmailAddressString("mail@"));
        Assertions.assertThatExceptionOfType(InvalidEmailStructureException.class)
                .isThrownBy(() -> EmailAddress.parseEmailAddressString("not.an.email.not.mail.com"));
    }

}