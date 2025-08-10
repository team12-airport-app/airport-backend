package com.team12.flightmanagement.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link TestController}.
 */
@Generated
public class TestController__TestContext001_BeanDefinitions {
  /**
   * Get the bean definition for 'testController'.
   */
  public static BeanDefinition getTestControllerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(TestController.class);
    beanDefinition.setInstanceSupplier(TestController::new);
    return beanDefinition;
  }
}
