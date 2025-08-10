package com.team12.flightmanagement.service;

import com.team12.flightmanagement.repository.CityRepository;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CityService}.
 */
@Generated
public class CityService__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'cityService'.
   */
  private static BeanInstanceSupplier<CityService> getCityServiceInstanceSupplier() {
    return BeanInstanceSupplier.<CityService>forConstructor(CityRepository.class)
            .withGenerator((registeredBean, args) -> new CityService(args.get(0)));
  }

  /**
   * Get the bean definition for 'cityService'.
   */
  public static BeanDefinition getCityServiceBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CityService.class);
    beanDefinition.setInstanceSupplier(getCityServiceInstanceSupplier());
    return beanDefinition;
  }
}
