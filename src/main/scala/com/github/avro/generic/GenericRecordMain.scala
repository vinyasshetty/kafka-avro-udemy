package com.github.avro.generic

import org.apache.avro.Schema
import org.apache.avro.generic.GenericData.Record
import org.apache.avro.generic.GenericRecordBuilder
import org.apache.avro.file.DataFileWriter
import org.apache.avro.generic.GenericDatumWriter
import org.apache.avro.file.DataFileReader
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericRecord
import java.io.{File, IOException}

class GenericRecordEx{

}

object GenericRecordMain {

  def main(args: Array[String]): Unit = {

    //1.Create a Generic Record
    val parser:Schema.Parser = new Schema.Parser()

    val schema = parser.parse("{\n     \"type\": \"record\",\n     \"namespace\": \"com.example\",\n     \"name\": \"Customer\",\n     \"doc\": \"Avro Schema for our Customer\",     \n     \"fields\": [\n       { \"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First Name of Customer\" },\n       { \"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last Name of Customer\" },\n       { \"name\": \"age\", \"type\": \"int\", \"doc\": \"Age at the time of registration\" },\n       { \"name\": \"height\", \"type\": \"float\", \"doc\": \"Height at the time of registration in cm\" },\n       { \"name\": \"weight\", \"type\": \"float\", \"doc\": \"Weight at the time of registration in kg\" },\n       { \"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"Field indicating if the user is enrolled in marketing emails\" }\n     ]\n}")

    //2.Write the generic record to a file
    val customBuilder = new GenericRecordBuilder(schema)
    customBuilder.set("first_name", "John")
    customBuilder.set("last_name", "Doe")
    customBuilder.set("age", 29)
    customBuilder.set("height", 180.2f)
    customBuilder.set("weight", 179.5f)
    customBuilder.set("automated_email", false)

    val record:Record = customBuilder.build()

    println(record)

    val customBuilder1 = new GenericRecordBuilder(schema)
    customBuilder1.set("first_name", "Mike")
    customBuilder1.set("last_name", "Sam")
    customBuilder1.set("age", 67)
    customBuilder1.set("height", 156.2f)
    customBuilder1.set("weight", 167.5f)

    val record1:Record = customBuilder1.build()

    println(record1)


    val datumWriter = new GenericDatumWriter[GenericRecord](schema)
    val dataFileWriter = new DataFileWriter[GenericRecord](datumWriter)
    try {
      dataFileWriter.create(schema, new File("customer-generic.avro"))
      dataFileWriter.append(record)  // We can keep appending multiple records
      println("Written customer-generic.avro")
    }
      catch {
        case e: IOException =>
          println("Couldn't write file")
          e.printStackTrace()
      }
    finally {
      dataFileWriter.close()

    }


    //3.Read the generic record from a file

    val file = new File("customer-generic.avro")

    val datumReader = new GenericDatumReader[GenericRecord]()
    val dataFileReader = new DataFileReader[GenericRecord](file, datumReader)
      try {

        println(dataFileReader.hasNext)
        val customerRead = dataFileReader.next()
        println("Successfully read avro file")
        println(customerRead.toString)
        // get the data from the generic record
        println("First name: " + customerRead.get("first_name"))
        // read a non existent field
        //println("Non existent field: " + customerRead.get("not_here"))
      } catch {
        case e: IOException =>
          e.printStackTrace()
      }
    finally {
      dataFileReader.close()

    }

  }

}
