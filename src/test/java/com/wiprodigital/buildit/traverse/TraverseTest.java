package com.wiprodigital.buildit.traverse;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.mockito.Mockito.*;

class TraverseTest {

    @Test
    void printMessageIfNoParams() {
        var output = mock(PrintStream.class);

        Traverse.createFromArgs(null, output);
        Traverse.createFromArgs(new String[]{}, output);

        verify(output, times(2)).println(Traverse.PARAMS_MSG);
    }

    @Test
    void printMessageIfNotValidUrl() {
        var output = mock(PrintStream.class);

        Traverse.createFromArgs(new String[]{"I am not a valid URL"}, output);

        verify(output).println(Traverse.URL_MSG);
    }
}