package com.ihl95.nuclear.e2e;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

/**
 * E2E test suite runner for NuclearPlant module.
 * Executes all Gherkin feature files using Cucumber.
 * Tests are realistic, user-focused scenarios with real HTTP requests.
 *
 * Features executed:
 * - src/test/resources/features/nuclearplant.feature
 *
 * Each scenario tests end-to-end workflows from HTTP request through database persistence.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.ihl95.nuclear.e2e.steps")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "src/test/resources/features")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "progress,html:target/cucumber-report.html")
public class NuclearPlantE2ETestSuite {
    // Suite configuration for running Cucumber features with Gherkin syntax
}

