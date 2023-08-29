# TECHNICAL QUESTIONS

1. How would you approach refactoring a legacy codebase?

2. Briefly explain some design patterns you particularly like/use and what do you think about SOLID principles, clean code, etc.

3. What do you think about agile methodologies? When they are beneficial and when they are not? Give examples.

4. We signed a project with an airline where we have to:

   - launch a daily process where we have to scan 12 given flight routes,
   - for every route, we have to scout all flights with departure within the next 3 days,
   - for every flight, we have to scan passengers and choose a max. of 15 of them based on some filtering criteria (seat cost +100€, solo travel, only cabin baggage),
   - for every passenger, we have to prepare an upgrade offer picking up one of 3 possible prices, according to this formula (given route + days to go (1..3) + seat cost range (100..150 || 150..1000) = X € upgrade offer)
   - for every offer, we have to send an email to the client with a link to a landing page,
   - the landing page shows the offer details and an accept offer button that triggers a POST operation against our API,
   - our API processes the request and executes the offer.
   
   The airline is giving us access to their API (SOAP) for their test environment, API technical documentation, business rules to follow and designs (email template, offer landing page). With all this in mind:
   - what technologies would you use to develop our solution (you can put on your fullstack/devops hat) and why? 
   - how would you put them together (please explain general flow and how they interact/communicate)? 
   - what would you do to help with performance? 
   
5. How would you deal with a feature/project where there is high uncertainty?
