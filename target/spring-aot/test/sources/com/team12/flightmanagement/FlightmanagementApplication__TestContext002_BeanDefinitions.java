package com.team12.flightmanagement;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassUtils;

/**
 * Bean definitions for {@link FlightmanagementApplication}.
 */
@Generated
public class FlightmanagementApplication__TestContext002_BeanDefinitions {
  /**
   * Get the bean definition for 'flightmanagementApplication'.
   */
  public static BeanDefinition getFlightmanagementApplicationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(FlightmanagementApplication.class);
    beanDefinition.setTargetType(FlightmanagementApplication.class);
    ConfigurationClassUtils.initializeConfigurationClass(FlightmanagementApplication.class);
    beanDefinition.setInstanceSupplier(FlightmanagementApplication$$SpringCGLIB$$0::new);
    return beanDefinition;
  }
}
