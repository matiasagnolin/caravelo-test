# TECHNICAL QUESTIONS

1. How would you approach refactoring a legacy codebase?
    - I would start understanding the system, reading documentation, looking also at the tests and if it's possible,
      talking with people who already worked on it.
    - Then I would make a plan, define goals and identify the most important areas of the code.
    - And finally, I would try to apply design patterns and architectural improvements to solve issues in a
      scalable way.

2. Briefly explain some design patterns you particularly like/use and what do you think about SOLID principles, clean
   code, etc.
    - I really like Strategy Pattern to encapsulate behaviors for a specific task and interchange them based on the
      situation.
    - Builder pattern when object creation is complex.
    - IoC for decoupling and DI.
    - I always try to apply SOLID principles and clean code practices, I think that they are fundamental to building
      software that is maintainable, scalable, and easy to understand.

3. What do you think about agile methodologies? When they are beneficial and when they are not? Give examples.
    - Agile methodologies brings flexibility, iterative progress, team planification and adaptability.
    - I think that they are beneficial when the client requires constants improvements and new features in a short
      period of time.
    - Sometimes bad planification can lead to extra-efforts and I feel that sometimes the flexibility regarding adding
      new features (or tickets) to the backlog can also lead to an end-less project

4. We signed a project with an airline where we have to:

    - launch a daily process where we have to scan 12 given flight routes,
    - for every route, we have to scout all flights with departure within the next 3 days,
    - for every flight, we have to scan passengers and choose a max. of 15 of them based on some filtering criteria (
      seat cost +100€, solo travel, only cabin baggage),
    - for every passenger, we have to prepare an upgrade offer picking up one of 3 possible prices, according to this
      formula (given route + days to go (1..3) + seat cost range (100..150 || 150..1000) = X € upgrade offer)
    - for every offer, we have to send an email to the client with a link to a landing page,
    - the landing page shows the offer details and an accept offer button that triggers a POST operation against our
      API,
    - our API processes the request and executes the offer.

   The airline is giving us access to their API (SOAP) for their test environment, API technical documentation, business
   rules to follow and designs (email template, offer landing page). With all this in mind:
    - what technologies would you use to develop our solution (you can put on your fullstack/devops hat) and why?

      Based on my knowledge, I would use the following technologies:
        - Kotlin/Java: I have a lot of years of experience with these programming languages and I feel that there
          are a lot of developers in the market, thinking at the moment to incorporate new team members.
        - Spring-Boot and Spring framework for creating microservices and expose the API.
        - I would use Hexagonal arch and DDD for creating a decouple system and communication based on events (I thought
          on this when I read "launch a daily process ...").
        - Async programming or a scheduler for the daily process.
        - Also async process to scout, apply filtering and creating the offers based on the business rules
          (also thought on hex arch to maintain the business rules near the domain layer) and sending emails (DDD).
        - I don't think we need a complex front-end for the landing, maybe vue.js and any css framework like Tailwind for
          the beginning  would fit the necessity.
        - PostgreSQL to store data related to routes, client preferences, offers and closed deals.
        - Kafka or RabbitMq to microservices communication (example: Event offer created to be processed by email
          sender).
        - AWS o GCP for hosting and infra. EC2, RDs, S3, SMTP for emails. I worked with AWS-SDK.
        - Jenkins for CI/CD (Groovy) or github actions.
        - Docker and Kubernetes for containerize the application and make it scalable (vertical/horizontal scalability
          and high availability).
        - Datadog, Signal, Splunk or any app to read logs and create monitoring (really useful to set alarms for MS).

        - how would you put them together (please explain general flow and how they interact/communicate)?
            - Schedule the Daily Task (Use Quarz or own standalone process to trigger the daily scan for all the
              routes).
            - Read the info from db to fetch the flights using SOAP client library.
            - Fetch the filtering data from DB and filter the flights based on criteria.
            - Create offers async for each client.
            - When offer created put an event on queue and store it on DB.
            - Subscribers will start processing  like email sender (ensure process is non-blocking).
            - The landing page shows the offer for each client, the info could be fetch making the user do login and
              fetching it from DB trough an endpoint from the backend. Once, I worked with Mustache which is a framework
              to create static websites dynamically from the BE, but I don't think we need this for this case.
            - FE sends POST request to the endpoint.
            - Stores result in DB and message in queue to trigger any further processing required like the actual
              booking in the airline, etc.
            - 
    - what would you do to help with performance?
    - Async processing, queues, batch processing (flights, routes), caching like Redis (but I don't see it clearly here),
    - circuit breaker when airlines don't work properly.
   - I think that all these technologies will bring high performance, good software development cycle and high availability.

5. How would you deal with a feature/project where there is high uncertainty?
   - I would start with an MVP or proof of concept if it's a feature.
   - short iterations to show progressive improvements.
   - keep a continues communication with the client to make sure that we are in the right path.
   - Always asking for feedback.
   - Use feature flags
   - Show transparency for negative and positive scenarios.