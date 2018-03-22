package view;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa la vista del tablero
 * @author Miguel Ã�ngel
 * @version 1.0
 */
public class PieceView extends ImageIcon implements Cloneable{

	private static final long serialVersionUID = 1L;
	//id de la imagen
    private int id;
    //Ã­ndice de fila
    private int indexRow;
    //Ã­ndice de columna
    private int indexColumn;
    //TamaÃ±o de la imagen
    private int imageSize;
    //Imagen
    private Image image;
    //Borde
    private Image back;
   
    public PieceView(int id, int indexRow, int indexColumn,int imageSize,Image image, Image back){
        super();
        this.id = id;
        this.indexColumn = indexColumn;
        this.indexRow = indexRow;
        this.imageSize = imageSize;
        this.image = image.getScaledInstance(imageSize - 5, imageSize - 5, Image.SCALE_DEFAULT);
        this.back = back.getScaledInstance(imageSize, imageSize, Image.SCALE_DEFAULT);
    }
    
    public int getIndexRow() {
        return indexRow;
    }

    public int getIndexColumn() {
        return indexColumn;
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }

    public int getId(){
        return this.id;
    }
    
	public Image getImage() {
		return image;
	}

	public Image getBorder() {
		return back;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

    public String toString(){
        return("id:"+id);
    }
    
 

}
