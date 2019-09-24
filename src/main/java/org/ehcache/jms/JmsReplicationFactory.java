package org.ehcache.jms;

import org.ehcache.core.spi.service.ServiceFactory;
import org.ehcache.spi.service.ServiceCreationConfiguration;

public class JmsReplicationFactory implements ServiceFactory<JmsReplication> {
  @Override
  public JmsReplication create(ServiceCreationConfiguration<JmsReplication, ?> configuration) {
    if (configuration instanceof JmsConfiguration) {
      return new JmsReplication((JmsConfiguration) configuration);
    } else {
      throw new IllegalArgumentException("Unexpected configuration type " + configuration.getClass());
    }
  }

  @Override
  public Class<? extends JmsReplication> getServiceType() {
    return JmsReplication.class;
  }
}
