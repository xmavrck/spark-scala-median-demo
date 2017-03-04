package com.xenonstack

import java.io.{FileInputStream, InputStream}
import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

/**
  * Created by xenon on 4/3/17.
  */
object Main extends App {

  override def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("spark-scala-median-demo").setMaster("local")
    val sparkContext = new SparkContext(conf)
    val (filePath) =
      try {
        val prop = new Properties()
        // Load a properties file config.properties from project classpath, and retrieved the property value.
//        prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"))
        prop.load(new FileInputStream("/home/xenon/DemoMedian/src/main/resources/config.properties"))
        // retrieved the property value
        (prop.getProperty("file.path"))
      } catch {
        case e: Exception =>
          e.printStackTrace()
          sys.exit(1)
      }
// converting file into rdd
    val input = sparkContext.textFile(filePath)

    val allMedians = input
      // splitting file line by line
      .flatMap(line => line.split("\n"))
      .map(line => {
        // getting median per line
        getMedian(line)
      }).collect().toList
    // getting median of all median
    println(getMedian(allMedians mkString " "))
  }

  // return median of any given string separeted by space
  def getMedian(line: String): Int = {
    // split the line by space and converting it to array and then sorting it
    val numbers = line.split(" ").sortBy(_.toInt)
    // if number of elements are even median
    // median= (number of elements/2)
    // finalMedian= (numbers(median-1) +numbers(median))/2
    if (numbers.length % 2 == 0) {
      val median = numbers.length / 2
      (numbers(median - 1).toInt + numbers(median).toInt) / 2
    } else
    // if number of elements are odd
    {
      numbers(numbers.length / 2).toInt
    }
  }
}
