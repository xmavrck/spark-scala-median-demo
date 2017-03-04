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
        prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"))
        // retrieved the property value
        (prop.getProperty("file.path"))
      } catch {
        case e: Exception =>
          e.printStackTrace()
          sys.exit(1)
      }

    val input = sparkContext.textFile(filePath)

    val allMedians = input
      .flatMap(line => line.split("\n"))
      .map(line => {
        getMedian(line)
      }).collect().toList
    println(getMedian(allMedians mkString " "))
  }

  def getMedian(line: String): Int = {
    val numbers = line.split(" ").sortBy(_.toInt)
    if (numbers.length % 2 == 0) {
      val median = numbers.length / 2
      (numbers(median - 1).toInt + numbers(median).toInt) / 2
    } else {
      numbers(numbers.length / 2).toInt
    }
  }
}
