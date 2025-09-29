# Lab 2 Web Server -- Project Report

## Description of Changes
In this lab assignment, we were asked to add some enhancements to a Spring Boot web server. In order to complete the assignment we were given three main tasks which improve the web server in different ways. 

The first task consists in improving the web server's error handling by developing a custom error page. This page should be displayed when an error occurs, replacing the default Spring Boot whitelabel page. I started creating the file 'error.html' in the route src/main/resources/templates. The content of the error file is a simple html which informs the user an error has occured. Thanks to Spring Boot, placing the file in the specified route should be enough for the template to automatically replace the whitelabel error page. 

After doing this, I started the web server with './gradlew bootRun' and looked up a non existent route in the web server (Ex: 'http://localhost:8080/).In order to automate the testing proccess, I created a file called ErrorPageTest.kt in the route src/test/kotlin/es/unizar/webeng/lab2. In this file I wrote the integration test, which sends a request to the route 'http://localhost:$port/ruta-que-no-existe', as we know it doesn't exist. Then, the test checks whether the response status is 404 and it searchs the HTML to identify the error.html template. It's really important to add the header 'Accept: text/html' in the request, otherwise the server asumes you are an app and gives the response in json format. We do not want this, as we want to make sure our custom error page is being displayed.

The next task of the assignment is adding a new endpoint, specifically, we have to create a new REST endpoint that returns the current server time. We can do this in several files (TimeProvider.kt, TimeDTO.kt, TimeController.kt, TimeService.kt), but, I did it in only one file* (TimeComponent.kt) because the lab assignment paper tells us to do it this way. In this time component, I wrote the code of the DTO, the service and the REST controller. Thanks to Spring Boot we don't have to do anything else, as it recognises the controller annotation and automatically exposes the endpoint. Once again, we can run the server using './gradlew bootRun' and make sure the endpoint is working. The easiest way to do so is looking up 'http://localhost:8080/time' in the navigator.

Apart from implementing the new feature, we have to create an automatic test that validates its use. To do so, I have created a file TimeControllerTest.kt in the route src/test/kotlin/es/unizar/webeng/lab2. Inside this file, I mocked the 'now' method in the time service so it returns a fixed date. After that, it makes a 'GET' request to the new endpoint, checking whether the response status is okay, the response format is json and the response date matches the fixed one. 

The last taks of the assignment consists in enabling HTTP/2 and SSL support. In order to do that, we have to generate a self-signed certificate and create a PKCS12 keystore containing the certificate and the private key. I did all of this using the command-line tool openssl. The first command given in the lab assignment paper generate the certificate and the private key. Then, the next command uses the output of the previous one to generate the PKCS12 keystore in a file called 'localhost.p12'. I moved this file to the route stc/main/resources. 

Additionally, we need to create a configuration file in the route src/main/resources called 'application.yml. In this file I added the configuration needed to enable SSL using the keystore anda activate HTTP/2 support on port 8443. We also have to add this file to the route src/test/resources, but, in this one we are going to disable ssl. I did this because otherwise, when running the integration tests (ErrorPageTest.kt) the web server started running in HTTPS. As running the tests that way required adding extra complexity to the test, I decided to disabe SSL only for testing.


## Technical Decisions
This assignment doesn't allow for many technical decisions since it was very guided. However, I'm going to comment on some of the decisions the lab assignment paper itself makes and why I probably would have made them too. 

Firstly, the lab assignment paper tells us to implement the new time feature in a single file. This may not be a good decision in a big project, but for a small lab assignment, it helps reduce complexity and keep related logic close.

Another good technical decision was the use of a PKCS12 keystore as it is the recommended protable keystore format and it's natively supported by the JVM. 

Finally, one technical decision that I could make for myself was disabling SSL for testing, which avoids handling it inside the tests. 

## Learning Outcome
Thanks to this lab assignment, I have learnt lots of useful things about Spring Boot. Some of this things were not new for me, as I had done them before in other, but I had never done them in this framework. For example, I had already worked with other frameworks in which customizing the error page is easy. Some of them are Express, Django or Laravel. 

It was really useful to implement a new feature from scratch, as that's what we are going to do in the first phases of the group project. Again, other frameworks handle this aspects in a similar way, but the best way to get used to a certain syntax is by writing it. 

Last, I found very surprising the ease of configuring HTTP/2 and SSL in Spring Boot. I had only done this in Express, and I can tell it's a bit more difficult. The good thing about Spring Boot is that it handles HTTP/2 and SSL configuration in a separate file, while in Express, you have to handle it with code. The first way of handling this is more clean and allows to change configurations between tests and production more easily.

I also read the bonus proposed tasks and I investigated about them. However, I didn't implement any of them as we have lots of work to do and I prefered to invest my time in mandatory assignments of other subjects. 

## AI Disclosure
### AI Tools Used
Chatgpt

### AI-Assisted Work
- In this lab assignment, I used AI for problem solving, specifically problems concerning the executions of the error page tests. At first, I tried to implement this tests as unit tests, which produced an error I didn't know how to solve. I asked AI to give me solutions but I was not very effective. Then, after the teacher told us the origin of this errors, I asked AI to help me rewrite the tests into integration tests. AI explained to me the differences between unit and integration tests in Spring Boot and told me what changes I had to make. Another use of AI was creating the code of the error.html file. 

- 15% AI assisted, 85% original work

- AI gave me the changes I had to make to turn unit tests into integration tests, however, it gave me lots of tests with lots of validations. I only copied the structure I needed for the tests to be integration ones instead of unit ones and the part of the code which made a request to the web server. Lastly, I also changed the error.html page AI gave as it was really complex in the begining. I didn't specify the length nor complexity of the page, so AI gave me a production ready HTML file. After that, I erased most of the code until the page was simple but good looking. This way, I have an HTML which I actually undesrtand and looks good.


### Original Work
- This lab assignment was very guided, so it was not necessary to use AI apart from the uses listed above. I followed the lab assignment paper learning how to implement the enhancements proposed in Spring Boot. I also tried to compare the way this frameworks does things with others I have already use. This improves my ability to choose frameworks, as I know in which one are things easier or more efficient. 
