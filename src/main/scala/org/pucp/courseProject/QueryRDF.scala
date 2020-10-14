package org.pucp.courseProject

import java.io.PrintWriter

import org.apache.jena.rdf.model.{InfModel, ModelFactory, Property, Resource, SimpleSelector}
import org.apache.jena.vocabulary.RDF

import scala.collection.JavaConversions.asScalaIterator

object QueryRDF {
  def main(args: Array[String]): Unit = {

    val inputFileName = "/labs/courseProject/publicaciones_cientificas.rdf"
    val inputStream = getClass.getResourceAsStream(inputFileName)

    val model = ModelFactory.createDefaultModel
    model.read(inputStream, "")
    val inf = ModelFactory.createRDFSModel(model)

    var resourcedURI = model.expandPrefix("pucp:123ISSN")
    val code = model.getResource(resourcedURI)

    resourcedURI = model.expandPrefix("pucp:ISSN")
    val ISSN = model.getResource(resourcedURI)

    showDeclarations(inf, ISSN, RDF.`type`, null)

    if (existAffirmations(inf, code, RDF.`type`, ISSN)) {
      println("La afirmación es cierta ")
      showDerivations(inf, code, RDF.`type`, ISSN)
    } else {
      println("La afirmación no es cierta ")
    }

  }

  /**
   * Function to show declaration in list statements
   *
   * @param inf            The model
   * @param subject        The subject
   * @param predicate      The predicate
   * @param objectResource The object Resource
   */
  private def showDeclarations(infModel: InfModel, subject: Resource, predicate: Property, objectResource: Resource): Unit = {
    val selector = new SimpleSelector(subject, predicate, objectResource)
    infModel.listStatements(selector).foreach(println)
  }

  /**
   * Function for checking Affirmations in RDF model
   *
   * @param inf            The model
   * @param subject        The subject
   * @param predicate      The predicate
   * @param objectResource The object Resource
   * @return true or false
   */
  def existAffirmations(infModel: InfModel, subject: Resource, predicate: Property, objectResource: Resource): Boolean = {
    val selector = new SimpleSelector(subject, predicate, objectResource)
    val iterator = infModel.listStatements(selector)
    iterator.hasNext
  }

  /**
   * Function to show derivations in list statements
   *
   * @param inf            The model
   * @param subject        The subject
   * @param predicate      The predicate
   * @param objectResource The object Resource
   */
  def showDerivations(infModel: InfModel, subject: Resource, predicate: Property, objectResource: Resource): Unit = {
    val out = new PrintWriter(System.out)
    val i = infModel.listStatements(subject, predicate, objectResource)

    while (i.hasNext) {
      val s = i.nextStatement
      println("Statement is " + s)

      val id = infModel.getDerivation(s)
      while (id.hasNext) {
        id.next.printTrace(out, true)
      }
    }

    out.flush()
  }

}
