package control;

import observer.Observable;
import observer.Observer;
import command.MoveCommand;
import confi.ConfiController;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import command.Command;

/**
 * Interfaz que tiene que ser implementada por un controlador.
 * 
 * @author Miguel Ã�ngel
 * @version 1.0
 */
public abstract class AbstractController extends MouseAdapter implements KeyListener, ActionListener, Observable {
	protected List<Observer> observerList;
	protected List<MoveCommand> commandList;
	protected List<MoveCommand> redoCommandList;
	protected ConfiController confi;

	public AbstractController() {
		observerList = new ArrayList<Observer>();
		commandList = new ArrayList<MoveCommand>();
		redoCommandList = new ArrayList<MoveCommand>();
	}
	
	public void addObserver(Observer observer) {
		if (observer != null) {
			observerList.add(observer);
		}
	}

	public void removeObserver(Observer observer) {
		if (observer != null) {
			observerList.remove(observer);
		}
	}

	public void putCommand(Command c) {
		commandList.add((MoveCommand) c);
		redoCommandList.clear();
	}

	public Command popCommand() {
		if (commandList.isEmpty()) {
			return null;
		} else {
			MoveCommand aux =  commandList.remove(commandList.size() - 1);
			redoCommandList.add(aux);
			return aux;
		}
	}
	
	public Command popRedoCommand() {
		if (redoCommandList.isEmpty()) {
			return null;
		} else {
			return redoCommandList.remove(redoCommandList.size() - 1);
		}
	}

	public int lastBlank() {
		if (!commandList.isEmpty()) {
			return commandList.get(commandList.size() - 1).getBlank();
		}
		return -1;
	}

	public int currentBlank() {
		if (!commandList.isEmpty()) {
			return commandList.get(commandList.size() - 1).getPosMoved();
		}
		return -1;
	}

}
