Feature: Supplier CRUD Operations
  As a supply chain manager
  I want to manage suppliers
  So that I can track vendors and their contact information

  Background:
    Given the authentication server is available
    And I have a valid JWT token

  Scenario: Create a new supplier
    Given I want to create a new supplier
    When I send a POST request with supplier name "Combustible Proveedor" and contact "fuel@supplier.com" and phone "+34912345600"
    Then the response status should be 201
    And the response should contain the supplier name "Combustible Proveedor"
    And the response should contain the supplier contact "fuel@supplier.com"
    And the new supplier should be persisted in the database

  Scenario: Retrieve all suppliers
    Given there are suppliers in the system
    When I send a GET request to retrieve all suppliers
    Then the response status should be 200
    And the response should contain a list of suppliers
    And each supplier should have name, contact and phone fields

  Scenario: Retrieve a specific supplier by ID
    Given a supplier exists with name "Water Supplier"
    When I send a GET request for the supplier by ID
    Then the response status should be 200
    And the response should contain the supplier name "Water Supplier"
    And the response should contain supplier contact and phone fields

  Scenario: Update an existing supplier
    Given a supplier exists with name "Old Supplier Name"
    When I update the supplier name to "New Supplier Name" and contact to "newsupplier@mail.com"
    Then the response status should be 200
    And the response should contain the updated supplier name "New Supplier Name"
    And the updated supplier should be persisted in the database

  Scenario: Delete a supplier
    Given a supplier exists with name "Temporal Supplier"
    When I send a DELETE request for the supplier
    Then the response status should be 204
    And the supplier should be removed from the database

  Scenario: Reject creation with invalid email
    Given I want to create a new supplier
    When I send a POST request with supplier name "BadEmail Supplier" and contact "invalid-email" and phone "+34912345678"
    Then the response status should be 400
    And the response should contain a validation error message

