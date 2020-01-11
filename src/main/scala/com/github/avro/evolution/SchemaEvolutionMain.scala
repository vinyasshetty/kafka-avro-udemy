package com.github.avro.evolution

import java.io.{File, IOException}

import com.example.{CustomerV1, CustomerV2}
import org.apache.avro.file.{DataFileReader, DataFileWriter}
import org.apache.avro.specific.{SpecificDatumReader, SpecificDatumWriter}

object SchemaEvolutionMain {

  def main(args: Array[String]): Unit = {

    val custb1 = CustomerV1.newBuilder()
    custb1.setFirstName("Vinyas")
    custb1.setLastName("Shetty")
    custb1.setAge(30)
    custb1.setWeight(79.2f)
    custb1.setHeight(179.2f)
    custb1.setAutomatedEmail(true)

    val cust1 = custb1.build()

    val datumWriter = new SpecificDatumWriter[CustomerV1](classOf[CustomerV1])
    val dataFileWriter = new DataFileWriter[CustomerV1](datumWriter)

    try {
      dataFileWriter.create(cust1.getSchema, new File("customerV1-specific.avro"))
      dataFileWriter.append(cust1)  // We can keep appending multiple records
      println(cust1)
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

    //Read the data using the new schema ie customer-v2
    val file = new File("customerV1-specific.avro")

    val datumReader = new SpecificDatumReader[CustomerV2](classOf[CustomerV2])
    val dataFileReader = new DataFileReader[CustomerV2](file, datumReader)

      while(dataFileReader.hasNext) {
        val cust2 = dataFileReader.next()
        println("Successfully read avro file")
        // get the data from the specific record
        println(cust2)
        //println("First name: " + customerRead.getFirstName)

      }
    dataFileReader.close()

  }

}
