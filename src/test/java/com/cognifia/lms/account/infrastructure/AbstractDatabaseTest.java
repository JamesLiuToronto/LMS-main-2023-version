package com.cognifia.lms.account.infrastructure;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@DataJpaTest
@ComponentScan(basePackages = "com.cognifia.lms")
@ContextConfiguration(classes = TestDbConfig.class)
public class AbstractDatabaseTest extends AbstractTestNGSpringContextTests {

}
