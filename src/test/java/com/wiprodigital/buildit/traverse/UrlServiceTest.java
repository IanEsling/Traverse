package com.wiprodigital.buildit.traverse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    String rootUrl = "http://root.url";

    @Mock
    PageParser pageParser;

    UrlService testee;

    @BeforeEach
    void setup(){
        testee = new UrlService(pageParser, rootUrl);
    }

    @Test
    void returnLinksFromParser() {
        var url = "pageUrl";
        when(pageParser.getLinksOnPage(url)).thenReturn(List.of("a", "b", "c"));

        var links = testee.linksOnPage(url);
        assertThat(links).containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void reformatLocalLinksToRootUrl(){
        var url = "pageUrl";
        when(pageParser.getLinksOnPage(url)).thenReturn(List.of("/local", "not-local", "/more-local"));

        var links = testee.linksOnPage(url);
        assertThat(links).containsExactlyInAnyOrder(rootUrl + "/local", "not-local", rootUrl + "/more-local");
    }

    @Test
    void reformatLocalLinksWhenRootUrlHasTrailingSlash(){
        testee = new UrlService(pageParser, rootUrl + "/");
        var url = "pageUrl";
        when(pageParser.getLinksOnPage(url)).thenReturn(List.of("/local", "not-local", "/more-local"));

        var links = testee.linksOnPage(url);
        assertThat(links).containsExactlyInAnyOrder(rootUrl + "/local", "not-local", rootUrl + "/more-local");
    }
}