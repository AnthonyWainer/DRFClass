package org.pucp.conocimiento.ExamPractice

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.{Model, ModelFactory, Property, Resource}

object GraphRDF {
  def main(args: Array[String]): Unit = {

    val model: Model = ModelFactory.createDefaultModel
    val URI = "http://examen/ejercicio1#"

    val anneHathaway: Resource = createResource(model, URI + "AnneHathaway")
    val shakespeare: Resource = createResource(model, URI + "Shakespeare")
    val married: Property = createProperty(model, URI + "married")
    createRelation(model, anneHathaway, married, shakespeare)

    val stratford: Resource = createResource(model, URI + "Stratford")
    val livedIn: Property = createProperty(model, URI + "livedIn")
    createRelation(model, shakespeare, livedIn, stratford)

    val england: Resource = createResource(model, URI + "England")
    val isIn: Property = createProperty(model, URI + "isIn")
    createRelation(model, stratford, isIn, england)

    val uk: Resource = createResource(model, URI + "UK")
    val partOf: Property = createProperty(model, URI + "partOf")
    createRelation(model, england, partOf, uk)

    val kingLear: Resource = createResource(model, URI + "KingLear")
    val macBeth: Resource = createResource(model, URI + "MacBeth")
    val wrote: Property = createProperty(model, URI + "wrote")
    createRelation(model, shakespeare, wrote, kingLear)
    createRelation(model, shakespeare, wrote, macBeth)

    val scotland: Resource = createResource(model, URI + "Scotland")
    val setIn: Property = createProperty(model, URI + "setIn")
    createRelation(model, macBeth, setIn, scotland)
    createRelation(model, scotland, partOf, uk)

    saveRDF("model.rdf", model, "RDF/XML-ABBREV")

  }

  private def createProperty(model: Model, uri_base: String) = model.createProperty(uri_base)

  private def createResource( model: Model, id: String) = model.createResource(id)

  private def createRelation(model: Model, inputResource: Resource, property: Property, outputResource: Resource): Unit = {
    model.add(inputResource, property, outputResource)
  }

  /**
   * Functions to saving RDF in path
   *
   * @param fileName The file name to save
   * @param model    The data model
   * @return model status
   */
  private def saveRDF(fileName: String, model: Model, formatType: String) = {
    val output = try {
      new FileOutputStream(s"src/main/Resources/labs/ExamPractice/$fileName")
    }
    catch {
      case e: FileNotFoundException =>
        println("Ocurrió un error al crear el archivo.", e)
        null
    }
    model.write(output, formatType)
  }

}

