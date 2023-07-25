package com.udacity.webcrawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParserFactory;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final public class CrawlInternalTaskBuilder {

    private final Clock clock;
    private final String url;
    private final Instant deadline;
    private final int maxDepth;
    private final Map<String, Integer> counts;
    private final ConcurrentSkipListSet<String> visitedUrls;
    private final List<Pattern> ignoredUrls;
   private final PageParserFactory parserFactory;


    private CrawlInternalTaskBuilder(Builder builder) {
        super();
        this.parserFactory = builder.parserFactory;
        this.clock = builder.clock;
        this.url = builder.url;
        this.deadline = builder.deadline;
        this.maxDepth = builder.maxDepth;
        this.counts = builder.counts;
        this.visitedUrls = builder.visitedUrls;
        this.ignoredUrls = builder.ignoredUrls;
    }

    public Clock getClock() {
        return clock;
    }

    public String getUrl() {
        return url;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public ConcurrentSkipListSet<String> getVisitedUrls() {
        return visitedUrls;
    }

    public List<Pattern> getIgnoredUrls() {
        return ignoredUrls;
    }

    public PageParserFactory getParserFactory() {
        return parserFactory;
    }

    public static class Builder{
        private Clock clock;
        private String url;
        private Instant deadline;
        private int maxDepth;
        private Map<String, Integer> counts;
        private ConcurrentSkipListSet<String> visitedUrls;
        private List<Pattern> ignoredUrls;
        private PageParserFactory parserFactory;

        public static Builder newInstance(){
            return new Builder();
        }

        private Builder(){}

        public Builder setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setDeadline(Instant deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder setMaxDepth(int maxDepth) {
            this.maxDepth = maxDepth;
            return this;
        }

        public Builder setCounts(Map<String, Integer> counts) {
            this.counts = counts;
            return this;
        }

        public Builder setVisitedUrls(ConcurrentSkipListSet<String> visitedUrls) {
            this.visitedUrls = visitedUrls;
            return this;
        }

        public Builder setIgnoredUrls(List<Pattern> ignoredUrls) {
            this.ignoredUrls = ignoredUrls;
            return this;
        }

        public Builder setParserFactory(PageParserFactory parserFactory) {
            this.parserFactory = parserFactory;
            return this;
        }

        public CrawlInternalTaskBuilder build(){
            return new CrawlInternalTaskBuilder(this);
        }
    }

}
