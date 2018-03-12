package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Clase que representa la vista del tablero
 * @author Miguel Ã�ngel
 * @version 1.0
 */
public class PieceView extends ImageIcon implements Cloneable{

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

    public PieceView(int id,int indexRow, int indexColumn,int imageSize,String imagePath) throws IOException{
        super();
        this.id = id;
        this.indexColumn = indexColumn;
        this.indexRow = indexRow;
        this.imageSize = imageSize;
        //this.imagePath = imagePath;  
        
        //Leer un String para sacar el fichero y convertirlo en object Image
        File fileImage = new File(imagePath);
        this.image = ImageIO.read(fileImage);
    }

    public PieceView(int id, int indexRow, int indexColumn,int imageSize,Image image){
        super();
        this.id = id;
        this.indexColumn = indexColumn;
        this.indexRow = indexRow;
        this.imageSize = imageSize;
        this.image = image;     
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

	public void setImage(Image image) {
		this.image = image;
	}

    public String toString(){
        return("id:"+id);
    }
    
    //
    // METODOS OBSOLETOS 
    //
    /*
    Path de la imagen
    private String imagePath;
    
    public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	*/
    //
	//
	//

}
