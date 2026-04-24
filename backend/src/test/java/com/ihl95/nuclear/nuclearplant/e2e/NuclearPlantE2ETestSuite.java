package com.ihl95.nuclear.nuclearplant.e2e;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.Suite;

/**
 * E2E test suite runner for NuclearPlant module.
 * Executes all Gherkin feature files using Cucumber.
 * Tests are realistic, user-focused scenarios with real HTTP requests.
 * <p>
 * Features executed:
 * - src/test/resources/features/nuclearplant.feature
 * <p>
 * Each scenario tests end-to-end workflows from HTTP request through database persistence.
 */
@Suite
@IncludeEngines("cucumber")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.ihl95.nuclear.nuclearplant.e2e.steps")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "classpath:features/nuclearplant.feature")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "progress,html:target/cucumber-report.html")
public class NuclearPlantE2ETestSuite {
    // Suite configuration for running Cucumber features with Gherkin syntax
}



