package org.pucp.conocimiento.thirdTopic

import java.io.InputStream

import org.apache.jena.rdf.model.{ModelFactory, RDFNode, SimpleSelector}
import org.apache.jena.vocabulary.RDFS

object ReadWorkersRDF {
  def main(args: Array[String]): Unit = {

    val inputFileName = "/labs/topic3/relaciones_trabajadores.rdf"
    val stream: InputStream = getClass.getResourceAsStream(inputFileName)

    val model = ModelFactory.createDefaultModel
    model.read(stream, "")
    val inf = ModelFactory.createRDFSModel(model)

    var resourceURI = model.expandPrefix("pucp:Goldman")
    val goldman = model.getResource(resourceURI)

    resourceURI = model.expandPrefix("pucp:TheFirm")
    val TheFirm = model.getResource(resourceURI)

    resourceURI = model.expandPrefix("pucp:worksFor")
    val worksFor = model.getProperty(resourceURI)

    val selector = new SimpleSelector(goldman, worksFor, TheFirm)

    val iter = inf.listStatements(selector)
    while (iter.hasNext) {
      println(iter.nextStatement.toString)
    }
  }
}
