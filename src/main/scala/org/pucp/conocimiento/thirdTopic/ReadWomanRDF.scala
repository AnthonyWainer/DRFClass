package org.pucp.conocimiento.thirdTopic

import java.io.InputStream

import org.apache.jena.rdf.model.{ModelFactory, RDFNode, SimpleSelector}
import org.apache.jena.vocabulary.{RDF, RDFS}

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

    val selector = new SimpleSelector(Karen, RDF.`type`, MarriedWoman)

    val iter = inf.listStatements(selector)

    while (iter.hasNext) {
      println(iter.nextStatement.toString)
    }
  }
}
