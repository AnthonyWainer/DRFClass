package org.pucp.conocimiento.thirdTopic

import org.apache.jena.rdf.model.{InfModel, _}
import org.apache.jena.vocabulary.RDF

object ShowDeclarationsRDF {
  def main(args: Array[String]): Unit = {

    val inputFileName = "/labs/topic3/cirujanos.rdf"
    val stream = getClass.getResourceAsStream(inputFileName)

    val model = ModelFactory.createDefaultModel
    model.read(stream, "")
    val inf = ModelFactory.createRDFSModel(model)

    val resourcedURI = model.expandPrefix("pucp:Kildare")
    val Kildare = model.getResource(resourcedURI)


    mostrarDeclaraciones(inf, Kildare, RDF.`type`, null)

  }

  def mostrarDeclaraciones(inf: InfModel, Sujeto: Resource, predicado: Property, objeto: Resource) = {

    val selector = new SimpleSelector(Sujeto, predicado, objeto)
    val iter = inf.listStatements(selector)
    while (iter.hasNext) {
      println(iter.nextStatement.toString)
    }
  }


}
