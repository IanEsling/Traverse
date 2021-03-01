package com.wiprodigital.buildit.traverse;

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

    @Mock
    PageParser pageParser;

    @InjectMocks
    UrlService testee;

    @Test
    void returnLinksFromParser() {
        var url = "pageUrl";
        when(pageParser.getLinksOnPage(url)).thenReturn(List.of("a", "b", "c"));

        var links = testee.linksOnPage(url);
        assertThat(links).containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void allLinksHaveSlashesRemoved() {
        var url = "pageUrl";
        when(pageParser.getLinksOnPage(url)).thenReturn(List.of("a/", "b", "c/"));

        var links = testee.linksOnPage(url);
        assertThat(links).containsExactlyInAnyOrder("a", "b", "c");
    }

    @Test
    void removeDuplicateLinks() {
        var url = "pageUrl";
        when(pageParser.getLinksOnPage(url)).thenReturn(List.of("a", "a", "b"));

        var links = testee.linksOnPage(url);
        assertThat(links).containsExactlyInAnyOrder("a", "b");
    }

    @Test
    void trimTrailingSpacesFromLinks() {
        var url = "pageUrl";
        when(pageParser.getLinksOnPage(url)).thenReturn(List.of("a ", "a", "b"));

        var links = testee.linksOnPage(url);
        assertThat(links).containsExactlyInAnyOrder("a", "b");
    }
}