package org.pucp.conocimiento.thirdTopic

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.{Model, ModelFactory}
import org.apache.jena.vocabulary.RDFS

object CreateDomainRDF {
  def main(args: Array[String]): Unit = {

    val model = ModelFactory.createDefaultModel
    val uri = "http://www.pucp.edu.pe/"
    val ns = "pucp"
    model.setNsPrefix(ns, uri)

    val Woman = crearRecurso(uri + "Woman", model)
    val MarriedWoman = crearRecurso(uri + "MarriedWoman", model)
    val hasMaidenName = crearPropiedad(uri, "hasMaidenName", model)


    model.add(MarriedWoman, RDFS.subClassOf, Woman)
    model.add(hasMaidenName, RDFS.domain, MarriedWoman)

    val Karen = crearRecurso(uri + "Karen", model)
    val Stephens = crearRecurso(uri + "Stephens", model)

    model.add(Karen, hasMaidenName, Stephens)

    grabarRDF("nombre_soltera.rdf", model)

  }

  private def grabarRDF(nmRDFFile: String, model: Model) = {
    var output: FileOutputStream = null

    try {
      output = new FileOutputStream(s"src/main/Resources/labs/class3/$nmRDFFile")
    }
    catch {
      case e: FileNotFoundException =>
        println("Ocurrio un error al crear el archivo.", e)
    }
    model.write(output, "RDF/XML-ABBREV")
  }

  private def crearPropiedad(uri_base: String, id: String, model: Model) = model.createProperty(uri_base + id)

  private def crearRecurso(id: String, model: Model) = model.createResource(id)
}
