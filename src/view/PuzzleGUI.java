package view;

import control.AbstractController;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PuzzleGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	// Instancia singleton
	public static PuzzleGUI instance = null;
	// Controlador
	public static AbstractController controller;
	// NÃºmero de filas
	public static int rowNum = 0;
	// NÃºmero de columnas
	public static int columnNum = 0;
	// TamaÃ±o de imagen
	public static int imageSize = 0;
	// Array de imagenes
	public static String[] imageList = null;
	// Panel de juego
	private BoardView boardView;
	// imagen
	public static File file = null;

	/**
	 * Constructor privado
	 */
	private PuzzleGUI() {
		super("GMD PuzzleGUI");
		boardView = new BoardView(rowNum, columnNum, imageSize, file);
		boardView.addMouseListener(controller);
		boardView.addKeyListener(controller);
		boardView.setFocusable(true);
		this.getContentPane().setLayout(new BorderLayout());
		this.setJMenuBar(createMenuBar());
		this.getContentPane().add(boardView, BorderLayout.CENTER);
		this.getContentPane().add(createSouthPanel(), BorderLayout.SOUTH);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(900, 650);
		this.setLocation(centerFrame());

	}

	// Singleton
	public static PuzzleGUI getInstance() {
		if (instance == null) {
			instance = new PuzzleGUI();
		}
		return (instance);
	}

	public static void initialize(AbstractController controller, int rowNum, int columnNum, int imageSize,
			File imageFile) {
		PuzzleGUI.controller = controller;
		PuzzleGUI.rowNum = rowNum;
		PuzzleGUI.columnNum = columnNum;
		PuzzleGUI.imageSize = imageSize;
		PuzzleGUI.file = imageFile;
	}

	// MÃ©todo que crea el panel inferior
	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton clutterButton = new JButton("Desordenar");// botÃ³n de desordenar
		clutterButton.setActionCommand("clutter");

		JButton solveButton = new JButton("Resolver");
		solveButton.setActionCommand("solve");

		clutterButton.addActionListener(controller);
		solveButton.addActionListener(controller);

		southPanel.add(clutterButton);
		southPanel.add(solveButton);

		return (southPanel);
	}

	// MÃ©todo que genera la barra de menus
	private JMenuBar createMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu archive = new JMenu("Archive");
		JMenu help = new JMenu("Help");

		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setActionCommand("newgame");
		JMenuItem load = new JMenuItem("Load");
		load.setActionCommand("load");
		JMenuItem save = new JMenuItem("Save");
		save.setActionCommand("save");
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		JMenuItem info = new JMenuItem("About");
		info.setActionCommand("info");

		archive.add(newGame);
		archive.add(load);
		archive.add(save);
		archive.add(exit);

		help.add(info);

		menu.add(archive);
		menu.add(help);

		newGame.addActionListener(controller);
		load.addActionListener(controller);
		save.addActionListener(controller);
		exit.addActionListener(controller);
		info.addActionListener(controller);

		return (menu);
	}

	// Centrar el frame en el centro de la pantalla.
	private Point centerFrame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int xCoord = (screenSize.width - this.getWidth()) / 2;
		int yCoord = (screenSize.height - this.getHeight()) / 2;
		return (new Point(xCoord, yCoord));
	}

	public static File showFileSelector() {
		File file = null;
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}
		return file;
	}

	public static void startNewGame() {
		OptionView setup = new OptionView(controller);
		setup.setVisible(true);
	}
	
	public static void stateInfo() {
		InfoView setup = new InfoView();
		setup.setVisible(true);
	}

	public BoardView getBoardView() {
		return (this.boardView);
	}

	// Metodo para actualizar boardview
	public void updateView(int row, int column, int size, File file) {
		controller.removeObserver(boardView);
		boardView.removeKeyListener(controller);
		this.remove(boardView);

		PuzzleGUI.rowNum = row;
		PuzzleGUI.columnNum = column;
		PuzzleGUI.imageSize = size;
		PuzzleGUI.file = file;

		boardView = null;
		boardView = new BoardView(row, column, size, file);
		boardView.addMouseListener(controller);
		boardView.addKeyListener(controller);
		this.getContentPane().add(boardView, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();

		controller.addObserver(boardView);
		boardView.requestFocus();
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void winStatus() {
		JOptionPane.showMessageDialog(this, "�Puzzle resuelto!");
	}

	public void errorStatus() {
		JOptionPane.showMessageDialog(this, "El tama�o de las piezas o filas/columnas es demasiado grande.",
				"Error en el tama�o de datos", JOptionPane.ERROR_MESSAGE);
	}

}
