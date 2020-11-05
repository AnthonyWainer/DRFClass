package org.pucp.conocimiento.ExamPractice

import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.vocabulary.RDF
import org.pucp.conocimiento.ExamPractice.QuerysRDF.showDeclarations

object QuestionTwo {
  def main(args: Array[String]): Unit = {
    val paradigmModel = ModelFactory.createDefaultModel
    val algorithmModel = ModelFactory.createDefaultModel
    val mlModel = ModelFactory.createDefaultModel

    paradigmModel.read("https://raw.githubusercontent.com/andres-melgar/rdf/master/paradigmas.rdf")
    algorithmModel.read("https://raw.githubusercontent.com/andres-melgar/rdf/master/algoritmos.rdf")
    mlModel.read("https://raw.githubusercontent.com/andres-melgar/rdf/master/aprendizaje_maquina.rdf")

    // se realiza la union
    val data = paradigmModel.union(algorithmModel).union(mlModel)
    val inf = ModelFactory.createRDFSModel(data)

    val resourcedURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/paradigmas.rdf#supervisado"

    val resource = data.getResource(resourcedURI)

    // se muestra los algoritmos del paradigma supervisado
    showDeclarations(inf, null, RDF.`type`, resource)

  }

}
