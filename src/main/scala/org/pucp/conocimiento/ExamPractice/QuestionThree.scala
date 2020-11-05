package org.pucp.conocimiento.ExamPractice

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.{Model, ModelFactory, Property, Resource}
import org.apache.jena.vocabulary.RDFS

object QuestionThree {
  def main(args: Array[String]): Unit = {
    val URI = "http://musicontology.com/music#"

    val modelMusic = ModelFactory.createDefaultModel
    // se crea ontología de música
    createMusicOntology(modelMusic, URI)

    val modelAlbum = ModelFactory.createDefaultModel
    // se crea album
    createAlbum(modelAlbum, URI)
  }

  private def createMusicOntology(model: Model, URI: String): Unit = {
    // Se crea el nombre del Album
    val natureAlbum = createResource(model, URI + "nature")

    // Se crea dos canciones del Album
    val noiseTrack = createResource(model, URI + "Noise")
    val elanTrack = createResource(model, URI + "Elan")

    // se crea la propiedad isTrackOf
    val isTrackOf = createProperty(model, URI + "isTrackOf")

    // se enlaza como una sub propiedad del album
    model.add(isTrackOf, RDFS.subPropertyOf, natureAlbum)

    // se enlaza la canción al album
    createRelation(model, noiseTrack, isTrackOf, natureAlbum)
    createRelation(model, elanTrack, isTrackOf, natureAlbum)

    // se crea el nombre de la banda
    val nightWishBand = createResource(model, URI + "NightWishBand")

    // se agrega el album como clase de la banda
    model.add(natureAlbum, RDFS.subClassOf, nightWishBand)

    // se crea la propiedad isFanWith
    val isFanWith = createProperty(model, URI + "isFanWith")

    // se agrega dominio isFanWith con el album
    model.add(isFanWith, RDFS.domain, natureAlbum)

    // se crea dos fans, Alan y Jorge
    val alan = createResource(model, URI + "Alan")
    val jorge = createResource(model, URI + "Jorge")

    // se enlaza los dos fans
    model.add(alan, isFanWith, jorge)

    // se crea la propiedad hasMember
    val hasMember = createProperty(model, URI + "hasMember")

    // se enlaza hasMember a la banda
    model.add(hasMember, RDFS.range, nightWishBand)

    // se crea dos miembros de la banda TuomasHolopainen y MarcoHietala
    val TuomasHolopainen = createResource(model, URI + "TuomasHolopainen")
    val MarcoHietala = createResource(model, URI + "MarcoHietala")

    // se enlaza los miembros a la banda a la banda
    model.add(TuomasHolopainen, hasMember, nightWishBand)
    model.add(MarcoHietala, hasMember, nightWishBand)

    // se guarda
    saveRDF("musicOntology.rdf", model, "RDF/XML-ABBREV")
  }

  private def createAlbum(model: Model, URI: String) = {

    // Se crea el nombre del Album
    val natureAlbum = createResource(model, URI + "nature")
    val imaginaerumAlbum = createResource(model, URI + "imaginaerum")

    // se crea el nombre de la banda
    val nightWishBand = createResource(model, URI + "NightWishBand")

    // se agrega el album como clase de la banda
    model.add(natureAlbum, RDFS.subClassOf, nightWishBand)
    model.add(imaginaerumAlbum, RDFS.subClassOf, nightWishBand)

    // se guarda
    saveRDF("albumMusicOntology.rdf", model, "RDF/XML-ABBREV")
  }

  private def createProperty(model: Model, uri_base: String) = model.createProperty(uri_base)

  private def createResource(model: Model, id: String) = model.createResource(id)

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

