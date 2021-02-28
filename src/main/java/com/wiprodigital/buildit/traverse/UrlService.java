package com.wiprodigital.buildit.traverse;

import java.util.Collection;
import java.util.stream.Collectors;

public class UrlService {

    private final PageParser pageParser;
    private final String rootUrl;

    public UrlService(PageParser pageParser, String rootUrl) {
        this.pageParser = pageParser;
        this.rootUrl = rootUrl.endsWith("/") ? rootUrl.substring(0, rootUrl.lastIndexOf("/")) : rootUrl;
    }

    public Collection<String> linksOnPage(String url) {
        var links = pageParser.getLinksOnPage(url);
        return links.stream()
                .map(l -> l.startsWith("/") ? rootUrl + l : l)
                .collect(Collectors.toList());
    }
}
