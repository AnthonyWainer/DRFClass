package org.pucp.conocimiento.firstTopic

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.vocabulary.VCARD

object WriteTurtleRDF {
  def main(args: Array[String]): Unit = {
    println(" Creando RDF ... ")
    val personURI = "http://www.pucp.edu.pe/AndresMelgar"
    val fullName = "Andrés Melgar"

    val model = ModelFactory.createDefaultModel()
    val andresMelgar = model.createResource(personURI)
    andresMelgar.addProperty(VCARD.FN, fullName)

    println("Write Turtle RDF...")

    model.write(System.out, "TURTLE")

    var output: FileOutputStream = null

    try {
      output = new FileOutputStream("src/main/Resources/labs/topic1/vCard.ttl")
    }
    catch {
      case e: FileNotFoundException =>
        println("Ocurrió un error al crear el archivo.", e)
    }
    model.write(output, "TURTLE")
  }
}
