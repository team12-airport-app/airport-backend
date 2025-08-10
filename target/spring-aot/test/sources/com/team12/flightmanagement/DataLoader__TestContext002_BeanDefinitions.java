package com.team12.flightmanagement;

import com.team12.flightmanagement.repository.AircraftRepository;
import com.team12.flightmanagement.repository.AirportRepository;
import com.team12.flightmanagement.repository.CityRepository;
import com.team12.flightmanagement.repository.FlightRepository;
import com.team12.flightmanagement.repository.PassengerRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link DataLoader}.
 */
@Generated
public class DataLoader__TestContext002_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'dataLoader'.
   */
  private static BeanInstanceSupplier<DataLoader> getDataLoaderInstanceSupplier() {
    return BeanInstanceSupplier.<DataLoader>forConstructor(CityRepository.class, AirportRepository.class, PassengerRepository.class, AircraftRepository.class, FlightRepository.class)
            .withGenerator((registeredBean, args) -> new DataLoader(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'dataLoader'.
   */
  public static BeanDefinition getDataLoaderBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(DataLoader.class);
    beanDefinition.setInstanceSupplier(getDataLoaderInstanceSupplier());
    return beanDefinition;
  }
}
