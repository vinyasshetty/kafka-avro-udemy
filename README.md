Before running , make sure you run avro:generate in sbt , This will generate the Java classes for avsc u have created.

https://github.com/sbt/sbt-avro

There is a possibility , that you might see a error saying "class Customer already created", for the go to File and Project Structure and
then Sources and make sure you do not have multiple locations for "compiled_avro" locations.

To get Avro Jar :
curl -H "Accept: application/zip"  http://central.maven.org/maven2/org/apache/avro/avro-tools/1.9.1/avro-tools-1.9.1.jar -o vro-tools-1.9.1.jar
java -jar vro-tools-1.9.1.jar tojson --pretty customer-generic.avro
java -jar avro-tools-1.9.1.jar getschema customer-specific.avro


Docker:
In the directory of the yml file , say :   

docker-compose up    

docker run -it --rm --net=host confluentinc/cp-schema-registry:5.3.1 bash
