#languge: en

Feature: Queue Service Integration Test
  QueueService enables the interaction with the queue

  Scenario Outline: Testing QueueService Functionalities
    Given the raw cdr file "<relativeCdrFilePath>" is in the queue at with completed status at step streaming
    When I run the QueueService
    Then the fact table should contains data from "<expectedContentFilePath>"
    And the storage status should be "<integrationStatus>"

    Examples:
      | expectedContentFilePath              | integrationStatus  | relativeCdrFilePath                              |
      | src/test/resources/expected-output   | completed          | src/test/resources/actual-input/info.txt         |
