package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.service.PassengerService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PassengerController}.
 */
@Generated
public class PassengerController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'passengerController'.
   */
  private static BeanInstanceSupplier<PassengerController> getPassengerControllerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<PassengerController>forConstructor(PassengerService.class)
            .withGenerator((registeredBean, args) -> new PassengerController(args.get(0)));
  }

  /**
   * Get the bean definition for 'passengerController'.
   */
  public static BeanDefinition getPassengerControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PassengerController.class);
    beanDefinition.setInstanceSupplier(getPassengerControllerInstanceSupplier());
    return beanDefinition;
  }
}
