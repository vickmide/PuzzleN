package control;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import model.PieceModel;
import command.MoveCommand;
import confi.ConfiController;
import confi.GameData;
import model.BoardModel;
import observer.Observer;
import view.BoardView;
import view.OptionView;
import view.PuzzleGUI;

public class PuzzleController extends AbstractController {

	private static final int NUM_OF_MOVES = 50;
	boolean saberOganar = false;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch (arg0.getActionCommand()) {
		case "clutter": // Desordena las piezas del puzzle
			for (Observer o : observerList) {
				if (o instanceof BoardModel) {
					int aux[];
					for (int i = 0; i < NUM_OF_MOVES; i++) {
						aux = ((BoardModel) o).getRandomMovement(lastBlank(), currentBlank());
						putCommand(new MoveCommand(aux[0], aux[1]));
						notifyObservers(aux[0], aux[1]);
					}
				}
			}
			break;

		case "solve": // deshace todas las acciones para volver a la situación original
			while (!commandList.isEmpty()) {
				int[] aux = popCommand().undoCommand();
				notifyObservers(aux[0], aux[1]);
			}
			break;

		case "save": // Guarda partida
			ConfiController.writeXML(new GameData(PuzzleGUI.rowNum, PuzzleGUI.columnNum, PuzzleGUI.imageSize,
					PuzzleGUI.file.getPath(), commandList));
			break;

		case "info": // muestra informacion sobre la asignatura
			PuzzleGUI.stateInfo();
			break;

		case "exit": // cierra la aplicacion
			System.exit(0);

		case "load": // carga una partida
			for (Observer o : observerList) {
				if (o instanceof BoardView) {
					File f = ((BoardView) o).getFile(); // obtiene archivo del selector
					GameData g = ConfiController.readXML(f); // lee archivo
					// actualiza view
					PuzzleGUI.getInstance().updateView(g.getRowNum(), g.getColumnNum(), g.getImageSize(),
							(new File(g.getPath())));
					commandList = new ArrayList<MoveCommand>(); // resetea lista de comandos
					while (!g.getMovements().isEmpty()) {
						commandList.add(g.getMovements().remove(0)); // inserta los antiguos en la nueva
					}
					for (Observer m : observerList) {
						if (m instanceof BoardModel) { // resetea modelo
							((BoardModel) m).resetModel(g.getRowNum(), g.getColumnNum(), g.getImageSize());
						}
					}
					for (MoveCommand m : commandList) { // aplica los movimientos guardados
						int aux[] = m.redoCommand();
						notifyObservers(aux[0], aux[1]);
					}
				}
			}
			break;
		case "newgame": // juego nuevo
			for (Observer o : observerList) {
				if (o instanceof BoardView) {
					PuzzleGUI.startNewGame();
				}
			}

			break;
		case "accept": // se acepta juego nuevo
			String[] datos = OptionView.getInsertData();
			if (datos == null) { // si datos no validos
				PuzzleGUI.getInstance().errorStatus(); // error
				break;
			} else { // si datos validos
				// reinicio view
				PuzzleGUI.getInstance().updateView(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]),
						Integer.parseInt(datos[2]), new File(datos[3]));
				commandList = new ArrayList<MoveCommand>();

				for (Observer o : observerList) {
					if (o instanceof BoardModel) { // reinicio modelo
						((BoardModel<PieceModel>) o).resetModel(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]), 100);
					}
				}
			}
			break;
		}
		focusOnBoardview();
	}

	@Override
	public void notifyObservers(int blankPos, int movedPos) {
		for (Observer o : observerList) {
			o.update(blankPos, movedPos);
			if (o instanceof BoardModel) {
				if (((BoardModel) o).isPuzzleSolve()) {
					saberOganar = true;
				}
			}
			if ((o instanceof BoardView) && saberOganar) {
				saberOganar = false;
				PuzzleGUI.getInstance().winStatus();
			}
		}
	}

	public void focusOnBoardview() {
		for (Observer o : observerList) {
			if (o instanceof BoardView) {
				// System.out.println("ey");
				((BoardView) o).setFocusable(true);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Se obtienen coordenadas, se traducen y se devuelven en caso de que permitan
		// un movimiento valido
		int[] pos = PuzzleGUI.getInstance().getBoardView().movePiece(e.getX(), e.getY());
		if (pos != null) { // movimiento no valido = null
			putCommand(new MoveCommand(pos[0], pos[1]));
			notifyObservers(pos[0], pos[1]);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// rehacer (control + y)
		if ((arg0.getKeyCode() == KeyEvent.VK_Y) && ((arg0.getModifiers() & KeyEvent.CTRL_MASK) != 0)
				&& !redoCommandList.isEmpty()) {
			int[] aux = popRedoCommand().redoCommand();
			System.out.println(aux[0] + " " + aux[1]);
			notifyObservers(aux[0], aux[1]);
		}

		// deshacer (control + z)
		if ((arg0.getKeyCode() == KeyEvent.VK_Z) && ((arg0.getModifiers() & KeyEvent.CTRL_MASK) != 0)
				&& !commandList.isEmpty()) {
			System.out.println("Ctrl + Z");
			int[] aux = popCommand().undoCommand();
			notifyObservers(aux[0], aux[1]);
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
