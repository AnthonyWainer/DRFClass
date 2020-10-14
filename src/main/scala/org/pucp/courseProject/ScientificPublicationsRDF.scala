package org.pucp.courseProject

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.{Model, ModelFactory}
import org.apache.jena.vocabulary.{RDFS, VCARD}

object ScientificPublicationsRDF {
  def main(args: Array[String]): Unit = {

    val model = ModelFactory.createDefaultModel
    val uri = "https://perucris.concytec.gob.pe/"
    val prefix = "pucp"
    model.setNsPrefix(prefix, uri)

    val scientificPublications = createResource(uri + "publicacionesCientificas", model)
    val publicationType = createResource(uri + "tipo", model)
    publicationType.addProperty(VCARD.TITLE, "Revistas")
    publicationType.addProperty(VCARD.TITLE, "Monografías Científicas")
    publicationType.addProperty(VCARD.TITLE, "Artículos Científicos")

    val publicationData = createResource(uri + "datosPublicacion", model)
    val wordsTitle = createResource(uri + "palabrasTitulo", model)

    model.add(scientificPublications, RDFS.subClassOf, publicationType)
    model.add(scientificPublications, RDFS.subClassOf, publicationData)
    model.add(scientificPublications, RDFS.subClassOf, wordsTitle)

    val matter = createProperty(uri, "materia", model)
    matter.addProperty(VCARD.NAME, "Ciencias Agrícolas")
    matter.addProperty(VCARD.NAME, "Ciencias Biológicas")
    matter.addProperty(VCARD.NAME, "Ciencias Exactas Y De La Tierra")
    matter.addProperty(VCARD.NAME, "Ciencias Sociales Aplicadas")
    matter.addProperty(VCARD.NAME, "Monografías Científicas")
    matter.addProperty(VCARD.NAME, "Humanidades")
    matter.addProperty(VCARD.NAME, "Ingeniería")
    matter.addProperty(VCARD.NAME, "Linguística, Letras Y Artes")

    model.add(publicationType, RDFS.subPropertyOf, matter)

    val author = createProperty(uri, "autor", model)
    author.addProperty(VCARD.NAME, "NeL")
    author.addProperty(VCARD.NAME, "Andreu")
    author.addProperty(VCARD.NAME, "Tom")

    val date = createProperty(uri, "fecha", model)
    date.addProperty(VCARD.FN, "12/06/2017")
    date.addProperty(VCARD.FN, "10/10/2019")
    date.addProperty(VCARD.FN, "05/02/2020")

    val place = createProperty(uri, "lugar", model)
    place.addProperty(VCARD.FN, "Kiev")
    place.addProperty(VCARD.FN, "LIma")
    place.addProperty(VCARD.FN, "Londres")

    val register = createProperty(uri, "registro", model)
    register.addProperty(VCARD.FN, "zxcq210")
    register.addProperty(VCARD.FN, "fgdfg13")
    register.addProperty(VCARD.FN, "dfsfsd2")

    model.add(publicationData, RDFS.subPropertyOf, author)
    model.add(publicationData, RDFS.subPropertyOf, date)
    model.add(publicationData, RDFS.subPropertyOf, place)
    model.add(publicationData, RDFS.subPropertyOf, register)

    val ISSN = createProperty(uri, "ISSN", model)
    ISSN.addProperty(VCARD.FN, "123")
    ISSN.addProperty(VCARD.FN, "321")
    val keyWords = createProperty(uri, "palabrasClaves", model)

    model.add(register, RDFS.domain, ISSN)
    model.add(wordsTitle, RDFS.subPropertyOf, keyWords)

    val ISSN1 = createResource(uri + "123ISSN", model)
    val ISSN2 = createResource(uri + "321ISSN", model)

    model.add(ISSN1, register, ISSN2)

    saveRDF("publicaciones_cientificas.rdf", model, "RDF/XML-ABBREV")
    saveRDF("publicaciones_cientificas.nt", model, "N-TRIPLE")
    saveRDF("publicaciones_cientificas.ttl", model, "TURTLE")

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
      new FileOutputStream(s"src/main/Resources/labs/courseProject/$fileName")
    }
    catch {
      case e: FileNotFoundException =>
        println("Ocurrio un error al crear el archivo.", e)
        null
    }
    model.write(output, formatType)
  }

  private def createProperty(uri_base: String, id: String, model: Model) = model.createProperty(uri_base + id)

  private def createResource(id: String, model: Model) = model.createResource(id)
}
