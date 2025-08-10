package com.team12.flightmanagement.service;

import com.team12.flightmanagement.repository.FlightRepository;
import com.team12.flightmanagement.repository.PassengerRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PassengerService}.
 */
@Generated
public class PassengerService__TestContext002_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'passengerService'.
   */
  private static BeanInstanceSupplier<PassengerService> getPassengerServiceInstanceSupplier() {
    return BeanInstanceSupplier.<PassengerService>forConstructor(PassengerRepository.class, FlightRepository.class)
            .withGenerator((registeredBean, args) -> new PassengerService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'passengerService'.
   */
  public static BeanDefinition getPassengerServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PassengerService.class);
    beanDefinition.setInstanceSupplier(getPassengerServiceInstanceSupplier());
    return beanDefinition;
  }
}
