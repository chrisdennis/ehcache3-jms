package org.ehcache.jms;

import org.ehcache.spi.service.ServiceCreationConfiguration;

public class JmsConfiguration implements ServiceCreationConfiguration<JmsReplication, Void> {

  @Override
  public Class<JmsReplication> getServiceType() {
    return JmsReplication.class;
  }
}
