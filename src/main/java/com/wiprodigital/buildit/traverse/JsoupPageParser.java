package com.wiprodigital.buildit.traverse;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JsoupPageParser implements PageParser {

    @Override
    public Collection<String> getLinksOnPage(String url) {
        try {
            var doc = Jsoup.connect(url).ignoreContentType(true).get();
            var anchorElements = doc.select("a[href]");
            var mediaElements = doc.select("[src]");
            var linkElements = doc.select("link[href]");

            var links = anchorElements.stream()
                    .map(a -> a.attr("abs:href"))
                    .collect(Collectors.toSet());
            links.addAll(mediaElements.stream()
                    .map(m -> m.attr("abs:src"))
                    .collect(Collectors.toSet()));
            links.addAll(linkElements.stream()
                    .map(l -> l.attr("abs:href"))
                    .collect(Collectors.toSet()));

            return links;
        } catch (IOException e) {
            return List.of("error: " + e);
        }
    }
}
