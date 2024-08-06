
Feature: Shopping on weather

  Scenario: Shopping  for products based on the temperature
    Given I am on the Weather Shopper homepage
    When the temperature is checked
    Then I choose the appropriate product category based on the temperature
    And I add the necessary products to the cart
    And I click on the cart
    Then I verify that the shopping cart looks correct
    And I fill out my payment details
    Then Submit the Form
