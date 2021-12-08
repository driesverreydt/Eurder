package com.switchfully.projects.eurder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EurderApplicationTest {

    @Test
    void mainTest() {
        EurderApplication.main(new String[] {});
        assertThat(true).isTrue();
        //Just to see the application starts up properly, I like green :)
    }
}