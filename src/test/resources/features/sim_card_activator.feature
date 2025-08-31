Feature: SIM Card Activation
  As a Telstra store
  I want to activate SIM cards
  So that customers can use their mobile services

  Scenario: Successful SIM card activation
    Given the actuator service is running on localhost:8444
    When I send a POST request to "/api/activate" with:
      | iccid        | customerEmail           |
      | 123456789012 | customer@example.com    |
    Then the response should indicate successful activation

  Scenario: Failed SIM card activation
    Given the actuator service is running on localhost:8444
    When I send a POST request to "/api/activate" with:
      | iccid        | customerEmail           |
      | 987654321098 | customer2@example.com   |
    Then the response should indicate failed activation
