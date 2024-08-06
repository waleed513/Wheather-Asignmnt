Project Summary
This project involves automated testing of a website called "Weather Shopper" using Selenium WebDriver, Cucumber, and ExtentReports. The main goal is to verify that the website behaves correctly based on the temperature, including selecting products, adding them to the cart, and processing payments.

Steps to Set Up and Run the Project

Project Setup Create a New Project in IntelliJ IDEA:
Open IntelliJ IDEA. Create a new Maven or Gradle project (choose Maven for the sake of this guide). 
Add Dependencies:
In your pom.xml (for Maven) or build.gradle (for Gradle), add dependencies for Selenium, Cucumber, ExtentReports, and WebDriverManager. Create the Project Structure:

src/test/java:
Contains step definitions, hooks, and test logic. src/test/resources: Contains feature files with Cucumber scenarios.
2. Write Cucumber Feature Files Create a Feature File: In src/test/resources, create a file named weather_shopper.feature. 
Create a Step Definition File: In src/test/java, create a class named WeatherTest.java. Implement the step definitions to perform actions and validations based on the feature file.
Add Hooks:
Add @Before and @After methods in your step definition class to set up and tear down the test environment. Configure ExtentReports to generate reports. Run Tests Using IntelliJ IDEA:
Right-click on the feature file or step definition class to perform tests.
