package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.service.AircraftService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AircraftController}.
 */
@Generated
public class AircraftController__TestContext002_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'aircraftController'.
   */
  private static BeanInstanceSupplier<AircraftController> getAircraftControllerInstanceSupplier() {
    return BeanInstanceSupplier.<AircraftController>forConstructor(AircraftService.class)
            .withGenerator((registeredBean, args) -> new AircraftController(args.get(0)));
  }

  /**
   * Get the bean definition for 'aircraftController'.
   */
  public static BeanDefinition getAircraftControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AircraftController.class);
    beanDefinition.setInstanceSupplier(getAircraftControllerInstanceSupplier());
    return beanDefinition;
  }
}
