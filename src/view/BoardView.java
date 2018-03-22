package view;

import observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BoardView extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private static final int BASE_OFFSET_Y = 55;
	private static final int BASE_OFFSET_X = 60;
	public static final int imageWidth = 785;
	public static final int imageHeight = 460;

	String fileSeparator = System.getProperty("file.separator");
	String imagePath = System.getProperty("user.dir") + fileSeparator + "resources" + fileSeparator;

	private int size; // tamaño de la pieza
	private int offsetY;// Variables para
	private int offsetX;// centrar la imagen en el panel
	private boolean isRow;
	// lista con subimagenes
	private ArrayList<PieceView> iconArray = null;

	// imagen de fondo
	private Image background;
	private Image backpiece;

	// Constructor
	public BoardView(int rowNum, int columnNum, int imageSize, File imageFile) {
		super();
		iconArray = new ArrayList<PieceView>();
		loadBackground();

		if (columnNum > rowNum) { // si hay mas columnas que filas
			size = imageWidth / columnNum; // tamaño de las piezas siempre cuadrado y proporcional
			isRow = false;

		} else {
			size = imageHeight / rowNum;
			isRow = true;
		}
		
		if (imageSize != -1) { //en el caso de que el tamaño no se haya puesto automatico
							   //establece el tamaño dado 
			size = imageSize;
		}
		

		// Reescala y separa la imagen en segmentos (uno por pieza)
		BufferedImage subImages[] = splitImage(resizeImage(imageFile), columnNum, rowNum, size);

		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				int id = i * columnNum + j;
				iconArray.add(new PieceView(id, i, j, size, subImages[id], backpiece));
			}
		}
		iconArray.get(0).setImage(null); // coloca blanca la primera pieza

	}

	private void loadBackground() {
		try {
			File fileImage = new File(imagePath + "background.png");
			background = ImageIO.read(fileImage);
			fileImage = new File(imagePath + "border.jpg");
			backpiece = ImageIO.read(fileImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Redimension de la imagen (se adapta a numero de filas y columnas)
	private BufferedImage resizeImage(File fileImage) {
		BufferedImage bufferedResized = null;
		Image image = null;
		try {
			if (isRow) { // si filas > columnas -> imagen se estira en vertical
				image = ImageIO.read(fileImage).getScaledInstance(PuzzleGUI.columnNum * size, imageHeight,
						Image.SCALE_DEFAULT);
				offsetY = 0;
				offsetX = (imageWidth - PuzzleGUI.columnNum * size) / 2;
			} else { // si filas < colummnas -> imagen se estira en horizontal
				image = ImageIO.read(fileImage).getScaledInstance(imageWidth, PuzzleGUI.rowNum * size,
						Image.SCALE_DEFAULT);
				offsetY = (imageHeight - PuzzleGUI.rowNum * size) / 2;
				offsetX = 0;
			}

			bufferedResized = new BufferedImage(image.getWidth(null), image.getHeight(null),
					BufferedImage.TYPE_INT_ARGB);
			bufferedResized.getGraphics().drawImage(image, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (bufferedResized);
	}

	// Division de la imagen en i * j fragmentos
	private BufferedImage[] splitImage(BufferedImage image, int columnNum, int rowNum, int imageSize) {
		BufferedImage splitImage[] = new BufferedImage[columnNum * rowNum];

		// Recorre buffer agregando las subimagenes
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				splitImage[i * columnNum + j] = image.getSubimage(j * imageSize, i * imageSize, imageSize, imageSize);
			}
		}
		return (splitImage);
	}

	@Override // Actualiza view desde controller
	public void update(int blankPos, int movedPos) {
		changePiece(blankPos, movedPos);
		this.repaint();
	}

	//intercambia dos piezas en el array
	private void changePiece(int a, int b) {
		PieceView pAux;
		pAux = iconArray.get(a);

		iconArray.set(a, iconArray.get(b));
		iconArray.set(b, pAux);
	}

	public void update(Graphics g) {
		paint(g);
	}

	// actualiza graficos
	public void paint(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this);
		for (int i = 0; i < PuzzleGUI.rowNum; i++) {
			for (int j = 0; j < PuzzleGUI.columnNum; j++) {
				//Dibuja primero el borde (es una imagen mas grande por detras)
				g.drawImage(iconArray.get(i * PuzzleGUI.columnNum + j).getBorder(),
						(j * size) + offsetX + BASE_OFFSET_X, (i * size) + offsetY + BASE_OFFSET_Y, this);
				//superpone la imagen de la pieza
				g.drawImage(iconArray.get(i * PuzzleGUI.columnNum + j).getImage(), (j * size) + offsetX + BASE_OFFSET_X,
						(i * size) + offsetY + BASE_OFFSET_Y, this);
			}
		}
	}

	// Mueve la pieza
	public int[] movePiece(int posX, int posY) {
		int posMoved = locatePiece(posX, posY);
		int posBlank = -1;

		// recorre el iconArray buscando la pieza blanca
		for (int i = 0; i < iconArray.size(); i++) {
			if (iconArray.get(i).getId() == 0) {
				posBlank = i;
			}
		}
		int[] ids = { posBlank, posMoved };
		// Si el movimiento de posBlank a posMoved es posible se envian los datos de
		// vuelta al controller
		if (canMove(ids[0], ids[1])) {
			return ids;
		}
		return null;
	}

	// calcula la posición del array sobre la que se ha pulsado, al calculo se le
	// agrega el offset para
	// traducir bien la posicion
	private int locatePiece(int posX, int posY) {
		int index = ((posY - offsetY - BASE_OFFSET_Y) / size) * PuzzleGUI.columnNum
				+ ((posX - offsetX - BASE_OFFSET_X) / size);

		if (index > iconArray.size() - 1) { // esta la posición contenida en el array??
			return (-1);
		}
		return (index);
	}

	// Comprueba si una posicion de movimiento es valida
	private boolean canMove(int b, int m) {

		if ((b - PuzzleGUI.columnNum) == m) { // posible arriba?
			return true;
		}
		if ((b + PuzzleGUI.columnNum) == m) { // posible abajo?
			return true;
		}
		if (!((b % PuzzleGUI.columnNum) == 0) && (m == (b - 1))) { // posible izquierda?
			return true;
		}
		if (!(((b + 1) % PuzzleGUI.columnNum) == 0) && (m == (b + 1))) { // posible derecha?
			return true;
		}
		return false;
	}

	public File getFile() {
		return PuzzleGUI.showFileSelector();
	}
}
