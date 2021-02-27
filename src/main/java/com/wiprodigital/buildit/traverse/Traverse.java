package com.wiprodigital.buildit.traverse;

import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class Traverse {

    public static final String PARAMS_MSG = "One URL parameter required to run.";
    public static final String URL_MSG = "A valid URL is required to run.";
    private final URI uri;
    private final boolean verbose;

    private Traverse(URI uri, boolean verbose) {
        this.uri = uri;
        this.verbose = verbose;
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
            if (args.length > 1) {
                return Optional.of(new Traverse(uri, args[1].equalsIgnoreCase("v")));
            }
            return Optional.of(new Traverse(uri, false));
        } catch (URISyntaxException e) {
            output.println(URL_MSG);
            return Optional.empty();
        }

    }

    public boolean isVerbose() {
        return verbose;
    }

    private void run() {
        var mapper = new SiteMapper(new PageLoader());
        var siteMap = mapper.mapSite(uri.toASCIIString());
        System.out.println("Found " + siteMap.size() + " pages starting from " + uri.toASCIIString());
        siteMap.forEach((k, v) -> System.out.println("- " + k + ", no of links on page: " + v.size()));
        if (verbose) {
            siteMap.keySet().forEach(k -> {
                        System.out.println(k);
                        siteMap.get(k)
                                .forEach(v -> System.out.println("\t+---" + v));
                        System.out.println();
                        System.out.println("=========================================================");
                        System.out.println();
                    }
            );
        }
    }
}
