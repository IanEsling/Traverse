package com.wiprodigital.buildit.traverse;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JsoupPageParser implements PageParser {

    @Override
    public Collection<String> getLinksOnPage(String url) {
        try {
            var elements = Jsoup.parse(new URL(url), 10000).select("a");
            return elements.stream()
                    .map(e -> e.attr("href"))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error trying to create a URL using " + url);
            System.out.println("error: " + e);
            return List.of("error: " + e);
        }
    }
}
