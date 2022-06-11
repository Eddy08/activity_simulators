# Starting the application:
1. Install Java and Maven and configure them properly.
2. go to the webactivitytracker folder and inside that try to run mvn spring-boot:run
3. Structure of DB/ Table will be autocreated. (⚠⚡I am using h2 and your previous data will be deleted once you restart your application.)
4. In the webactivitytracker/src/main/resources/application.properties go to the directory-name-with-jsons and place your directory where jsons are presents. (NOTE PLEASE PUT JSON FILES AS SPECIFIED BELOW to run the application)
5. Hit localhost:8080/files -> it will import the required (validated ) data.
6. Hit localhost:8080/analytics -> it will show you analytics stuff.
7. JSON File format as mentioned in point 4.
{"unique_id": 62762019, "activities": [{"name": "doubleTap", "time": 1654970692, "duration": 11062}, {"name": "anr", "time": 1654970692, "duration": 18787}, {"name": "crash", "time": 1654970692, "duration": 26919}, {"name": "doubleTap", "time": 1654970692, "duration": 25143}, {"name": "crash", "time": 1654970692, "duration": 28543}, {"name": "invalid", "time": 1654970692, "duration": 23966}, {"name": "anr", "time": 1654970692, "duration": 5820}, {"name": "anr", "time": 1654970692, "duration": 14007}, {"name": "singleTap", "time": -3235.8327808097465, "duration": 15923}, {"name": "singleTap", "time": 1654970692, "duration": 26315}]}
8. You can go through below commands to auto generate mock directory file no. files
9. Do not forget to star 🌟 the repo.

# Run with params will generate no of files passed in the param
python json-generator.py 100
# Run without params will generate -> 10 files only
python json-generator.py 
