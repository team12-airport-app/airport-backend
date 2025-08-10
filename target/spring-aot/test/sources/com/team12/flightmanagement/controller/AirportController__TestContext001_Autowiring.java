package com.team12.flightmanagement.controller;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link AirportController}.
 */
@Generated
public class AirportController__TestContext001_Autowiring {
  /**
   * Apply the autowiring.
   */
  public static AirportController apply(RegisteredBean registeredBean, AirportController instance) {
    AutowiredFieldValueResolver.forRequiredField("airportRepository").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
