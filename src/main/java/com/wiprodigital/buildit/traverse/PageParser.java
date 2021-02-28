package com.wiprodigital.buildit.traverse;

import java.util.Collection;

public interface PageParser {

    Collection<String> getLinksOnPage(String url);
}
