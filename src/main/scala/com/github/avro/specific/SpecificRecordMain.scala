package com.github.avro.specific

import java.io.{File, IOException}

import com.example.Customer
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}


object SpecificRecordMain {

  def main(args: Array[String]): Unit = {

    val custBuilder = Customer.newBuilder()
    custBuilder.setAge(28)
    custBuilder.setFirstName("Vinyas")
    custBuilder.setLastName("Shetty")
    custBuilder.setWeight(79)
    custBuilder.setHeight(180.23f)
    custBuilder.setAutomatedEmail(false)

    val customer1 = custBuilder.build()

    println(customer1)

    val schema = customer1.getSchema()
    val datumWriter = new SpecificDatumWriter[Customer](classOf[Customer])
    val dataFileWriter = new DataFileWriter[Customer](datumWriter)

    try {
      dataFileWriter.create(schema, new File("customer-specific.avro"))
      dataFileWriter.append(customer1)  // We can keep appending multiple records
      println("Written customer-specific.avro")
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

    val file = new File("customer-specific.avro")

    val datumReader = new SpecificDatumReader[Customer](classOf[Customer])
    val dataFileReader = new DataFileReader[Customer](file, datumReader)
    try {

      while(dataFileReader.hasNext)
        {
          val customerRead = dataFileReader.next()
          println("Successfully read avro file")
          println(customerRead.toString)
          // get the data from the specific record
          println("First name: " + customerRead.getFirstName)

        }

    } catch {
      case e: IOException =>
        e.printStackTrace()
    }
    finally {
      dataFileReader.close()

    }
    



  }

}
