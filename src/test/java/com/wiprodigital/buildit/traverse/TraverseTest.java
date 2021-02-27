package com.wiprodigital.buildit.traverse;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TraverseTest {

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

    @Test
    void setVerboseOutput() {
        var output = mock(PrintStream.class);

        var app = Traverse.createFromArgs(new String[]{"https://google.com", "v"}, output);

        assertThat(app).isPresent();
        assertThat(app.get().isVerbose()).isTrue();

        app = Traverse.createFromArgs(new String[]{"https://google.com", "V"}, output);

        assertThat(app).isPresent();
        assertThat(app.get().isVerbose()).isTrue();

        app = Traverse.createFromArgs(new String[]{"https://google.com", "x"}, output);

        assertThat(app).isPresent();
        assertThat(app.get().isVerbose()).isFalse();
    }
}