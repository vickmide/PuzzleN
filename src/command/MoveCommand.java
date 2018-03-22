package command;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "posicion")
@XmlAccessorType(XmlAccessType.FIELD)
public class MoveCommand implements Command {

	int action[] = new int[2];
	
	public MoveCommand() {
		
	}
	
	public MoveCommand(int blank, int pos) {
		action[0] = blank;
		action[1] = pos;
	}
	
	public int[] getAction() {
		return action;
	}

	public void setAction(int[] acction) {
		this.action = acction;
	}

	@Override //devuelve los valore inversos para "deshacer" la accion
	public int[] undoCommand() {
		int aux = action[0];
		action[0] = action[1];
		action[1] = aux;
		return action; 
	}

	@Override
	public int[] redoCommand() {
		return action;
	}
	
	public String getData() {
		String data = "{"+String.valueOf(action[0])+", "+String.valueOf(action[1]+"}");
		return data;
	}
		
	
	public int getPosMoved() {
		return action[1];
	}

	public int getBlank() {
		return action[0];
	}
	
}
