# routes
Route selection application

Find the most representative ship route between two points, from the given historical dataset of ship routes.

1. Reduce big data set to small statistically significant data set , using normal distribution and Z-score(constant) by two factors (route duration and length). 
This operation discards routes, which have big deviation from shortest or more obvious path , what can be caused by bad weather, emergency, or navigation error.

2. Find overlapping set between sets filtered by duration and length. This would be rather small set, like 7-10 routes.

3. In this final set, find the route, which has minimal deviation from avg  duration or segment count (this is strategy switchable in the code)

The output is geojson file, which can be visualized in the web app geojson.io: open http://geojson.io and open produced file

The program is written on Java 15 with preview features (Records). 
OpenJDK 15 is required for compilation.

![Alt text](selectedRoutes.PNG?raw=true "Title")


______________________________________________________________________________________
PREREQUISITES:

OpenJDK 15 as default JDK.<br>
check version with java -version:<br>
openjdk version "15.0.1" 2020-10-20<br>
OpenJDK Runtime Environment (build 15.0.1+9-18)<br>
OpenJDK 64-Bit Server VM (build 15.0.1+9-18, mixed mode, sharing)<br>

Maven configured to use JDK 15 (via JAVA_HOME set to default JDK).<br>
check version of java in maven with mvn -version:<br>
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)<br>
Maven home: C:\java\apache-maven-3.6.3\bin\..<br>
Java version: 15.0.1, vendor: Oracle Corporation, runtime: C:\java\jdk-15.0.1<br>
Default locale: en_US, platform encoding: Cp1250<br>
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"<br>

Download link:<br>
https://jdk.java.net/15/
______________________________________________________________________________________

BUILD:

mvn clean package - with unit tests<br>
mvn clean package -DskipTests - no unit tests<br>
______________________________________________________________________________________

RUN:

java --enable-preview -jar target/routes-jar-with-dependencies.jar src/main/resources\DEBRV_DEHAM_historical_routes.csv selectedRoutes.geojson<br>

where:<br>
src/main/resources\DEBRV_DEHAM_historical_routes.csv - file with source routes<br>
selectedRoutes.geojson - output file with results<br>