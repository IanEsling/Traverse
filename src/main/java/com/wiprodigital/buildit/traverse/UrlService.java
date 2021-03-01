package com.wiprodigital.buildit.traverse;

import java.util.Collection;
import java.util.stream.Collectors;

public class UrlService {

    private final PageParser pageParser;

    public UrlService(PageParser pageParser) {
        this.pageParser = pageParser;
    }

    public Collection<String> linksOnPage(String url) {
        var links = pageParser.getLinksOnPage(url);
        return links.stream()
                .map(String::trim)
                .map(l -> l.endsWith("/") ? l.substring(0, l.lastIndexOf("/")) : l)
                .collect(Collectors.toSet());
    }
}
