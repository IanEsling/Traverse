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
public class SiteMapperTest {

    @Mock
    PageLoader pageLoader;
    @InjectMocks
    SiteMapper siteMapper;

    @Test
    void mapSite() {
        when(pageLoader.linksOnPage("a")).thenReturn(List.of("b", "e"));
        when(pageLoader.linksOnPage("b")).thenReturn(List.of("c", "d"));
        when(pageLoader.linksOnPage("e")).thenReturn(List.of("f", "g"));

        var map = siteMapper.mapSite(new RootUrl("a", s -> true));

        assertThat(map).hasSize(7);
        assertThat(map.keySet()).containsExactlyInAnyOrder("a","b","c","d","e","f","g");
        assertThat(map.get("a")).containsExactlyInAnyOrder("b", "e");
        assertThat(map.get("b")).containsExactlyInAnyOrder("c", "d");
        assertThat(map.get("e")).containsExactlyInAnyOrder("f", "g");
    }

    @Test
    void doNotRecurse(){
        when(pageLoader.linksOnPage("a")).thenReturn(List.of("b", "e"));
        when(pageLoader.linksOnPage("b")).thenReturn(List.of("a", "e"));
        when(pageLoader.linksOnPage("e")).thenReturn(List.of("a", "b"));

        var map = siteMapper.mapSite(new RootUrl("a", s -> true));

        assertThat(map).hasSize(3);
        assertThat(map.keySet()).containsExactlyInAnyOrder("a","b","e");
        assertThat(map.get("a")).containsExactlyInAnyOrder("b", "e");
        assertThat(map.get("b")).containsExactlyInAnyOrder("a", "e");
        assertThat(map.get("e")).containsExactlyInAnyOrder("a", "b");
    }

    @Test
    void onlyMapLinksToSameDomain(){
        when(pageLoader.linksOnPage("domain")).thenReturn(List.of("domain-1", "not-domain"));
        when(pageLoader.linksOnPage("domain-1")).thenReturn(List.of("domain-2", "domain-3", "also-not-domain"));
        when(pageLoader.linksOnPage("domain-2")).thenReturn(List.of());
        when(pageLoader.linksOnPage("domain-3")).thenReturn(List.of());

        var map = siteMapper.mapSite("domain");

        assertThat(map).hasSize(4);
        assertThat(map.keySet()).containsExactlyInAnyOrder("domain","domain-1","domain-2", "domain-3");
        assertThat(map.get("domain")).containsExactlyInAnyOrder("domain-1", "not-domain");
        assertThat(map.get("domain-1")).containsExactlyInAnyOrder("domain-2", "domain-3", "also-not-domain");
        assertThat(map.get("domain-2")).containsExactlyInAnyOrder();
        assertThat(map.get("domain-3")).containsExactlyInAnyOrder();
    }
}
