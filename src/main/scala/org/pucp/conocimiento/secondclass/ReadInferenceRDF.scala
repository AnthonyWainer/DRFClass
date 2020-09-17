package org.pucp.conocimiento.secondclass

import java.io.InputStream

import org.apache.jena.rdf.model.{ModelFactory, SimpleSelector}
import org.apache.jena.vocabulary.RDF

object ReadInferenceRDF {
  def main(args: Array[String]): Unit = {

    val inputFileName = "/labs/class2/camisas.rdf"
    val stream: InputStream = getClass.getResourceAsStream(inputFileName)

    val model = ModelFactory.createDefaultModel
    model.read(stream, "")

    val inf = ModelFactory.createRDFSModel(model)

    val resourcedURI = model.expandPrefix("pucp:Shirts")
    val resource = model.getResource(resourcedURI)
    val selector = new SimpleSelector(null, RDF.`type`, resource)

    val iter = inf.listStatements(selector)
    while ( {
      iter.hasNext
    })
      println(iter.nextStatement.toString)
  }


}
