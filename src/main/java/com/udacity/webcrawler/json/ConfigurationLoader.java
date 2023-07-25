package com.udacity.webcrawler.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A static utility class that loads a JSON configuration file.
 */
public final class ConfigurationLoader {

  private final Path path;

  /**
   * Create a {@link ConfigurationLoader} that loads configuration from the given {@link Path}.
   */
  public ConfigurationLoader(Path path) {
    this.path = Objects.requireNonNull(path);
  }

  /**
   * Loads configuration from this {@link ConfigurationLoader}'s path
   *
   * @return the loaded {@link CrawlerConfiguration}.
   */
  public CrawlerConfiguration load() throws IOException {
    // TODO: Fill in this method.

    ObjectMapper configurationMapper = new ObjectMapper();
    configurationMapper.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    CrawlerConfiguration crawlerConfiguration = configurationMapper.readValue(new File(path.toString()), CrawlerConfiguration.Builder.class).build();

    return crawlerConfiguration;
  }

  /**
   * Loads crawler configuration from the given reader.
   *
   * @param reader a Reader pointing to a JSON string that contains crawler configuration.
   * @return a crawler configuration
   */
  public static CrawlerConfiguration read(Reader reader) throws IOException {
    // This is here to get rid of the unused variable warning.
    Objects.requireNonNull(reader);

    // TODO: Fill in this method
    ObjectMapper configurationMapper = new ObjectMapper();
    configurationMapper.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    CrawlerConfiguration crawlerConfiguration = configurationMapper.readValue(reader, CrawlerConfiguration.Builder.class).build();

    return crawlerConfiguration;
  }
}
