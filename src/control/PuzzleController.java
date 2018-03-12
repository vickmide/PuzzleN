package control;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import observer.Observer;
import view.PuzzleGUI;

public class PuzzleController extends AbstractController{

	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()) {
			case "clutter": observerList.get(0).shufflePieces();
		}
	}

	@Override
	public void notifyObservers(int blankPos, int movedPos) {
		for (Observer o: observerList) {
			o.update(blankPos, movedPos);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {	
		int [] pos = PuzzleGUI.getInstance().getBoardView().movePiece(e.getX(), e.getY());
		System.out.println(pos);
		/*
		 * ILLO
		 * CABESA
		 * 
		 */
		
		if (pos!=null){
			notifyObservers(pos[0], pos[1]);
		}	
	}
}
