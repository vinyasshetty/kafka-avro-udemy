package com.github.vinyasshetty.avrov1

import java.util.Properties

import org.apache.kafka.clients.producer.ProducerConfig

object KafkaProducerMainV1 {

  def main(args: Array[String]): Unit = {

    val props = new Properties()
    props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "")

  }

}
