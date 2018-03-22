package confi;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import command.MoveCommand;

public class GameData {
	
	@XmlElement(name="rowsNum")
	public int rowNum;
	@XmlElement(name="columnsNum")
	public int columnNum;
	@XmlElement(name="imageSize")
	public int imageSize;
	@XmlElement(name="path")
	public String path;
	@XmlElement(name="position")
	public List<MoveCommand> movements;
	
	public GameData() {
		
	}
	
	public GameData(int row, int column, int size, String rute, List<MoveCommand> moves) {
		rowNum = row;
		columnNum = column;
		imageSize = size;
		path = rute;
		movements = moves;
	}
	
	public int getRowNum() {
		return this.rowNum;
	}
	
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	
	public int getColumnNum() {
		return this.columnNum;
	}
	
	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}
	
	public int getImageSize() {
		return this.imageSize;
	}
	
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}
	
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public List<MoveCommand> getMovements() {
		return this.movements;
	}
	
	public void setMovements(List<MoveCommand> movements) {
		this.movements = movements;
	}
	
	

}
