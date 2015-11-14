package org.fing.tecnoinf.app;

//Imports
/*import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;*/

//Sax Imports
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//Dom Imports
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;


//Programa principal
public class Main {

   public static void main(String[] args) {

	   //Obtengo directorio actual
	   String dir = System.getProperty("user.dir");
	   
	   //Defino xsd
	   String xsd = dir + "\\archivos\\xsd\\validacion_factura.xsd";
	   
	   //Defino xml
	   String xml = dir + "\\archivos\\xml\\facturas-2.xml";
	   
	   try {
		   validateXml(xml, xsd);
		   parserXml(xml);
	   }
	   catch (Exception e) {
			System.err.println(e.getMessage());
		}
   }
   
   

   
   static void validateXml(String xml, String xsd){
	   try {
		   
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