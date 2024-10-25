# Architecture Decision Record

## ADR 001: Use Hexagonal Architecture
---

### Context:

- The system needs to be highly maintainable and adaptable, allowing for future changes without modifying core business
  logic.
  Integrations with external services (e.g., API calls) and variations in business logic (e.g., different campaign
  types) should be decoupled from core logic.
  This approach should enable easy testing, ensuring that the business logic can be validated without relying on
  external dependencies.

- Domain Layer: Contains the core business logic, represented by domain models such as Campaign, Flight, Outcome and
  Events.
  Campaign is the root class for all type of campaigns. It is the one that will mantain the core logic and the
  calculations.
- Application Layer: Contains services like CampaignExecutor that use the domain models and implement the use cases.
  Campaign Executor will control the flow.
- Adapters Layer: Contains components that implements the interfaces defined in port layer. This is where the event
  publishing is centralized and
  where the different types of campaigns are implemented.
- Ports Layer: Defines interfaces for the external interactions required by the application.
- Infrastructure Layer: Where the inbound and outbound communication are defined, like controllers and API classes.

### Consequences:

- Benefits: This decision ensures that the core business logic remains isolated and testable.
  It allows external dependencies to be swapped without changing core logic, leading to better maintainability and
  evolvability.
- Drawbacks: Increased upfront complexity, as more classes and interfaces are required to maintain
  the separation between the domain and external systems.
- Future Impact: This architecture simplifies the addition of new integrations (e.g., switching a data source or
  adding a new campaign strategy).

## ADR 002: Use Strategy Pattern for Campaign Execution
---

### Context:

- The system supports multiple campaign types (CampaignA, CampaignB, CampaignC), each with its own execution logic.
- The logic for each campaign should be encapsulated separately to adhere to the Single Responsibility Principle.
- I want to easily extend the system with new campaign types without modifying the existing codebase.

- I Use the Strategy Pattern to encapsulate the logic for each campaign type. Then, defined a CampaignStrategy interface
  with an execute method, and create concrete strategy classes (CampaignAStrategy, CampaignBStrategy, CampaignCStrategy)
  that implement this interface.
- I Used a CampaignExecutor service that selects the appropriate strategy based on the CampaignType and delegates the
  execution.

### Consequences:

- Benefits: This approach provides **flexibility** and **extensibility** by allowing new campaign types to be added with
  minimal
  changes to the CampaignExecutor. It promotes **readability** and adheres to the **Open/Closed Principle.**
- Drawbacks: Introduces additional classes for each campaign type, which could lead to a larger codebase as more
  campaign types are added.
- Future Impact: New campaign types can be added by simply implementing the CampaignStrategy interface and registering
  the new strategy, reducing the risk of introducing errors in existing logic.

## ADR 003: Use Event-Driven Architecture for Campaign Notifications

### Context:

- The system must notify other services or components when certain events occur (e.g., a campaign is executed).
- These notifications should be decoupled from the campaign execution logic to allow for future changes without
  affecting the core campaign logic.
- There may be a need for asynchronous processing of certain events to improve performance.

- I use an Event-Driven Architecture with EventBus to publish events after each Campaign is executed.
- Then, I defined events like NotifyOutcome in the domain layer since it's part of the core logic.

### Consequences:

- Benefits: The decoupling of event handling improves **maintainability** and **scalability**. There can be Added new
  event listeners straight forward, which supports **evolvability**.
- Drawbacks: Adds complexity in terms of managing asynchronous flows and requires handling potential race conditions or
  failures in event processing.
- Future Impact: This architecture supports adding new event-based features without changing existing logic, making the
  system more adaptable to new requirements.

---