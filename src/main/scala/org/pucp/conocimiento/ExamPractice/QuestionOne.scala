package org.pucp.conocimiento.ExamPractice

import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.vocabulary.VCARD
import org.pucp.conocimiento.ExamPractice.QuerysRDF.showDeclarations

object QuestionOne {
  def main(args: Array[String]): Unit = {

    val studentsURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/alumnos.rdf"
    val groupsURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/grupos.rdf"
    val emailURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/correos.rdf"


    val studentModel = ModelFactory.createDefaultModel
    val emailModel = ModelFactory.createDefaultModel
    val groupModel = ModelFactory.createDefaultModel

    studentModel.read(studentsURI)
    emailModel.read(emailURI)
    groupModel.read(groupsURI)

    // Se une la data para realizar las operaciones
    val data = studentModel.union(emailModel).union(groupModel)

    println(" ")
    println("#" * 20, "PREGUNTA 1", "#" * 20)
    println(" ")

    val code = ModelFactory.createRDFSModel(data)
    val codeURI = "alum:20204704"

    val resource = data.getResource(codeURI)

    // Se muestra el Nombre
    showDeclarations(code, resource, VCARD.FN, null)
    // Se muestra los correos
    showDeclarations(code, resource, VCARD.EMAIL, null)

    println(" ")
    println("#" * 20, "PREGUNTA 2", "#" * 20)
    println(" ")

    val question2 = ModelFactory.createRDFSModel(data)
    val nameURI = "CACHAY GUIVIN, ANTHONY WAINER"

    // Se itera para buscar el nombre
    val resIter = data.listStatements()
    while (resIter.hasNext) {
      val res = resIter.nextStatement()

      // se verifica el nombre
      if (res.getObject.toString.endsWith(nameURI)) {
        val nameResource = data.getProperty(res.getSubject.toString)
        val perteneceURI = "https://raw.githubusercontent.com/andres-melgar/rdf/master/grupos.rdf#pertenece"
        val pertenece = data.getProperty(perteneceURI)

        // Se muestra el grupo
        showDeclarations(question2, nameResource, pertenece, null)
      }
    }

    println(" ")
    println("#" * 20, "PREGUNTA 3", "#" * 20)
    println(" ")

    val question3 = ModelFactory.createRDFSModel(data)
    val groupURI = "grupo:1"

    // Se itera para buscar el grupo
    val groupIter = data.listStatements()
    while (groupIter.hasNext) {
      val res = groupIter.nextStatement()

      // se verifica el grupo
      if (res.getObject.toString.endsWith(groupURI)) {
        val nameResource = data.getProperty(res.getSubject.toString)

        // Se obtiene los correos
        showDeclarations(question3, nameResource, VCARD.EMAIL, null)
      }
    }

  }
}
