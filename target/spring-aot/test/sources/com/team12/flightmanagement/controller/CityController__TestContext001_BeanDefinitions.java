package com.team12.flightmanagement.controller;

import com.team12.flightmanagement.service.CityService;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link CityController}.
 */
@Generated
public class CityController__TestContext001_BeanDefinitions {
  /**
   * Get the bean instance supplier for 'cityController'.
   */
  private static BeanInstanceSupplier<CityController> getCityControllerInstanceSupplier() {
    return BeanInstanceSupplier.<CityController>forConstructor(CityService.class)
            .withGenerator((registeredBean, args) -> new CityController(args.get(0)));
  }

  /**
   * Get the bean definition for 'cityController'.
   */
  public static BeanDefinition getCityControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(CityController.class);
    beanDefinition.setInstanceSupplier(getCityControllerInstanceSupplier());
    return beanDefinition;
  }
}
