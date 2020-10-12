package org.pucp.conocimiento.work1

import java.io.{FileNotFoundException, FileOutputStream}

import org.apache.jena.rdf.model.{Model, ModelFactory}
import org.apache.jena.vocabulary.{RDFS, VCARD}

object publicacionesRDF {
  def main(args: Array[String]): Unit = {

    val model = ModelFactory.createDefaultModel
    val uri = "https://perucris.concytec.gob.pe/"
    val ns = "pucp"
    model.setNsPrefix(ns, uri)

    val publicacionesCientificas = crearRecurso(uri + "publicacionesCientificas", model)
    val tipo = crearRecurso(uri + "tipo", model)
    tipo.addProperty(VCARD.TITLE, "Revistas")
    tipo.addProperty(VCARD.TITLE, "Monografías Científicas")
    tipo.addProperty(VCARD.TITLE, "Artículos Científicos")

    val datosPublicacion = crearRecurso(uri + "datosPublicacion", model)
    val palabrasTitulo = crearRecurso(uri + "palabrasTitulo", model)

    model.add(tipo, RDFS.subClassOf, publicacionesCientificas)
    model.add(datosPublicacion, RDFS.subClassOf, publicacionesCientificas)
    model.add(palabrasTitulo, RDFS.subClassOf, publicacionesCientificas)

    val materia = crearPropiedad(uri, "materia", model)
    materia.addProperty(VCARD.NAME, "Ciencias Agrícolas")
    materia.addProperty(VCARD.NAME, "Ciencias Biológicas")
    materia.addProperty(VCARD.NAME, "Ciencias Exactas Y De La Tierra")
    materia.addProperty(VCARD.NAME, "Ciencias Sociales Aplicadas")
    materia.addProperty(VCARD.NAME, "Monografías Científicas")
    materia.addProperty(VCARD.NAME, "Humanidades")
    materia.addProperty(VCARD.NAME, "Ingeniería")
    materia.addProperty(VCARD.NAME, "Linguística, Letras Y Artes")

    model.add(materia, RDFS.subPropertyOf, tipo)

    val autor = crearPropiedad(uri, "autor", model)
    autor.addProperty(VCARD.NAME, "NeL")
    autor.addProperty(VCARD.NAME, "Andreu")
    autor.addProperty(VCARD.NAME, "Tom")

    val fecha = crearPropiedad(uri, "fecha", model)
    fecha.addProperty(VCARD.FN, "12/06/2017")
    fecha.addProperty(VCARD.FN, "10/10/2019")
    fecha.addProperty(VCARD.FN, "05/02/2020")

    val lugar = crearPropiedad(uri, "lugar", model)
    lugar.addProperty(VCARD.FN, "KIev")
    lugar.addProperty(VCARD.FN, "LIma")
    lugar.addProperty(VCARD.FN, "Londres")

    val registro = crearPropiedad(uri, "registro", model)
    registro.addProperty(VCARD.FN, "zxcq212")
    registro.addProperty(VCARD.FN, "fgdfg12")
    registro.addProperty(VCARD.FN, "dfsfsd2")

    model.add(autor, RDFS.subPropertyOf, datosPublicacion)
    model.add(fecha, RDFS.subPropertyOf, fecha)
    model.add(lugar, RDFS.subPropertyOf, lugar)
    model.add(registro, RDFS.subPropertyOf, registro)

    val ISSN = crearPropiedad(uri, "ISSN", model)
    ISSN.addProperty(VCARD.FN, "123")
    ISSN.addProperty(VCARD.FN, "321")
    val palabrasClaves = crearPropiedad(uri, "palabrasClaves", model)

    model.add(ISSN, RDFS.domain, registro)
    model.add(palabrasClaves, RDFS.subPropertyOf, palabrasTitulo)

    grabarRDF("publicaciones_cientificas.rdf", model)

  }

  private def grabarRDF(nmRDFFile: String, model: Model) = {
    var output: FileOutputStream = null

    try {
      output = new FileOutputStream(s"src/main/Resources/labs/work1/$nmRDFFile")
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
