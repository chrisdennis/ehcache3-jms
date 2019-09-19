package org.ehcache.jms;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.junit.Test;

import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;

public class EhcacheIntegrationTest {

  @Test
  public void testEhcacheIntegration() {
    try (CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder().using(new JmsReplication())
            .withCache("foo", newCacheConfigurationBuilder(String.class, String.class, heap(100))).build(true)) {

      Cache<String, String> fooCache = manager.getCache("foo", String.class, String.class);
      Cache<String, String> barCache = manager.createCache("bar", newCacheConfigurationBuilder(String.class, String.class, heap(100)));

      fooCache.put("foo", "foo");
      barCache.put("bar", "bar");
    }
  }
}
