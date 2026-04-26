package com.ihl95.nuclear.supplier.e2e;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

/**
 * E2E test suite runner for Supplier module.
 * Executes all Gherkin feature files using Cucumber with JUnit Platform.
 * Tests are realistic, user-focused scenarios with real HTTP requests.
 *
 * Features executed:
 * - src/test/resources/features/supplier.feature (6 scenarios)
 *
 * Each scenario tests end-to-end workflows from HTTP request through database persistence.
 *
 * Execution: mvn test -Dtest=SupplierE2ETest or just mvn test (auto-detected)
 */
@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.ihl95.nuclear.supplier.e2e.steps")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "classpath:features/supplier.feature")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "progress,html:target/cucumber-report-supplier.html")
public class SupplierE2ETest {
    // Suite configuration for running Cucumber features with Gherkin syntax
}

