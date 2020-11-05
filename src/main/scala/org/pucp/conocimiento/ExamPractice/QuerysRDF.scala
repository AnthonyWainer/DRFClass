package org.pucp.conocimiento.ExamPractice

import java.io.PrintWriter

import org.apache.jena.rdf.model.{InfModel, _}
import org.apache.jena.vocabulary.{RDF, RDFS}

object QuerysRDF {
  def main(args: Array[String]): Unit = {

    val albumMusicOntology = "/labs/ExamPractice/albumMusicOntology.rdf"
    val musicOntology = "/labs/ExamPractice/musicOntology.rdf"

    val albumMusicOntologyStream = getClass.getResourceAsStream(albumMusicOntology)
    val albumMusicOntologyStreamModel = ModelFactory.createDefaultModel
    albumMusicOntologyStreamModel.read(albumMusicOntologyStream, "")

    val musicOntologyStream = getClass.getResourceAsStream(musicOntology)
    val musicOntologyStreamModel = ModelFactory.createDefaultModel
    musicOntologyStreamModel.read(musicOntologyStream, "")

    // se une Album con Music
    val unionMusic = musicOntologyStreamModel.union(albumMusicOntologyStreamModel)

    val inf = ModelFactory.createRDFSModel(unionMusic)
    val resourcedURI = "http://musicontology.com/music#NightWishBand"
    val resource = unionMusic.getResource(resourcedURI)

    println(" ")
    println("#"*20, "UNION INFERENCIA", "#"*20)
    println(" ")
    println("Se muestra la union")
    showDeclarations(inf, null, RDFS.subClassOf, resource)

    val intersectionMusic = musicOntologyStreamModel.intersection(albumMusicOntologyStreamModel)
    val resourceInter = intersectionMusic.getResource(resourcedURI)
    val infInter = ModelFactory.createRDFSModel(intersectionMusic)

    println(" ")
    println("#"*20, "INTERSECTION INFERENCIA", "#"*20)
    println(" ")
    println("Se muestra la union")

    showDeclarations(infInter, null, RDFS.subClassOf, resourceInter)

    val infDerivations = ModelFactory.createRDFSModel(unionMusic)
    infDerivations.setDerivationLogging(true)


    println(" ")
    println("#"*20, "Mostrar derivaciones", "#"*20)
    println(" ")
    val isFanWithURI = "http://musicontology.com/music#isFanWith"
    val isFanWith = unionMusic.getResource(isFanWithURI)

    if (existenAfirmaciones(infDerivations, isFanWith, RDF.`type`, null)) {
      println("La afirmación es cierta ")
      mostrarDerivaciones(infDerivations, isFanWith, RDF.`type`, null)
    } else {
      println("La afirmación no es cierta ")
    }

  }

  def existenAfirmaciones(inf: InfModel, Sujeto: Resource, predicado: Property, objeto: Resource): Boolean = {
    val selector = new SimpleSelector(Sujeto, predicado, objeto)
    val iter = inf.listStatements(selector)
    iter.hasNext
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
  def showDeclarations(inf: InfModel, Sujeto: Resource, predicado: Property, objeto: Resource): Unit = {

    val selector = new SimpleSelector(Sujeto, predicado, objeto)
    val iter = inf.listStatements(selector)
    while (iter.hasNext) {
      println(iter.nextStatement.toString)
    }
  }
}
