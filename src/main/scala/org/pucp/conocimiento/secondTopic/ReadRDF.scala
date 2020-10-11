package org.pucp.conocimiento.secondTopic

import java.io.InputStream

import org.apache.jena.rdf.model.{ModelFactory, RDFNode, SimpleSelector}
import org.apache.jena.vocabulary.RDFS

object ReadRDF {
  def main(args: Array[String]): Unit = {

    val inputFileName = "/labs/topic2/camisas.rdf"
    val stream: InputStream = getClass.getResourceAsStream(inputFileName)

    val model = ModelFactory.createDefaultModel
    model.read(stream, "")

    val resourcedURI = model.expandPrefix("pucp:Henleys")
    val resource = model.getResource(resourcedURI)
    val selector = new SimpleSelector(resource, RDFS.subClassOf, null.asInstanceOf[RDFNode])

    val iter = model.listStatements(selector)
    while ( {
      iter.hasNext
    })
      println(iter.nextStatement.toString)
  }
}
