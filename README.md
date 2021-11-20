#Content
1. [fakepayment](#s1)<br/>
2. [The basic rules](#s2)<br/>
3. [Must](#s3)<br/>
4. [How to deliver it](#s4)<br/>

----

# <a name="s1"></a>fakepayment

You are given several Java interfaces for a fake payment system.  Your task is to implement the system.

----
## Delivered in this version by Orlando Cano


### How to run it?

1. Have maven installed
2. run `mvn clean install`
3. run ` mvn spring-boot:run ` (**Note: this application by default is running in 8080 port** )
4. You can check Open API docs at http://localhost:8080/swagger-ui.html
5. Optionality (and is recommended to use) there is a **postman collect**i**on** under `src/main/resources` that you can import it and use it.

#### Note: This is a POC, is too far away from an application ready to prod and is a version with a lot of bug and not edge cases covered, remember the Triad (Time, Cost, Quality )


----

# <a name="s2"></a>The basic rules are :

1. No two users can have the same e-mail address.  The e-mail address uniquely identifies each user in the system. <br/>
2. Accounts can be shared by multiple users but a user can only be linked to 1 account ( for the sake of simplicity ). <br/>
3. You cannot modify any of the interfaces but you are free to add whatever methods you want to your implementation classes. <br/>

----

# <a name="s3"></a>Must :
- You must document the API with swagger.
- You must implement unit test and integration test if needed.
- Will be a plus if you use any database.
- Will be a plus if you deploy the application in a cloud.
- Will be a plus if you deliver a postman collection to consume the API.

----

# <a name="s4"></a>How to deliver it?
In the next following 3-4 days after to receive this challenge you will have to push your code into a personal repository of GitHub, GitLab or somethig similar and send the link.

Take in consideration it will be evaluated given the code quality and delivery time.
