package org.pucp.conocimiento.secondTopic

import org.apache.jena.rdf.model.{ModelFactory, SimpleSelector}
import org.apache.jena.vocabulary.RDF

object Laboratory {
  def main(args: Array[String]): Unit = {
    val paradigmModel = ModelFactory.createDefaultModel
    val algorithmModel = ModelFactory.createDefaultModel
    val mlModel = ModelFactory.createDefaultModel

    paradigmModel.read("https://raw.githubusercontent.com/andres-melgar/rdf/master/paradigmas.rdf")
    algorithmModel.read("https://raw.githubusercontent.com/andres-melgar/rdf/master/algoritmos.rdf")
    mlModel.read("https://raw.githubusercontent.com/andres-melgar/rdf/master/aprendizaje_maquina.rdf")

    val data = paradigmModel.union(algorithmModel).union(mlModel)
    val inf = ModelFactory.createRDFSModel(data)

    val resourcedURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/paradigmas.rdf#supervisado"
    //val resourcedURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/aprendizaje_maquina.rdf#paradigma"

    val resource = data.getResource(resourcedURI)

    val selector = new SimpleSelector(null, RDF.`type`, resource)

    val iter = inf.listStatements(selector)

    while (iter.hasNext) {
      println(iter.nextStatement.toString)
    }
  }

  /*    val inf = ModelFactory.createRDFSModel(data)

      val resourcedURI = data.expandPrefix("rdf:supervisado")
      val resource = data.getResource(resourcedURI)
      val selector = new SimpleSelector(null, RDF.`type`, resource)

      val iter = inf.listStatements(selector)
      while ( {
        iter.hasNext
      })
        println(iter.nextStatement.toString)
    }*/
}
