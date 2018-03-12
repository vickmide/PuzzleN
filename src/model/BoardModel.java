package model;

import java.util.ArrayList;

import view.PieceView;

public class BoardModel<E> extends AbstractModel<E> {

	ArrayList<PieceModel> pieceModel = new ArrayList<PieceModel>();

	// constructor
	public BoardModel(int rowNum, int columnNum, int pieceSize) {
		super(rowNum, columnNum, pieceSize);
		// TODO Auto-generated constructor stub
	}

	private void changePiece(int a, int b) {
		PieceModel pAux;
		pAux = pieceModel.get(a);

		pieceModel.set(a, pieceModel.get(b));
		pieceModel.set(b, pAux);
	}
	
	@Override
	public void update(int blankPos, int movedPos) {
		changePiece(blankPos, movedPos);
	}

	@Override
	public void addNewPiece(int id, int indexRow, int indexCol, String imagePath) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addNewPiece(int id, int indexRow, int indexCol) {
		// TODO Auto-generated method stub
		pieceModel.add(new PieceModel(id, indexRow, indexCol));
	}

	public void shufflePieces() {
		for (int i = 0; i < pieceModel.size() / 2; i++) {
			changePiece((int) (Math.random() * 8), (int) (Math.random() * 8));
		}
		System.out.println(1);
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
