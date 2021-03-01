package com.wiprodigital.buildit.traverse;

import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class Traverse {

    public static final String PARAMS_MSG = "One URL parameter required to run.";
    public static final String URL_MSG = "A valid URL is required to run.";
    private final URI uri;
    private final boolean summary;

    private Traverse(URI uri, boolean summary) {
        this.uri = uri;
        this.summary = summary;
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
                return Optional.of(new Traverse(uri, args[1].equalsIgnoreCase("s")));
            }
            return Optional.of(new Traverse(uri, false));
        } catch (URISyntaxException e) {
            output.println(URL_MSG);
            return Optional.empty();
        }

    }

    public boolean isSummary() {
        return summary;
    }

    private void run() {
        var rootUrl = uri.toASCIIString();
        var mapper = new SiteMapper(new UrlService(new JsoupPageParser()));
        var siteMap = mapper.mapSite(rootUrl);
        System.out.println("Found " + siteMap.size() + " pages starting from " + rootUrl);
        siteMap.forEach((k, v) -> System.out.println("- " + k + ", no of links on page: " + v.size()));
        if (!isSummary()) {
            siteMap.keySet().forEach(k -> {
                        System.out.println(k);
                        System.out.println("\t|");
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
