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

//SAX and external XSD
import org.xml.sax.XMLReader;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.InputSource;


//Programa principal
public class Main {

   public static void main(String[] args) {

	   //Obtengo directorio actual
	   String dir = System.getProperty("user.dir");
	   
	   //Defino xsd
	   String xsd = dir + "\\archivos\\xsd\\validacion_factura.xsd";
	   
	   //Defino xml
	   String xml = dir + "\\archivos\\xml\\facturas-1.xml";
	   
	   try {
		   validateXml(xml, xsd);
		   //parserXml(xml);
	   }
	   catch (Exception e) {
			System.err.println(e.getMessage());
		}
   }

   
   static void validateXml(String xml, String xsd){
	   try {
		   SAXParserFactory factory = SAXParserFactory.newInstance();
		   factory.setValidating(false); 
		   factory.setNamespaceAware(true);
		   
		   SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		   SAXParser parser = null;
		   
	       factory.setSchema(schemaFactory.newSchema(new Source[] {new StreamSource( xsd )}));
	       parser = factory.newSAXParser();
	       
	       XMLReader reader = parser.getXMLReader();
	       
	       reader.setErrorHandler(
	          new ErrorHandler() {
	        	  
	            public void warning(SAXParseException e) throws SAXException {
	              System.out.println("WARNING: " + e.getMessage()); // do nothing
	            }
	
	            public void error(SAXParseException e) throws SAXException {
	              System.out.println("ERROR : " + e.getMessage());
	              throw e;
	            }
	
	            public void fatalError(SAXParseException e) throws SAXException {
	              System.out.println("FATAL : " + e.getMessage());
	              throw e;
	            }
	          }
	       );
	       
	       reader.parse(new InputSource(xml));
	       System.out.println("ESO EE!");
	   }
	   catch (Exception e) {
		   e.printStackTrace();
	   }
   }

   
   
   //Parseador de datos
   static void parserXml(String xml){

	   try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			
			//Manejador de los datos xml
			DefaultHandler handler = new DefaultHandler() {
				boolean flag = false;
	
				//Inicio de un tag
				public void startElement(String uri, String localName,String qName,Attributes attributes) throws SAXException {
					if(qName == "factura"){
						System.out.println("_____________________________________");
						System.out.println("Nro Factura: " + attributes.getValue(0));
					}
					if(qName == "empresa"){
						System.out.print("Empresa: ");
						flag = true;
					}
					if(qName == "rut"){
						System.out.print("RUT: ");
						flag = true;
					}
					if(qName == "direccion"){
						System.out.print("Dirección: ");
						flag = true;
					}
					if(qName == "fechaDeEmision"){
						System.out.print("Fecha de emisión: ");
						flag = true;
					}
					if(qName == "item"){
						System.out.println("\nItem:");
					}
					if(qName == "cantidad"){
						System.out.print("\tCantidad: ");
						flag = true;
					}
					if(qName == "descripcion"){
						System.out.print("\tDescripción: ");
						flag = true;
					}
					if(qName == "precio"){
						System.out.print("\tPrecio: ");
						flag = true;
					}
					if(qName == "subtotal"){
						System.out.print("\nSubtotal: ");
						flag = true;
					}
					if(qName == "iva"){
						System.out.print("IVA: ");
						flag = true;
					}
					if(qName == "total"){
						System.out.print("Total: ");
						flag = true;
					}
					
				}

				//Datos del tag
				public void characters(char ch[], int start, int length) throws SAXException {
					if(flag == true){
						System.out.println(new String(ch, start, length));
						flag = false;
					}
				}
				
				//Fin de un tag
				public void endElement(String uri, String localName,String qName) throws SAXException {
					if(qName == "factura"){
						System.out.println("_____________________________________\n");
					}
				}
				
			}; //End manejador
			
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