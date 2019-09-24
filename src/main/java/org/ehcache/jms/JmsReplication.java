package org.ehcache.jms;

import org.ehcache.Cache;
import org.ehcache.Status;
import org.ehcache.core.events.CacheManagerListener;
import org.ehcache.core.spi.service.CacheManagerProviderService;
import org.ehcache.core.spi.store.InternalCacheManager;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventFiring;
import org.ehcache.event.EventOrdering;
import org.ehcache.event.EventType;
import org.ehcache.spi.service.Service;
import org.ehcache.spi.service.ServiceDependencies;
import org.ehcache.spi.service.ServiceProvider;

import java.util.EnumSet;

@ServiceDependencies(CacheManagerProviderService.class)
public class JmsReplication implements Service, CacheManagerListener {

  private InternalCacheManager cacheManager;

  public JmsReplication(JmsConfiguration configuration) {

  }

  @Override
  public void start(ServiceProvider<Service> serviceProvider) {
    this.cacheManager = serviceProvider.getService(CacheManagerProviderService.class).getCacheManager();
    this.cacheManager.registerListener(this);
  }

  @Override
  public void stop() {
  }

  @Override
  public void cacheAdded(String alias, Cache<?, ?> cache) {
    System.out.println("Cache Added: " + alias);
    cache.getRuntimeConfiguration().registerCacheEventListener(
            (CacheEventListener<Object, Object>) event -> System.out.println("Received [" + alias + "]: " + event),
            EventOrdering.ORDERED, EventFiring.SYNCHRONOUS, EnumSet.allOf(EventType.class)
    );
  }

  @Override
  public void cacheRemoved(String alias, Cache<?, ?> cache) {
    System.out.println("Cache Removed: " + alias);
  }

  @Override
  public void stateTransition(Status from, Status to) {
    if (Status.AVAILABLE.equals(to)) {
      cacheManager.getRuntimeConfiguration().getCacheConfigurations().forEach((alias, configuration) ->
              cacheAdded(alias, cacheManager.getCache(alias, configuration.getKeyType(), configuration.getValueType())));
    }
  }
}
