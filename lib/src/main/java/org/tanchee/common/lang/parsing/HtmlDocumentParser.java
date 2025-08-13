package org.tanchee.common.lang.parsing;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlDocumentParser {
    public HtmlDocumentParser() {}

    public Document parseHtmlUrl(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
