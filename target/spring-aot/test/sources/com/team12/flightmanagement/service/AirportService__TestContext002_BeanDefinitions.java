package com.team12.flightmanagement.service;

import com.team12.flightmanagement.repository.AirportRepository;
import com.team12.flightmanagement.repository.CityRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link AirportService}.
 */
@Generated
public class AirportService__TestContext002_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'airportService'.
   */
  private static BeanInstanceSupplier<AirportService> getAirportServiceInstanceSupplier() {
    return BeanInstanceSupplier.<AirportService>forConstructor(AirportRepository.class, CityRepository.class)
            .withGenerator((registeredBean, args) -> new AirportService(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'airportService'.
   */
  public static BeanDefinition getAirportServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AirportService.class);
    beanDefinition.setInstanceSupplier(getAirportServiceInstanceSupplier());
    return beanDefinition;
  }
}
