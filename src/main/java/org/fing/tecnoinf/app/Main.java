package org.fing.tecnoinf.app;

//Imports
/*import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;*/
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


//Programa principal
public class Main {

   public void main(String[] args) {
	   //Ruta actual
	   System.out.println(System.getProperty("user.dir"));
   }

   Boolean validate(String xml, String xsd){
	   return false;
   }

   //Parseador de datos
   void parserXml(String xml){

	   try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			//Definicion de los datos
			DefaultHandler handler = new DefaultHandler() {
	
				//Funcion llamada al inicio de un elemento del Xml
				public void startElement(String uri, String localName,String qName,Attributes attributes) throws SAXException {

					//Imprimo tag
					System.out.println(qName + ": ");
				}

				//Funcion llamada al fin de un elemento del Xml
				public void endElement(String uri, String localName,String qName) throws SAXException {}

				//Funcion llamada entre el inicio y el fin de un elemento del Xml
				public void characters(char ch[], int start, int length) throws SAXException {
					System.out.println(new String(ch, start, length));
				}
				
				
			}; //End handler
			
			//Parsear datos
			saxParser.parse(xml, handler);
	   }
	   
	   //Capturo errores
	   catch (Exception e) {
		   e.printStackTrace();
	   }


   } //End parserXml


} //End Main



/**** Ejemplos *****/
/*
	Validacion de XSD online http://www.freeformatter.com/xml-validator-xsd.html
	SAXPArser https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html
	
	How to read XML file in Java – (SAX Parser): http://www.mkyong.com/java/how-to-read-xml-file-in-java-sax-parser/
	Xml parsing data DOM & SAX: http://www.java-samples.com/showtutorial.php?tutorialid=152
	Validar documentos XML usando XSD: http://www.bitriding.com/articulos/validacion-con-xsd.html
	Validación de ficheros XML usando un esquema XSD (JAVA): http://jesus.hernandez.abeleira.com/2014/03/validacion-de-ficheros-xml-contra-un.html
*/