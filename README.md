## How to run EXPRESS2.0 ?
1.  Development description
	+	IDE: IntelliJ IDEA 2020.1 x64
	+	Programming language: Java
	+	JDK: 1.8
	+	SpringBoot: 2.6.11
	+	mysql: 5.7.29
	+	Mybatis-Plus: 3.5.1
	+	Amap js api:1.4.15
	+	openfaas
2.	Deploy
	+	Create a maven project in IntelliJ IDEA.
	+	Inside the project directory, initialize an empty Git repository with the following command.
	+	git init
	+	Add the Git repository of project as the origin remote.
	+	git remote add origin https://github.com/ISEC-AHU/EXPRESS2.0
	+	Pull the contents of the repository to your machine.
	+	git pull origin master
	+	The openfaas service-related class, OpenFaasUtils, under the utils package, needs to be modified for your own deployed service. If you are unable to deploy openfaas, we offer a way to run it locally:  Modify the ServiceComposition, ResourceAllocation, and SecurityService in the core package, set the openFaasFlag to 0, and restart the project to use the local service. 
	+	Project operation requires multiple requests for Autonavi background service, please ensure that your network is unobstructed.  
	+	We provide free js api key with access limit for your research, you can also apply for a new js api key, just change the key in the page to your own. 