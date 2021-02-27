package com.wiprodigital.buildit.traverse;

import java.util.function.Predicate;

public class RootUrl {

    private final String root;
    private final Predicate<String> domainPredicate;

    public RootUrl(String root) {
        this(root, new DefaultDomainPredicate(root));
    }

    public RootUrl(String root, Predicate<String> domainPredicate) {
        this.root = root;
        this.domainPredicate = domainPredicate;
    }

    public boolean isPartOfDomain(String url) {
        return domainPredicate.test(url);
    }

    public String getUrl() {
        return root;
    }

    private static class DefaultDomainPredicate implements Predicate<String> {

        private final String rootUrl;

        DefaultDomainPredicate(String rootUrl) {
            this.rootUrl = rootUrl;
        }

        @Override
        public boolean test(String url) {
            return url.startsWith(rootUrl);
        }
    }
}
