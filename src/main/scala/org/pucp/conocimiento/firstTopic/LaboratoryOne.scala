package org.pucp.conocimiento.firstTopic

import org.apache.jena.rdf.model.ModelFactory

import scala.collection.JavaConversions.asScalaIterator

object LaboratoryOne {
  def main(args: Array[String]): Unit = {

    val alumnosURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/alumnos.rdf"
    val gruposURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/grupos.rdf"
    val correosURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/correos.rdf"


    val alumnosModel = ModelFactory.createDefaultModel

    val correosModel = ModelFactory.createDefaultModel

    val gruposModel = ModelFactory.createDefaultModel

    println("Leer alumnos: ")
    alumnosModel.read(alumnosURI)

    println("Leer correos: ")
    correosModel.read(correosURI)

    println("Leer grupos: ")
    gruposModel.read(gruposURI)

    println("Unir data: ")
    val data = alumnosModel.union(correosModel).union(gruposModel)

    val iter = data.listStatements().toArray


    val codigo = "alum:20204579"
    val grupo = "grupo:1"

    val nombre = "AYAIPOMA CONDORI, ANDREU AMADEUS"

    println("Ejercicio 1")
    println("*" * 50)
    println("Ingresamos el código " + codigo)
    iter.filter(stm => stm.getSubject.toString == codigo).foreach(
      datos => {
        if (datos.getPredicate.getLocalName == "FN") {
          println(datos.getObject)
        }

        if (datos.getPredicate.getLocalName == "EMAIL") {
          println(datos.getObject)
        }
      }
    )

    println("")
    println("Ejercicio 2")
    println("*" * 50)
    iter.filter(stm => stm.getObject.toString == nombre).foreach(
      datos => {
        val codigo = datos.getSubject.toString
        iter.filter(stm => (stm.getSubject.toString == codigo)).map(
          p => {
            if (p.getPredicate.getLocalName == "pertenece") {
              println("El alumno: " + nombre + " con codigo tal: " + codigo + ", pertenece al " + p.getObject)
            }
          }
        )

      }
    )

    println("")
    println("Ejercicio 3")
    println("*" * 50)
    println("Ingresamos el grupo " + grupo)

    iter.filter(stm => stm.getObject.toString == grupo).foreach(
      datos => {

        val codigo = datos.getSubject.toString
        iter.filter(stm => (stm.getSubject.toString == codigo)).map(
          p => {
            if (p.getPredicate.getLocalName == "EMAIL") {
              println(p.getObject)
            }
          }
        )
      }
    )
  }
}
