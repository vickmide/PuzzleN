package model;

import java.util.ArrayList;

public class BoardModel<E> extends AbstractModel<E> {

	ArrayList<PieceModel> mispiezas = new ArrayList<PieceModel>();
	
	//constructor
	public BoardModel(int rowNum, int columnNum, int pieceSize) {
		super(rowNum, columnNum, pieceSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(int blankPos, int movedPos) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addNewPiece(int id, int indexRow, int indexCol, String imagePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewPiece(int id, int indexRow, int indexCol) {
		// TODO Auto-generated method stub
		mispiezas.add(new PieceModel(id, indexRow, indexCol));
	}

	@Override
	public boolean isPuzzleSolve() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] getRandomMovement(int lastPos, int pos) {
		// TODO Auto-generated method stub
		return null;
	}

}
