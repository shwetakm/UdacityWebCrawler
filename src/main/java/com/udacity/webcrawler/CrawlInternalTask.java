package com.udacity.webcrawler;

import com.udacity.webcrawler.parser.PageParser;
import com.udacity.webcrawler.parser.PageParserFactory;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CrawlInternalTask extends RecursiveTask<Boolean> {

    private final PageParserFactory parserFactory;
    private final Clock clock;
    private final String url;
    private final Instant deadline;
    private final int maxDepth;
    private final Map<String, Integer> counts;
    private final ConcurrentSkipListSet<String> visitedUrls;
    private final List<Pattern> ignoredUrls;

    public CrawlInternalTask(PageParserFactory parserFactory, Clock clock, String startingUrl, Instant deadline, int maxDepth, Map<String, Integer> counts, ConcurrentSkipListSet<String> visitedUrls, List<Pattern> ignoredUrls) {
        super();
        this.parserFactory = parserFactory;
        this.clock = clock;
        this.url = startingUrl;
        this.deadline = deadline;
        this.maxDepth = maxDepth;
        this.counts = counts;
        this.visitedUrls = visitedUrls;
        this.ignoredUrls = ignoredUrls;
    }

    @Override
    protected Boolean compute() {
        if (maxDepth == 0 || clock.instant().isAfter(deadline)) {
            return false;
        }
        for (Pattern pattern : ignoredUrls) {
            if (pattern.matcher(url).matches()) {
                return false;
            }
        }
        if (visitedUrls.contains(url)) {
            return false;
        }
        visitedUrls.add(url);
        PageParser.Result result = parserFactory.get(url).parse();
        for (Map.Entry<String, Integer> e : result.getWordCounts().entrySet()) {
            if (counts.containsKey(e.getKey())) {
                counts.put(e.getKey(), e.getValue() + counts.get(e.getKey()));
            } else {
                counts.put(e.getKey(), e.getValue());
            }
        }

        List<CrawlInternalTask> subtasks =
                result.getLinks().stream().map(url -> new CrawlInternalTask(parserFactory, clock,url, deadline, maxDepth - 1, counts, visitedUrls, ignoredUrls))
                        .collect(Collectors.toList());
        invokeAll(subtasks);

        return true;
    }


}
