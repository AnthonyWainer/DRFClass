package org.pucp.conocimiento.thirdTopic

import org.apache.jena.rdf.model._
import org.apache.jena.vocabulary.RDF
import org.apache.jena.rdf.model.InfModel
import java.io.PrintWriter

object ShowDerivationsRDF {
  def main(args: Array[String]): Unit = {

    val inputFileName = "/labs/topic3/nombre_soltera.rdf"
    val stream = getClass.getResourceAsStream(inputFileName)

    val model = ModelFactory.createDefaultModel
    model.read(stream, "")
    val inf = ModelFactory.createRDFSModel(model)
    inf.setDerivationLogging(true)

    var resourcedURI = model.expandPrefix("pucp:Karen")
    val Karen = model.getResource(resourcedURI)

    resourcedURI = model.expandPrefix("pucp:MarriedWoman")
    val MarriedWoman = model.getResource(resourcedURI)

    if (existenAfirmaciones(inf, Karen, RDF.`type`, MarriedWoman)) {
      println("La afirmación es cierta ");
      mostrarDerivaciones(inf, Karen, RDF.`type`, MarriedWoman)
    } else {
      println("La afirmación no es cierta ");
    }

  }

  def existenAfirmaciones(inf: InfModel, Sujeto: Resource, predicado: Property, objeto: Resource): Boolean = {
    var hayAfirmaciones = false
    val selector = new SimpleSelector(Sujeto, predicado, objeto)
    val iter = inf.listStatements(selector)
    hayAfirmaciones = iter.hasNext
    hayAfirmaciones
  }

  def mostrarDerivaciones(inf: InfModel, Sujeto: Resource, predicado: Property, objeto: Resource): Unit = {
    val out = new PrintWriter(System.out)
    val i = inf.listStatements(Sujeto, predicado, objeto)
    while (i.hasNext) {
      val s = i.nextStatement
      println("Statement is " + s)

      val id = inf.getDerivation(s)
      while ( {
        id.hasNext
      }) {
        id.next.printTrace(out, true)
      }
    }
    out.flush()
  }

}
