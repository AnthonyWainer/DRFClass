package org.pucp.conocimiento.thirdTopic

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.{Model, ModelFactory}
import org.apache.jena.vocabulary.{RDF, RDFS}

object CirujanosRDF {
  def main(args: Array[String]): Unit = {

    val model = ModelFactory.createDefaultModel
    val uri = "http://www.pucp.edu.pe/"
    val ns = "pucp"
    model.setNsPrefix(ns, uri)

    val Staff = crearRecurso(uri + "Staff", model)
    val Physician = crearRecurso(uri + "Physician", model)
    val Surgeon = crearRecurso(uri + "Surgeon", model)
    val Kildare = crearRecurso(uri + "Kildare", model)

    model.add(Surgeon, RDFS.subClassOf, Staff)
    model.add(Surgeon, RDFS.subClassOf, Physician)
    model.add(Kildare, RDF.`type`, Surgeon)

    grabarRDF("cirujanos.rdf", model)
  }

  private def grabarRDF(nmRDFFile: String, model: Model) = {
    val output: FileOutputStream = try {
      new FileOutputStream(s"src/main/Resources/labs/topic3/$nmRDFFile")
    }
    catch {
      case e: FileNotFoundException =>
        println("Ocurrio un error al crear el archivo.", e)
        null
    }
    model.write(output, "RDF/XML-ABBREV")
  }

  private def crearRecurso(id: String, model: Model) = model.createResource(id)
}
