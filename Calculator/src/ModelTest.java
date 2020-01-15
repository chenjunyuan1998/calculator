
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class ModelTest {
	Calc_Model model;
	@Test
	public void resultTest() throws IOException, ClassNotFoundException, SAXException, ParserConfigurationException{
		model = new Calc_Model();
		model.setA(5);
		model.setB(6);
		model.setOp('+');
		model.calculate();
		model.save("afile");
		model.exportToXML();
		double result = model.getResult();
		Calc_Model newModel = new Calc_Model();
		newModel.importfromXML("tmp1.xml");
		assertEquals(model.getResult(),newModel.getResult());
	}

}
