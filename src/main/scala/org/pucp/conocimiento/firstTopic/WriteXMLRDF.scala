package org.pucp.conocimiento.firstTopic

import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.vocabulary.VCARD
import java.io.{File, FileNotFoundException, FileOutputStream, OutputStream}

object WriteXMLRDF {
  def main(args: Array[String]): Unit = {
    println(" Creando RDF ... ")
    val personURI = "http://www.pucp.edu.pe/AndresMelgar"
    val fullName = "Andrés Melgar"

    val model = ModelFactory.createDefaultModel()
    val andresMelgar = model.createResource(personURI)
    andresMelgar.addProperty(VCARD.FN, fullName)

    println("Write RDF RDF / XML...")

    model.write(System.out, "RDF/XML")

    var output: FileOutputStream = null

    try {
      output = new FileOutputStream("src/main/Resources/labs/class1/vCard.rdf")
    }
    catch {
      case e: FileNotFoundException =>
        println("Ocurrió un error al crear el archivo.", e)
    }
    model.write(output, "RDF/XML-ABBREV")
  }
}
