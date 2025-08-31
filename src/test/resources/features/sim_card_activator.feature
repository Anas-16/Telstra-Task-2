Feature: SIM Card Activation
  As a Telstra store
  I want to activate SIM cards
  So that customers can use their mobile services

  Scenario: Successful SIM card activation
    Given the actuator service is running on localhost:8444
    When I send a POST request to "/api/activate" with ICCID "1255789453849037777" and customer email "customer@example.com"
    Then the response should indicate successful activation
    And the activation record should be saved to the database with ID 1
    And the activation record with ID 1 should show successful activation

  Scenario: Failed SIM card activation
    Given the actuator service is running on localhost:8444
    When I send a POST request to "/api/activate" with ICCID "8944500102198304826" and customer email "customer2@example.com"
    Then the response should indicate failed activation
    And the activation record should be saved to the database with ID 2
    And the activation record with ID 2 should show failed activation

