package com.team12.flightmanagement.service;

import com.team12.flightmanagement.repository.AircraftRepository;
import com.team12.flightmanagement.repository.FlightRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AircraftService}.
 */
@Generated
public class AircraftService__TestContext002_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'aircraftService'.
   */
  private static BeanInstanceSupplier<AircraftService> getAircraftServiceInstanceSupplier() {
    return BeanInstanceSupplier.<AircraftService>forConstructor(AircraftRepository.class, FlightRepository.class)
            .withGenerator((registeredBean, args) -> new AircraftService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'aircraftService'.
   */
  public static BeanDefinition getAircraftServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AircraftService.class);
    beanDefinition.setInstanceSupplier(getAircraftServiceInstanceSupplier());
    return beanDefinition;
  }
}
