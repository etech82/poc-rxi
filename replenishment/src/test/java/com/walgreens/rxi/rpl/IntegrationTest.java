package com.walgreens.rxi.rpl;

import com.walgreens.rxi.rpl.RedisTestContainerExtension;
import com.walgreens.rxi.rpl.ReplenishmentApp;
import com.walgreens.rxi.rpl.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { ReplenishmentApp.class, TestSecurityConfiguration.class })
@ExtendWith(RedisTestContainerExtension.class)
public @interface IntegrationTest {
}
