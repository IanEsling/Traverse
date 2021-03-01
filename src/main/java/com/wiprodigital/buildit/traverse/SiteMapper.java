package com.wiprodigital.buildit.traverse;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SiteMapper {

    private final UrlService urlService;

    public SiteMapper(UrlService urlService) {
        this.urlService = urlService;
    }

    public Map<String, Collection<String>> mapSite(String rootUrl) {
        return mapSite(new RootUrl(rootUrl));
    }

    public Map<String, Collection<String>> mapSite(RootUrl root) {
        var rootUrl = root.getUrl();
        Map<String, Collection<String>> map = new HashMap<>();
        var rootUrls = urlService.linksOnPage(rootUrl);
        var urlsToVisit = new LinkedList<>(rootUrls);
        map.put(rootUrl, rootUrls);
        while (urlsToVisit.size() > 0) {
            var url = urlsToVisit.poll();
            if (root.isPartOfDomain(url) && !map.containsKey(url)) {
                var urls = urlService.linksOnPage(url);
                map.put(url, urls);
                urlsToVisit.addAll(urls);
            }
            System.out.print(".");
        }
        return map;
    }
}
