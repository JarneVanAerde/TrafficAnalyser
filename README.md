# Traffic Analyzer

Detecting fines with Thymeleaf web interface and generating fake messages with simulator.

## Adding new fines & detection services

To add new fines you can extend from the abstract class Fine. Configure the needed JpaConfiguration and you’re done.

Adding a new detection service for your new fine is just as easy. Implement the detection service interface and override the “detectFine”-method. You can choose which type of messages the service needs to process because the interface is generic. The new detection service will be autowired to the processor

If needed, a new repository can also be added to correspond with your new fine.

## Adding new receivers & processor

To add a new receivers you can implement the Receiver interface and override the “receiveMessage”.

To add a new processor you can implement the ProcessorService interface and override the “processMessage” method. This interface is generic and can easily be changed to another type camera message.

### Contribution
Made by Jarne Van Aerde.
