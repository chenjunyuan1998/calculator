import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Calc_Controller {
	private Calc_Model model;
	private Calc_View view;
	public Calc_Controller(Calc_Model model,Calc_View view) {
		this.model = model;
		this.view = view;
		int num=1;
		for(int i =0;i<3;i++) {
			for(int j = 0;j<3;j++) {
				view.addButtonListener(i, j, new buttonActionListener(num));
				num++;
			}
		}
		view.addClearListener(new clearActionListener());
		view.addPlusListener(new plusActionListener());
		view.addMinusListener(new minusActionListener());
		view.addMultListener(new multActionListener());
		view.addDividListener(new dividActionListener());
		view.addZeroListener(new buttonActionListener(0));
		view.addEqualListener(new equalActionListener());
		view.addSaveListener(new saveActionListener());
		view.addLoadListener(new loadActionListener());
		
	}
	public class clearActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.setOp(' ');
			model.setA(0);
			model.setB(0);
			view.clearDisplay();
		}
		
	}
	public class buttonActionListener implements ActionListener{
		private int num;
		public buttonActionListener(int num) {
			this.num = num;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(!model.opSet()) {
				model.insertNumForA(num); 
				view.setDisplay(model.getA());
			}
			else if(model.opSet()) {
				model.insertNumForB(num);
				view.setDisplay(model.getB());
			}
		}
		
	}
	public class plusActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			model.setOp('+');
		}
		
	}
	public class minusActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			model.setOp('-');
		}
		
	}
	public class multActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			model.setOp('*');
		}
		
	}
	public class dividActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			model.setOp('/');
		}
		
	}
	public class equalActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(model.opSet()) {
				model.calculate();
				view.setDisplay(model.getResult());
				model.setA(model.getResult());
				model.setB(0);
				model.setOp(' ');
				model.setResult(0);
			}
		}
		
	}
	public class saveActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				model.save("afile");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public class loadActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Calc_Model newModel = new Calc_Model();
				newModel.load("afile");
				if(newModel.getResult()!=0) {
					view.setDisplay(newModel.getResult());
					model.setA(newModel.getResult());
					model.setB(0);
					model.setOp(' ');
				}else if(newModel.getB()!=0) {
					view.setDisplay(newModel.getB());
					model.setA(newModel.getA());
					model.setB(newModel.getB());
					model.setOp(newModel.getOp());
				}else {
					view.setDisplay(newModel.getA());
					model.setA(newModel.getA());
				}
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
