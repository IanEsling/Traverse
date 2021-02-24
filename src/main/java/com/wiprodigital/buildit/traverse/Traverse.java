package com.wiprodigital.buildit.traverse;

import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class Traverse {

    public static final String PARAMS_MSG = "One URL parameter required to run.";
    public static final String URL_MSG = "A valid URL is required to run.";
    private final URI uri;

    private Traverse(URI uri) {
        this.uri = uri;
    }

    public static void main(String... args) {
        var traverse = createFromArgs(args, System.out);
        traverse.ifPresent(Traverse::run);
    }

    public static Optional<Traverse> createFromArgs(String[] args, PrintStream output) {
        if (args == null || args.length == 0) {
            output.println(PARAMS_MSG);
            return Optional.empty();
        }
        try {
            var uri = new URI(args[0]);
            return Optional.of(new Traverse(uri));
        } catch (URISyntaxException e) {
            output.println(URL_MSG);
            return Optional.empty();
        }

    }

    private void run() {

    }
}
