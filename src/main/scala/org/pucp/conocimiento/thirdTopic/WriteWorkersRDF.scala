package org.pucp.conocimiento.thirdTopic

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.{Model, ModelFactory, Property, Resource}
import org.apache.jena.vocabulary.{RDF, RDFS}

object WriteWorkersRDF {
  def main(args: Array[String]): Unit = {

    val model = ModelFactory.createDefaultModel
    val uri = "http://www.pucp.edu.pe/"
    val ns = "pucp"
    model.setNsPrefix(ns, uri)

    val worksFor = crearPropiedad(uri, "worksFor", model)
    val contractsTo = crearPropiedad(uri, "contractsTo", model)
    val isEmployedBy = crearPropiedad(uri, "isEmployedBy", model)
    val freeLancesTo = crearPropiedad(uri, "freeLancesTo", model)
    val indirectlyContractsTo = crearPropiedad(uri, "indirectlyContractsTo", model)

    model.add(contractsTo, RDFS.subPropertyOf, worksFor)
    model.add(isEmployedBy, RDFS.subPropertyOf, worksFor)
    model.add(freeLancesTo, RDFS.subPropertyOf, contractsTo)
    model.add(indirectlyContractsTo, RDFS.subPropertyOf, contractsTo)

    val TheFirm = crearRecurso(uri + "TheFirm", model)
    val Goldman = crearRecurso(uri + "Goldman", model)
    val Spence = crearRecurso(uri + "Spence", model)
    val Long = crearRecurso(uri + "Long", model)

    model.add(Goldman, isEmployedBy, TheFirm)
    model.add(Spence, freeLancesTo, TheFirm)
    model.add(Long, indirectlyContractsTo, TheFirm)

    var output: FileOutputStream = null

    try {
      output = new FileOutputStream("src/main/Resources/labs/topic3/relaciones_trabajadores.rdf")
    }
    catch {
      case e: FileNotFoundException =>
        println("Ocurrio un error al crear el archivo.", e)
    }
    model.write(output, "RDF/XML-ABBREV")
  }


  private def crearPropiedad(uri_base: String, id: String, model: Model) = model.createProperty(uri_base + id)

  private def crearRecurso(id: String, model: Model) = model.createResource(id)
}
