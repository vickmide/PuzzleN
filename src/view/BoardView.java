package view;

import observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.PieceModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase que representa la vista del tablero
 * 
 * @author Miguel Ãƒï¿½ngel
 * @version 1.0
 */
public class BoardView extends JPanel implements Observer {
	public static final int imageWidth = 96;
	public static final int imageHeight = 96;

	// lista con subimagenes
	private ArrayList<PieceView> iconArray = null;

	// Constructor para imagen por defecto
	public BoardView(int rowNum, int columnNum, int imageSize, String[] imageList) {
		super();
		iconArray = new ArrayList<PieceView>(imageList.length);

		try { // Se crean tantos PieceView como imagenes por defecto
			for (int i = 0; i < columnNum; i++) {
				for (int j = 0; j < rowNum; j++) {
					int id = i * columnNum + j;
					iconArray.add(new PieceView(id, i, j, imageSize, imageList[id]));
				}
			}
			//shufflePieces();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Constructor para imagen externa
	public BoardView(int rowNum, int columnNum, int imageSize, File imageFile) {
		super();
		iconArray = new ArrayList<PieceView>(rowNum * columnNum);
		BufferedImage subImages[] = splitImage(resizeImage(imageFile), columnNum, rowNum, imageSize);

		for (int i = 0; i < columnNum; i++) {
			for (int j = 0; j < rowNum; j++) {
				int id = i * columnNum + j;
				iconArray.add(new PieceView(id, i, j, imageSize, subImages[id]));
			}
		}
	}

	// Redimension de la imagen (96px * 96px)
	private BufferedImage resizeImage(File fileImage) {
		BufferedImage bufferedResized = null;
		Image image = null;

		try {
			image = ImageIO.read(fileImage).getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
			bufferedResized = new BufferedImage(image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			bufferedResized.getGraphics().drawImage(image, 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return (bufferedResized);
	}

	// Division de la imagen en i * j fragmentos
	private BufferedImage[] splitImage(BufferedImage image, int columnNum, int rowNum, int imageSize) {
		BufferedImage splitImage[] = new BufferedImage[columnNum * rowNum];

		// Recorre buffer aÃ±adiendo las subimagenes
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				splitImage[i * columnNum + j] = image.getSubimage(j * imageSize, i * imageSize, imageSize, imageSize);
			}
		}
		return (splitImage);
	}

	public void update(int blankPos, int movedPos) {
		changePiece(blankPos, movedPos);
		this.repaint();
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paint(Graphics g) {
		
		super.paintComponent(g);
		
		for (int i = 0; i < PuzzleGUI.rowNum; i++) {
			for (int j = 0; j < PuzzleGUI.columnNum; j++) {
				g.drawImage(iconArray.get(i * PuzzleGUI.columnNum + j).getImage(), j * PuzzleGUI.imageSize, i * PuzzleGUI.imageSize,
						this);
			}
		}
	}

	// Dado una posicion X e Y localizar una pieza
	private int locatePiece(int posX, int posY) {
		//calcula la posición del array sobre la que se ha pulsado
		int index = (posY / PuzzleGUI.imageSize) * PuzzleGUI.columnNum + posX / PuzzleGUI.imageSize;
		if (index > 8) { //esta la posición contenida en el array??
			return (-1); 
		}
		return (index);
	}

	/**
	 * Mueve la pieza y devuelve las coordenadas en un array de dos posiciones
	 * donde: la primera posicion representa la posicion actual de la pieza blanca y
	 * la segunda posicion representa la posicion actual de la pieza a mover.
	 * 
	 * @param posX
	 *            posicion X del puntero
	 * @param posY
	 *            posicion Y del puntero.
	 * @return Array de dos posiciones: posicion actual de la pieza blanca y
	 *         posicion actual de la pieza que tiene que ser movida.
	 */
	public int[] movePiece(int posX, int posY) {
		int posMoved = locatePiece(posX, posY);
		int posBlank = -1; 
		
		//recorre el iconArray buscando la pieza blanca
		for (int i = 0; i < iconArray.size(); i++) {
			if (iconArray.get(i).getId() == 0) {
				posBlank = i;
			}
		}
		
		int[] ids = { posBlank, posMoved };
		//System.out.println(canMove(ids[0], ids[1]));
		if (canMove(ids[0], ids[1])) {
			return ids;
		} 
		
		return null;
	}

	private boolean canMove (int b, int m) {
		
		if ((b - PuzzleGUI.columnNum) == m) {
			return true;
		} 
		
		if ((b + PuzzleGUI.columnNum) == m) {
			return true;
		} 
		
		if (!((b % PuzzleGUI.columnNum) == 0) && (m == (b - 1))) {
			return true;
		}
		
		if (!(((b+1) % PuzzleGUI.columnNum) == 0) && (m == (b + 1))) {
			return true;
		}
		return false;
	}
	
//	private void shufflePieces() {
//		for (int i = 0; i < iconArray.size() / 2; i++) {
//			changePiece((int) (Math.random() * 8), (int) (Math.random() * 8));
//		}
//	}

	private void changePiece(int a, int b) {
		PieceView pAux;
		pAux = iconArray.get(a);

		iconArray.set(a, iconArray.get(b));
		iconArray.set(b, pAux);
	}

	@Override
	public void shufflePieces() {
		// TODO Auto-generated method stub
		
	}

}
