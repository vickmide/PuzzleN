import java.io.File;

import control.PuzzleController;
import model.BoardModel;
import model.PieceModel;
import view.PuzzleGUI;

/*
 * Copyright 2016 Miguel Ã�ngel RodrÃ­guez-GarcÃ­a (miguel.rodriguez@urjc.es).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class PuzzleApp {

	public static void main(String args[]) {
		//Variables
		int imageSize = -1; //automatico
		int rowNum = 3;
		int columnNum = 3;

		String fileSeparator = System.getProperty("file.separator");
		String imagePath = System.getProperty("user.dir") + fileSeparator + "resources" + fileSeparator;
		
		//Cargar archivo deseado y colocarlo como puzzle
		File f = new File(imagePath + "default.png");
		
		//Modelo de datos 
		BoardModel<PieceModel> boardModel = new BoardModel<PieceModel>(rowNum, columnNum, imageSize);
		
		//Controlador de datos
		PuzzleController puzzleController = new PuzzleController();
		
		//Vista de datos
		PuzzleGUI.initialize(puzzleController, rowNum, columnNum, imageSize, f);
		
		//Se a�aden el modelo y la vista al controlador
		puzzleController.addObserver(boardModel); 
		puzzleController.addObserver(PuzzleGUI.getInstance().getBoardView()); 
		
		//Se visualiza la aplicaci�n
		PuzzleGUI.getInstance().setVisible(true);	
	}
}
