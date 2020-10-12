package org.pucp.conocimiento.work1

import org.apache.jena.rdf.model.{InfModel, _}
import org.apache.jena.vocabulary.RDF

object QueryRDF {
  def main(args: Array[String]): Unit = {
    val inputFileName = "/labs/work1/publicaciones_cientificas.rdf"
    val stream = getClass.getResourceAsStream(inputFileName)

    val model = ModelFactory.createDefaultModel
    model.read(stream, "")
    val inf = ModelFactory.createRDFSModel(model)

    val resourcedURI = model.expandPrefix("pucp:publicacionesCientificas")
    val publicacionesCientificas = model.getResource(resourcedURI)

    mostrarDeclaraciones(inf, publicacionesCientificas, RDF.`type`, null)

  }

  def mostrarDeclaraciones(inf: InfModel, Sujeto: Resource, predicado: Property, objeto: Resource) = {

    val selector = new SimpleSelector(Sujeto, predicado, objeto)
    val iter = inf.listStatements(selector)
    while (iter.hasNext) {
      println(iter.nextStatement.toString)
    }
  }


}
