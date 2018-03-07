import java.io.File;

import control.PuzzleController;
import model.BoardModel;
import model.PieceModel;
import view.BoardView;
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

/**
 * Clase principal que ejecuta el juego
 * 
 * @Author Miguel Ã�ngel
 * @version 1.0
 */
public class PuzzleApp {

	public static void main(String args[]) {
		//Variables
		int imageSize = 32;
		int rowNum = 3;
		int columnNum = 3;

		String fileSeparator = System.getProperty("file.separator"); // <-- windows: / , linux/iOS: \
		String imagePath = System.getProperty("user.dir") + fileSeparator + "resources" + fileSeparator;

		String[] imageList = { imagePath + "blank.gif", imagePath + "five.gif", imagePath + "two.gif",
				imagePath + "seven.gif", imagePath + "four.gif", imagePath + "one.gif", imagePath + "six.gif",
				imagePath + "three.gif", imagePath + "eight.gif" };
		
		//Cargar archivo deseado y colocarlo como puzzle
		File f = new File(imagePath + "test.png");
		//
		

		//Modelo de datos
		BoardModel<PieceModel> boardModel = new BoardModel<PieceModel>(rowNum, columnNum, imageSize);
		//Se llena el modelo de datos con el numero de piezas determinado
		for (int i = 0; i < rowNum; i++) {
			for (int j = 0; j < columnNum; j++) {
				boardModel.addNewPiece(i * columnNum + j, i, j);
			}						//   id            x  y
		}

		//Controlador
		PuzzleController puzzleController = new PuzzleController();
		puzzleController.addObserver(boardModel); 
		
		
		// Inicializamos la GUI (muestra datos)
		PuzzleGUI.initialize(puzzleController, rowNum, columnNum, imageSize, imageList);
		
		// Obtenemos la vista del tablero
		//BoardView WindowsVista = new BoardView(rowNum, columnNum, imageSize, imageList);
		
		// AÃ±adimos un nuevo observador al controlador
		//micontrolador.addObserver(WindowsVista);
		
		// Visualizamos la aplicaciÃ³n.
		PuzzleGUI.getInstance().setVisible(true);
	}
}
