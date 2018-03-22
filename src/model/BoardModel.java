package model;

import java.util.ArrayList;

public class BoardModel<E> extends AbstractModel<E> {

	ArrayList<PieceModel> pieceModel = new ArrayList<PieceModel>();

	// constructor
	public BoardModel(int rowNum, int columnNum, int pieceSize) {
		super(rowNum, columnNum, pieceSize);
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				addNewPiece(i * columnNum + j, i, j);
			}
		}
	}

	@Override
	public void update(int blankPos, int movedPos) {
		changePiece(blankPos, movedPos);
	}

	private void changePiece(int a, int b) {
		PieceModel pAux = pieceModel.get(a);
		pieceModel.set(a, pieceModel.get(b));
		pieceModel.set(b, pAux);
	}

	@Override
	public void addNewPiece(int id, int indexRow, int indexCol) {
		pieceModel.add(new PieceModel(id, indexRow, indexCol));
	}

	@Override
	public boolean isPuzzleSolve() {
		for (int i = 0; i < pieceModel.size(); i++) {
			if (pieceModel.get(i).getId() != i) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int[] getRandomMovement(int lastPos, int actualPos) {
		if (lastPos == -1) { // si no existe movimiento de referencia
			int[] initMove = { 0, 1 };
			return initMove;
		} else {
			int[] rndMove = { actualPos, randomNeighbour(lastPos, actualPos) };
			return rndMove;
		}
	}

	// Calcula un vecino aleatorio valido
	private int randomNeighbour(int lastPos, int actualPos) {
		int neighbour = 0;
		do {
			do {
				int n = (int) (Math.random() * 4);
				switch (n) {
				case 0: // arriba
					neighbour = actualPos - columnNum;
					break;
				case 1: // abajo
					neighbour = actualPos + columnNum;
					break;
				case 2: // izquierda
					neighbour = actualPos - 1;
					break;
				case 3: // derecha
					neighbour = actualPos + 1;
					break;
				}
			} while (neighbour == lastPos); // diferente a la anterior?
		} while (!canMove(actualPos, neighbour)); // es posible moverse?

		return neighbour;
	}

	// Calcula si el vecino candidato es un vecino valido
	public boolean canMove(int actualPos, int neighbour) {
		if ((neighbour < 0) || (neighbour > pieceModel.size() - 1)) {
			return false;
		}
		if ((actualPos - columnNum) == neighbour) {
			return true;
		}
		if ((actualPos + columnNum) == neighbour) {
			return true;
		}
		if (!((actualPos % columnNum) == 0) && (neighbour == (actualPos - 1))) {
			return true;
		}
		if (!(((actualPos + 1) % columnNum) == 0) && (neighbour == (actualPos + 1))) {
			return true;
		}
		return false;
	}

	public ArrayList<PieceModel> getModel() {
		return pieceModel;
	}

	// reinicia parametros del modelo
	public void resetModel(int row, int column, int size) {
		rowNum = row;
		columnNum = column;
		pieceSize = size;

		pieceModel = new ArrayList<PieceModel>();

		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				addNewPiece(i * columnNum + j, i, j);
			}
		}
	}
}
