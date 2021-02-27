package com.wiprodigital.buildit.traverse;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.stream.Collectors;

public class PageLoader {

    public Collection<String> linksOnPage(String url) {
        try {
            var elements = Jsoup.parse(new URL(url), 10000).select("a");
            return elements.stream()
                    .map(e -> e.attr("href"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Cannot create a URL using " + url);
            throw new RuntimeException(e);
        }
    }
}
