package view;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoView extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	public InfoView(){
        super(PuzzleGUI.getInstance(), "InformaciÃ³n de la aplicaciÃ³n", true);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(buildCenter(), BorderLayout.CENTER);
        this.getContentPane().add(buildSouth(), BorderLayout.SOUTH);
        this.setLocationRelativeTo(PuzzleGUI.getInstance());
        this.setSize(380, 320);
        this.setResizable(false);
        this.setVisible(true);

    }

    private JTextPane buildCenter(){
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        try {

            Document document = textPane.getStyledDocument();

            Font titleFont = new Font("Times New Roman", Font.BOLD, 18);
            Font professorFont = new Font("Times New Roman", Font.ITALIC, 12);
            Font copyrightFont = new Font("Times New Roman", Font.ITALIC, 10);

            //Formato del titulo
            SimpleAttributeSet simpleSet = new SimpleAttributeSet();
            StyleConstants.setFontFamily(simpleSet, titleFont.getFamily());
            StyleConstants.setFontSize(simpleSet, titleFont.getSize());
            StyleConstants.setBold(simpleSet, true);
            document.insertString(document.getLength(), "PrÃ¡ctica Asignatura GestiÃ³n de Medios Digitales\n\n", simpleSet);

            simpleSet = new SimpleAttributeSet();
            StyleConstants.setFontFamily(simpleSet, professorFont.getFamily());
            StyleConstants.setFontSize(simpleSet, professorFont.getSize());
            document.insertString(document.getLength(), "Profesores: \n", simpleSet);
            document.insertString(document.getLength(), "LucÃ­a (lucia.serrano@urjc.es  \n",simpleSet);
            document.insertString(document.getLength(), "Miguel Ã�ngel (miguel.rodriguez@urjc.es)  \n\n",simpleSet);

            simpleSet = new SimpleAttributeSet();
            StyleConstants.setFontFamily(simpleSet, copyrightFont.getFamily());
            StyleConstants.setFontSize(simpleSet, copyrightFont.getSize());
            StyleConstants.setItalic(simpleSet, true);
            document.insertString(document.getLength(), "Copyright 2016 Miguel Ã�ngel RodrÃ­guez-GarcÃ­a (miguel.rodriguez@urjc.es).\n" +
                    " Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    " you may not use this file except in compliance with the License.\n" +
                    " You may obtain a copy of the License at\n" +
                    " \n" +
                    "       http://www.apache.org/licenses/LICENSE-2.0\n" +
                    " \n" +
                    " Unless required by applicable law or agreed to in writing, software\n" +
                    " distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    " WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    " See the License for the specific language governing permissions and\n" +
                    " limitations under the License.\n",simpleSet);
        }catch(Exception e){
            JOptionPane.showMessageDialog(PuzzleGUI.getInstance(), "Error no se puede generar informaciÃ³n", "GMD", JOptionPane.ERROR_MESSAGE);

        }
        return(textPane);
    }

    private JPanel buildSouth(){
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton accept = new JButton("Aceptar");
        accept.addActionListener(this);
        south.add(accept);

        return(south);
    }

    public void actionPerformed(ActionEvent ae){
        this.setVisible(false);
    }
}
