@GoogleMapTesting
Feature: Verify if user is able to get travel details from map

  Background: 
    Given User details read from propertiesfile

  Scenario Outline: User is checking driving instruction to destination through Google Map
    Given Verify user is able to Go to maps.google.com
    And Check user is able to Click on Directions button
    Then Verify user is able to Enter Starting Location as residential location
    And Check User adding Destination from properties file
    Then Check whether user able to download or copy all the driving instructions in excel sheet
    And Verify User able to select the first route
    And close the window
