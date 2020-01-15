import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


import javax.naming.spi.DirStateFactory.Result;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

public class Calc_Model extends DefaultHandler implements Serializable {
	private double a;
	private double b;
	private char op;
	private double result;
	private double prev_result;
	private boolean ba,bb,br,bc=false;
	public Calc_Model() {
		this.a =0;
		this.b = 0;
		this.op = ' ';
		this.prev_result = 0;
		this.result = 0;
	}
	
	public void calculate() {
		if (op=='+') {
			result = a + b;
			prev_result = result;
		}
		else if(op=='-') {
			result = a - b;
			prev_result = result;
		}
		else if(op=='*') {
			result = a*b;
			prev_result = result;
		}
		else {
			result = a/b;
			prev_result = result;
		}
	}
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	public double getResult() {
		return result;
	}
	public void setPrev_result(double a) {
		prev_result = a;
	}
	public double getPrev_result() {
		return prev_result;
	}
	public boolean opSet() {
		if( getOp()=='+'||getOp()=='-'||getOp()=='*'||getOp()=='/') {
			return true;
		}
		return false;
	}
	public char getOp() {
		return op;
	}
	public void setOp(char op) {
		this.op = op;
	}
	public void insertNumForA(double a) {
		if(this.a ==0) {
			this.a = a;
		}
		else if(!opSet()) {
			this.a = this.a*10 + a;
		}
	}
	public void insertNumForB(double b) {
		if(this.b==0) {
			this.b = b;
		}
		else if(opSet()) {
			this.b = this.b*10 + b;
		}
	}
	public void setResult(double d) {
		result = d;
	}
	public void save(String filename) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
		oos.writeObject(this);
	}
	public void load(String filename) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
		Calc_Model model = (Calc_Model) ois.readObject();
			result = model.getResult();
			b=model.getB();
			a=model.getA();
			op=model.getOp();
	}
	public void exportToXML() throws SAXException, IOException {
		SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
			try {
				TransformerHandler handler = factory.newTransformerHandler();
				Transformer transformer = handler.getTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				File f = new File("tmp1.xml");
				if(!f.exists()){
					f.createNewFile();
				}
				StreamResult result = new StreamResult(new FileOutputStream(f));
				handler.setResult(result);
				
				AttributesImpl attr =new AttributesImpl();
				handler.startDocument();
				handler.startElement("", "", "calc_model", attr);
				
				attr.clear();
				handler.startElement("", "", "a", attr);
				handler.characters((getA()+"").toCharArray(),0, (getA()+"").toCharArray().length );
				handler.endElement("", "", "a");
				
				attr.clear();
				handler.startElement("", "", "b", attr);
				handler.characters((getB()+"").toCharArray(),0 , (getB()+"").toCharArray().length);
				handler.endElement("", "", "b");
				
				attr.clear();
				handler.startElement("", "", "result", attr);
				handler.characters((getResult()+"").toCharArray(), 0, (getResult()+"").toCharArray().length);
				handler.endElement("", "", "result");
				
				handler.endElement("", "", "calc_model");
				handler.endDocument();
				
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch(SAXException e) {
				e.printStackTrace();
			}
	
	}
	@Override
	public void startElement(String uri,String local,String qName,Attributes attributes)throws SAXException{
		if(qName.equalsIgnoreCase("calc_model")) {
			bc=true;
		}
		if(qName.equalsIgnoreCase("a")) {
			ba=true;
		}
		else if(qName.equalsIgnoreCase("b")) {
			bb=true;
		}
		else if(qName.equalsIgnoreCase("result")) {
			br=true;
		}
	}
	@Override
	public void characters(char[] ch,int start,int length) throws SAXException{
		String str = new String(ch,start,length);
		if(ba) {
			a = Double.parseDouble(str);
			ba=false;
		}
		else if(bb) {
			b = Double.parseDouble(str);
			bb=false;
		}else if(br) {
			result =Double.parseDouble(str);
			br=false;
		}
	}
	public void endElement(String uri,String localName,String qName)throws SAXException{
		
	}
	public Calc_Model importfromXML(String filename) throws SAXException, IOException, ParserConfigurationException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser s = spf.newSAXParser();
		s.parse(new File(filename), this);
		return this;
	}
}
