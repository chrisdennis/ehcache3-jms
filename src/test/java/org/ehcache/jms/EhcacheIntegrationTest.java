package org.ehcache.jms;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.junit.Test;

import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.heap;

public class EhcacheIntegrationTest {

  @Test
  public void testEhcacheIntegration() {
    try (CacheManager manager = CacheManagerBuilder.newCacheManagerBuilder().using(new JmsReplication(new JmsConfiguration()))
            .withCache("foo", newCacheConfigurationBuilder(String.class, String.class, heap(100))).build(true)) {

      Cache<String, String> fooCache = manager.getCache("foo", String.class, String.class);
      Cache<String, String> barCache = manager.createCache("bar", newCacheConfigurationBuilder(String.class, String.class, heap(100)));

      fooCache.put("foo", "foo");
      barCache.put("bar", "bar");
    }
  }

  @Test
  public void testXmlIntegration() {
    try (CacheManager manager = CacheManagerBuilder.newCacheManager(new XmlConfiguration(EhcacheIntegrationTest.class.getResource("/ehcache-jms.xml")))) {
      manager.init();

      Cache<String, String> fooCache = manager.getCache("foo", String.class, String.class);
      Cache<String, String> barCache = manager.createCache("bar", newCacheConfigurationBuilder(String.class, String.class, heap(100)));

      fooCache.put("foo", "foo");
      barCache.put("bar", "bar");
    }
  }
}
