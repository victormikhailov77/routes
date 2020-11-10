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