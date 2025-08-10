package org.springframework.test.context.aot;

import com.team12.flightmanagement.ContextLoadsTest__TestContext001_ApplicationContextInitializer;
import com.team12.flightmanagement.api.ApiSmokeTest__TestContext002_ApplicationContextInitializer;
import java.lang.Class;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.aot.generate.Generated;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Generated mappings for {@link AotTestContextInitializers}.
 */
@Generated
public class AotTestContextInitializers__Generated {
  public static Map<String, Supplier<ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> getContextInitializers(
      ) {
    Map<String, Supplier<ApplicationContextInitializer<? extends ConfigurableApplicationContext>>> map = new HashMap<>();
    map.put("com.team12.flightmanagement.ContextLoadsTest", () -> new ContextLoadsTest__TestContext001_ApplicationContextInitializer());
    map.put("com.team12.flightmanagement.api.ApiSmokeTest", () -> new ApiSmokeTest__TestContext002_ApplicationContextInitializer());
    return map;
  }

  public static Map<String, Class<? extends ApplicationContextInitializer<?>>> getContextInitializerClasses(
      ) {
    Map<String, Class<? extends ApplicationContextInitializer<?>>> map = new HashMap<>();
    map.put("com.team12.flightmanagement.ContextLoadsTest", ContextLoadsTest__TestContext001_ApplicationContextInitializer.class);
    map.put("com.team12.flightmanagement.api.ApiSmokeTest", ApiSmokeTest__TestContext002_ApplicationContextInitializer.class);
    return map;
  }
}
