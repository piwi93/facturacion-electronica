package org.fing.tecnoinf.app;

//Imports
/*import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;*/
import java.util.List;

//Sax Imports
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//Dom Imports
import org.jdom2.Document;
import org.jdom2.Element;
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
	   
	   //Defino xml a usar
	   String xml = dir + "\\archivos\\xml\\facturas-2.xml";
	   
	   try {
		   validateXml(xml, xsd);
		   parserXml(xml);
	   }
	   catch (Exception e) {
			System.err.println(e.getMessage());
		}
   }
   
   

   
   static void validateXml(String xml, String xsd) throws Exception {
	   
	   //Bandera booleana, mensaje.
	   boolean successful = false;
	   
	   try {
		   //Definiciones
		   XMLReaderJDOMFactory  XmlRXsdFactory = new XMLReaderXSDFactory(xsd);
		   SAXBuilder SaxBuilder = new SAXBuilder(XmlRXsdFactory);
		   Document XmlDocument = SaxBuilder.build(xml);
		   Element elementFacturas = XmlDocument.getRootElement();
		   
		   //Defino lista de facturas
		   List<Element> facturas = elementFacturas.getChildren("factura");
		   
		   //Logica del validador Dom
		   for (Element factura : facturas) {
				
			    //Defino numero de factura
			    int nroFactura = factura.getAttribute("nroFactura").getIntValue();
			    
			    //Defino subtotal/iva/total de la factura
			    Element subtotal = factura.getChild("subtotal");
				Element iva = factura.getChild("iva");
				Element total = factura.getChild("total");
				
			    //Capturo los items
			    List<Element> items = factura.getChild("items").getChildren("item");
				
			    //Calculo de subtotal real
			    float real_subtotal = 0;
			    for (Element item : items) {
					real_subtotal += ( Float.parseFloat(item.getChild("cantidad").getValue()) * Float.parseFloat(item.getChild("precio").getValue()) );
			    }
				
				//Calculo iva real
				float real_iva = (22 * real_subtotal) / 100;
				
				//Calculo total real
				float real_total = real_subtotal + real_iva;
				
				
				//Validaciones independientes: valores reales vs facturados
				
				if (real_subtotal != Float.valueOf(subtotal.getValue())) {
					throw new Exception("Factura nº"+nroFactura+", subtotal erroneo.");
				}
				if (real_iva != Float.valueOf(iva.getValue())) {
					throw new Exception("Factura nº"+nroFactura+", iva erroneo segun lista de items presentes.");
				}

				if (real_total != Float.valueOf(total.getValue())) {
					throw new Exception("Factura nº"+nroFactura+", error en suma del subtotal y el iva presentes.");
				}

			}//End for
		   
		    //Set successful flag to true
		    successful = true;
			
	   } //End try
	   
	   catch (Exception e) {
		   throw new Exception(e);
	   }
	   
	   finally {
		   if(successful){
			   System.out.println("\n_____________________________________");
			   System.out.println("Facturas correctas.");
			   System.out.println("_____________________________________\n");
		   }
		   else{
			   System.out.println("\n_____________________________________");
			   System.out.println("Facturas erroneas, por favor corregir.");
			   System.out.println("_____________________________________\n");
		   }
	   }
   } //End validate

   
   
   //Parseador de datos
   static void parserXml(String xml) throws Exception {

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
		   throw new Exception(e);
	   }


   } //End parserXml

} //End Main