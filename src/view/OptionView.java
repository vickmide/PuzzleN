package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import control.AbstractController;

public class OptionView extends JFrame {

	private static final long serialVersionUID = 1L;
	private AbstractController controller;

	// Selectores
	private static JRadioButton originalMode;
	private static JRadioButton loadImageMode;
	private static JComboBox<String> num_of_rows;
	private static JComboBox<String> num_of_columns;
	private static JComboBox<String> size_of_pieces;

	// Valores de tamaño
	public static final int imageWidth = 785;
	public static final int imageHeight = 460;

	// valores
	private static String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10" };
	private static String[] sizes = { "Auto", "25", "50", "75", "100", "125", "150", "175", "200" };
	private static String fileSeparator = System.getProperty("file.separator");
	private static String imagePath = System.getProperty("user.dir") + fileSeparator + "resources" + fileSeparator;

	public OptionView(AbstractController controller) {
		super("Setup");
		this.controller = controller;
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(createModeSelecction(), BorderLayout.NORTH);
		this.getContentPane().add(createConfigParams(), BorderLayout.CENTER);
		this.getContentPane().add(createSouthPanel(), BorderLayout.SOUTH);
		this.setResizable(false);
		this.setSize(250, 250);
		this.setLocation(centerFrame());
		num_of_rows.setEnabled(false);
		num_of_columns.setEnabled(false);
		size_of_pieces.setEnabled(false);
	}

	private JPanel createModeSelecction() {
		JPanel gameModePanel = new JPanel();
		gameModePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel text = new JLabel("SELECCIONA UN MODO");
		c.fill = GridBagConstraints.CENTER;
		c.ipady = 20;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 0;
		gameModePanel.add(text, c);

		originalMode = new JRadioButton("Por defecto");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 0;
		originalMode.setSelected(true);
		// Este evento se gestiona aqui porque solo afecta a la propia vista
		originalMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				num_of_rows.setEnabled(false);
				num_of_columns.setEnabled(false);
				size_of_pieces.setEnabled(false);
			}
		});
		gameModePanel.add(originalMode, c);

		loadImageMode = new JRadioButton("Imagen propia");
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 0;
		loadImageMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				num_of_rows.setEnabled(true);
				num_of_columns.setEnabled(true);
				size_of_pieces.setEnabled(true);
			}
		});
		gameModePanel.add(loadImageMode, c);

		ButtonGroup group = new ButtonGroup();
		group.add(originalMode);
		group.add(loadImageMode);

		return gameModePanel;
	}

	private JPanel createConfigParams() {

		JPanel confiParams = new JPanel();
		confiParams.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel textrow = new JLabel("Numero de filas:    ");
		c.fill = GridBagConstraints.EAST;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		confiParams.add(textrow, c);

		num_of_rows = new JComboBox<String>(values);
		num_of_rows.setSelectedIndex(1);
		c.fill = GridBagConstraints.WEST;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 15;
		confiParams.add(num_of_rows, c);

		JLabel textcolumn = new JLabel("Numero de columnas: ");
		c.fill = GridBagConstraints.WEST;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		confiParams.add(textcolumn, c);

		num_of_columns = new JComboBox<String>(values);
		num_of_columns.setSelectedIndex(1);
		c.fill = GridBagConstraints.EAST;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;
		confiParams.add(num_of_columns, c);

		JLabel textSize = new JLabel("Tamaño de la pieza: ");
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		confiParams.add(textSize, c);

		size_of_pieces = new JComboBox<String>(sizes);
		size_of_pieces.setSelectedIndex(0);
		c.fill = GridBagConstraints.CENTER;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 2;
		confiParams.add(size_of_pieces, c);

		return confiParams;
	}

	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton clutterButton = new JButton("Aceptar");
		clutterButton.setActionCommand("accept");
		clutterButton.addActionListener(controller);
		southPanel.add(clutterButton);

		return southPanel;
	}

	private Point centerFrame() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int xCoord = (screenSize.width - this.getWidth()) / 2;
		int yCoord = (screenSize.height - this.getHeight()) / 2;
		return (new Point(xCoord, yCoord));
	}

	public static String[] getInsertData() {

		String rows, columns, size, path;

		rows = values[num_of_rows.getSelectedIndex()];
		columns = values[num_of_columns.getSelectedIndex()];
		size = sizes[size_of_pieces.getSelectedIndex()];

		if (originalMode.isSelected()) {
			path = imagePath + fileSeparator + "default.png";
		} else {
			path = PuzzleGUI.showFileSelector().getPath();
		}

		if (size_of_pieces.getSelectedIndex() == 0) {
			size = "-1"; //tamaño automatico
		}
		
		//En el caso de que el puzzle vaya a ser demasiado grande por superar el ancho y alto
		//de la aplicacion, se devuelve null
		if ((Integer.parseInt(rows) * Integer.parseInt(size) > imageWidth)
				|| (Integer.parseInt(columns) * Integer.parseInt(size) > imageHeight)) {
			return null;
		}

		String[] results = { rows, columns, size, path };

		return results;
	}

}
