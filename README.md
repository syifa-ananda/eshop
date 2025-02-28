<details>
<summary>Module 1</summary>

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
the code is free of bugs, it just means all lines were executed, not necessarily tested for 
correctness. That’s why unit tests should be designed to check logic thoroughly, not just for 
coverage

- The new functional test suite introduces some clean code issues, particularly code duplication, 
since it repeats the same setup procedures and instance variables as previous test suites. 
This makes maintenance harder and increases the chance of inconsistencies when changes are 
needed. To improve the code, we should follow the DRY (Don't Repeat Yourself) principle by 
extracting shared setup logic into a base class or utility functions. This would make the test 
suite cleaner, easier to manage, and more maintainable in the long run.

</details>

<details>
<summary>Module 2</summary>

## Reflection

- ### List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.

**Define a constant instead of duplicating this literal "redirect:/product/list" 3 times.**

I replaced the repeated string with a single constant to ensure consistency and make future updates 
easier.

**Remove this field injection and use constructor injection instead.**

I switched from using field injection with @Autowired to constructor injection, which improved the 
code's clarity, immutability, and testability.

**Anchor tags should not be used as buttons.**

I converted the ```<a>``` elements styled as buttons into actual ```<button>``` elements or proper form submissions 
to enhance semantic correctness and accessibility.

**Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or 
complete the implementation.**

I added a comment within the empty method to explain that it's intentionally left blank for now, 
indicating that future implementation is planned or that an exception could be thrown if needed.

**Add at least one assertion to this test case.**

I included an assertion in the test case to verify that the expected behavior occurs, ensuring the test 
actually validates the functionality.

**Remove the declaration of thrown exception 'java.lang.Exception', as it cannot be thrown from method's 
body.**

I removed the unnecessary throws Exception declaration because the method’s code does not throw any checked 
exceptions.

**List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.**

I addressed several issues by replacing repeated string literals with a constant, switching to constructor 
injection for better maintainability, using semantically correct HTML elements, clarifying intentionally 
empty methods with comments, adding missing assertions in tests, and removing redundant exception 
declarations, all guided by best practices and a focus on making the code easier to maintain and understand.

- ### Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

My CI/CD workflows do a great job of ensuring that our code stays in good shape. Every time I push new changes or 
open a pull request, the system automatically builds the project, runs unit tests, and performs code quality and security 
analyses so any issues are caught early. When updates are pushed to the main branch, the deployment pipeline takes over by 
automatically building a Docker image and deploying it to our platform, eliminating the need for manual intervention. 
With additional safeguards like scheduled checks and branch protection in place, the entire process is both reliable and 
secure

</details>

<details>
<summary>Module 3</summary>

## Reflection

### 1) Explain what principles you apply to your project!

- **Single Responsibility Principle (SRP):**

To apply SRP, I made sure that each file in the project is responsible for one clear aspect of the application. For 
instance, my repository files, such as ```CarRepository.java``` and ```ProductRepository.java``` are focused on 
data persistence (CRUD operations) for their respective models. The service files like ```CarServiceImpl.java``` and 
```ProductServiceImpl.java``` handle the business logic and delegate data access to the repositories, while the controller 
files are only in charge of handling HTTP requests and responses. This separation makes it easier to understand and maintain 
each part of the application without one file doing too much.

- **Dependency Inversion Principle (DIP):**

I applied DIP by ensuring that my service classes depend on abstractions rather than concrete classes. 
This was achieved by defining repository interfaces (such as ``CarRepositoryInterface.java``) which are then implemented by 
the concrete classes like ```CarRepository.java```. In my service files (```CarServiceImpl.java``` and ```ProductServiceImpl.java```), 
I use constructor injection to receive these interfaces instead of directly instantiating the repositories. This approach 
not only separate my application layers but also makes it easy to swap out or mock these dependencies during testing.

- **Open-Closed Principle (OCP):**  

My project follows the Open-Closed Principle by making it easy to add new features without changing the existing. 
I built a generic abstract repository (called `InMemoryRepository`) that handles the common CRUD operations. For each 
specific type of entity, like a Car or a Product, I simply extend this generic repository and only add the code that is 
unique to that entity.

### 2) Explain the advantages of applying SOLID principles to your project with examples.

- **Easier Maintenance:**

Each file or class does just one job. For example, one file handles data access, another handles business rules, and 
another deals with user requests. This means if something goes wrong, I can quickly find and fix the specific part.

- **Better Testability:**

By using interfaces for my services, I can swap real parts of my code with fake ones (mocks) when testing. 
This lets me check if my business rules work correctly without needing to rely on a real database.

- **Improved Flexibility and Extensibility:**

This code is designed to let me add new features without changing existing code. For instance, I can create a new 
file for a new type of entity (like a Car or a Product) by extending a generic repository, which minimizes the risk 
of breaking what already works.

### 3) Explain the disadvantages of not applying SOLID principles to your project with examples.

- **Harder Maintenance:**

Without the SRP, files or classes might handle too many tasks. For example, if one 
file manages both data access and business logic, fixing a bug can be confusing because the issue might be hidden among 
many responsibilities.

- **Difficult Testing:**

Without using DIP, the code becomes tightly linked to specific implementations. 
This makes it harder to test because it can’t easily replace real parts with simple test versions (mocks). For instance, 
if my service directly creates a database connection, testing the business logic independently becomes challenging.

- **Limited Flexibility:**

Not following the OCP means that every time I add a new feature or change a behavior, I 
need to modify existing code. For example, adding a new type of entity might require changes throughout my repository 
and service layers, increasing the risk of introducing new bugs.

</details>




