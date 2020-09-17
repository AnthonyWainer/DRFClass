package org.pucp.conocimiento.secondclass

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.{Model, ModelFactory}
import org.apache.jena.vocabulary.{RDF, RDFS}

object WriteRDF {
  def main(args: Array[String]): Unit = {

    val model = ModelFactory.createDefaultModel
    val uri = "http://www.pucp.edu.pe/"
    val ns = "pucp"
    model.setNsPrefix(ns, uri)

    val mensWear = crearRecurso(uri, "MensWear", model)
    val shirts = crearRecurso(uri, "Shirts", model)
    val tshirts = crearRecurso(uri, "Tshirts", model)
    val henleys = crearRecurso(uri, "Henleys", model)
    val oxfords = crearRecurso(uri, "Oxfords", model)
    val chamoisHenley = crearRecurso(uri, "ChamoisHenley", model)
    val classicOxford = crearRecurso(uri, "ClassicOxford", model)


    model.add(shirts, RDFS.subClassOf, mensWear)
    model.add(tshirts, RDFS.subClassOf, shirts)
    model.add(henleys, RDFS.subClassOf, shirts)
    model.add(oxfords, RDFS.subClassOf, shirts)

    model.add(chamoisHenley, RDF.`type`, henleys)
    model.add(classicOxford, RDF.`type`, shirts)
    model.add(classicOxford, RDF.`type`, oxfords)

    var output: FileOutputStream = null

    try {
      output = new FileOutputStream("camisas.rdf")
    }
    catch {
      case e: FileNotFoundException =>
        System.out.println("Ocurrio un error al crear el archivo.")
    }
    model.write(output, "RDF/XML-ABBREV")
  }


  private def crearRecurso(uri: String, id: String, model: Model) = model.createResource(uri + id)


}
