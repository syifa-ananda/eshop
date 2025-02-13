## Reflection 1

I structured my code using the MVC pattern to ensure clarity and separation of concerns. 
The controller handles HTTP requests, the service manages business logic, the repository 
deals with data storage, and the model defines the product entity. I applied clean code 
principles like meaningful method names, dependency injection, and proper annotations 
(```@Autowired```, ```@Controller```). However, input validation is missing in 
```ProductController``` and templates, which could lead to invalid data. Adding validation 
constraints and exception handling would improve data integrity and security.

## Reflection 2

- Writing unit tests has given me a better understanding of how my code behaves in different
scenarios. It helps catch potential issues early and ensures that changes don’t break existing
functionality. There’s no strict rule on how many unit tests a class should have, but it's important 
to have enough such that it can handle normal/expected and extreme cases. Code coverage is a useful 
metric to see which parts of the code are tested, but achieving 100% coverage doesn’t mean 
the code is free of bugs—it just means all lines were executed, not necessarily tested for 
correctness. That’s why unit tests should be designed to check logic thoroughly, not just for 
coverage

- The new functional test suite introduces some clean code issues, particularly code duplication, 
since it repeats the same setup procedures and instance variables as previous test suites. 
This makes maintenance harder and increases the chance of inconsistencies when changes are 
needed. To improve the code, we should follow the DRY (Don't Repeat Yourself) principle by 
extracting shared setup logic into a base class or utility functions. This would make the test 
suite cleaner, easier to manage, and more maintainable in the long run.







