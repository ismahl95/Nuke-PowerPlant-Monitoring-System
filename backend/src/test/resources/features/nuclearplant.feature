Feature: NuclearPlant CRUD Operations
  As a system administrator
  I want to manage nuclear power plants
  So that I can keep track of multiple nuclear facilities

  Background:
    Given the authentication server is available
    And I have a valid JWT token

  Scenario: Create a new nuclear plant
    Given I want to create a new nuclear plant
    When I send a POST request with plant name "Planta Zaragoza" and location "Zaragoza"
    Then the response status should be 201
    And the response should contain the plant name "Planta Zaragoza"
    And the response should contain the plant location "Zaragoza"
    And the new plant should be persisted in the database

  Scenario: Retrieve all nuclear plants
    Given there are nuclear plants in the system
    When I send a GET request to retrieve all plants
    Then the response status should be 200
    And the response should contain a list of plants
    And each plant should have a name and location

  Scenario: Retrieve a specific nuclear plant by ID
    Given a nuclear plant exists with name "Planta Sevilla"
    When I send a GET request for the plant by ID
    Then the response status should be 200
    And the response should contain the plant name "Planta Sevilla"

  Scenario: Update an existing nuclear plant
    Given a nuclear plant exists with name "Planta Bilbao"
    When I update the plant name to "Planta Bilbao Updated" and location to "Bilbao Updated"
    Then the response status should be 200
    And the response should contain the updated plant name "Planta Bilbao Updated"
    And the updated plant should be persisted in the database

  Scenario: Delete a nuclear plant
    Given a nuclear plant exists with name "Planta Temporal"
    When I send a DELETE request for the plant
    Then the response status should be 204
    And the plant should be removed from the database

  Scenario: Reject creation with missing name
    Given I want to create a new nuclear plant
    When I send a POST request with missing name and location "Madrid"
    Then the response status should be 400
    And the response should contain validation error for name field


