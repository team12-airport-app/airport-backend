package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.service.AirportService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AirportCrudController}.
 */
@Generated
public class AirportCrudController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'airportCrudController'.
   */
  private static BeanInstanceSupplier<AirportCrudController> getAirportCrudControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AirportCrudController>forConstructor(AirportService.class)
            .withGenerator((registeredBean, args) -> new AirportCrudController(args.get(0)));
  }

  /**
   * Get the bean definition for 'airportCrudController'.
   */
  public static BeanDefinition getAirportCrudControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AirportCrudController.class);
    beanDefinition.setInstanceSupplier(getAirportCrudControllerInstanceSupplier());
    return beanDefinition;
  }
}
