# Installation #

you need to update path of your sample input file in config.properties in resource folder

Enter the project root directory of project and build using [SBT] 
```
sbt assembly
```

The build produces JAR file:
* `Project_ROOT_DIRECTORY/target/scala-2.11/DemoMedian-assembly-1.0.jar` 

Launch Spark Job

* `./bin/spark-submit --class com.xenonstack.Main Project_ROOT_DIRECTORY/target/scala-2.11/DemoMedian-assembly-1.0.jar`
