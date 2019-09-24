package org.ehcache.jms.xml;

import org.ehcache.jms.JmsConfiguration;
import org.ehcache.jms.JmsReplication;
import org.ehcache.spi.service.ServiceCreationConfiguration;
import org.ehcache.xml.CacheManagerServiceConfigurationParser;
import org.w3c.dom.Element;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class JmsXmlConfigParser implements CacheManagerServiceConfigurationParser<JmsReplication> {

  private static final URI NAMESPACE = URI.create("http://www.ehcache.org/jms-replication");

  private static final URL SCHEMA = JmsXmlConfigParser.class.getResource("/jms-replication-ext.xsd");
  @Override
  public Source getXmlSchema() throws IOException {
    return new StreamSource(SCHEMA.openStream());
  }

  @Override
  public URI getNamespace() {
    return NAMESPACE;
  }

  @Override
  public ServiceCreationConfiguration<JmsReplication, ?> parseServiceCreationConfiguration(Element fragment, ClassLoader classLoader) {
    return new JmsConfiguration();
  }

  @Override
  public Class<JmsReplication> getServiceType() {
    return JmsReplication.class;
  }

  @Override
  public Element unparseServiceCreationConfiguration(ServiceCreationConfiguration<JmsReplication, ?> serviceCreationConfiguration) {
    throw new UnsupportedOperationException();
  }
}
