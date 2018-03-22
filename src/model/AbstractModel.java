package model;

import observer.Observer;

@SuppressWarnings("hiding")
public abstract class AbstractModel<PieceModel> implements Observer {

	// nÃºmero de filas
	protected int rowNum = 0;
	// nÃºmero de columnas
	protected int columnNum = 0;
	// tamaÃ±o de la pieza
	protected int pieceSize = 0;
	// lista de images

	// constructor de la clase.
	public AbstractModel(int rowNum, int columnNum, int pieceSize) {
		this.rowNum = rowNum;
		this.columnNum = columnNum;
		this.pieceSize = pieceSize;
	}

	public abstract void addNewPiece(int id, int indexRow, int indexCol);

	public abstract boolean isPuzzleSolve();

	public abstract int[] getRandomMovement(int lastPos, int pos);

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}

	public void setPieceSize(int pieceSize) {
		this.pieceSize = pieceSize;
	}

	public int getRowCount() {
		return rowNum;
	}

	public int getColumnCount() {
		return columnNum;
	}

	public int getPieceSize() {
		return pieceSize;
	}

}
