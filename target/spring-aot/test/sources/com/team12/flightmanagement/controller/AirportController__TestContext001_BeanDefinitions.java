package com.team12.flightmanagement.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AirportController}.
 */
@Generated
public class AirportController__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'airportController'.
   */
  public static BeanDefinition getAirportControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AirportController.class);
    InstanceSupplier<AirportController> instanceSupplier = InstanceSupplier.using(AirportController::new);
    instanceSupplier = instanceSupplier.andThen(AirportController__TestContext001_Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
