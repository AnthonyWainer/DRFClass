package org.pucp.conocimiento.thirdTopic

import org.apache.jena.rdf.model.{InfModel, ModelFactory, Property, Resource, SimpleSelector}
import org.apache.jena.vocabulary.RDF

object ReadWomanRDF {
  def main(args: Array[String]): Unit = {

    val inputFileName = "/labs/topic3/nombre_soltera.rdf"
    val stream = getClass.getResourceAsStream(inputFileName)

    val model = ModelFactory.createDefaultModel
    model.read(stream, "")
    val inf = ModelFactory.createRDFSModel(model)

    var resourcedURI = model.expandPrefix("pucp:Karen")
    val Karen = model.getResource(resourcedURI)

    resourcedURI = model.expandPrefix("pucp:MarriedWoman")
    val MarriedWoman = model.getResource(resourcedURI)

    if (existenAfirmaciones(inf, Karen, RDF.`type`, MarriedWoman)) {
      println("La afirmación es cierta ")
    } else {
      println("La afirmación no es cierta ")
    }

  }

  def existenAfirmaciones(inf: InfModel, Sujeto: Resource, predicado: Property, objeto: Resource): Boolean = {
    var hayAfirmaciones = false
    val selector = new SimpleSelector(Sujeto, predicado, objeto)
    val iter = inf.listStatements(selector)
    hayAfirmaciones = iter.hasNext
    hayAfirmaciones
  }

}
