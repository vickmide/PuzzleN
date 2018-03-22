package confi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import command.MoveCommand;

public class ConfiController {

	public static GameData readXML(File f) {
		SAXBuilder builder = new SAXBuilder();
		File XMLFile = f;
		try {
			Document document = (Document) builder.build(XMLFile);
			Element raiz = document.getRootElement();
			int row = Integer.parseInt(raiz.getChildText("rowsNum"));
			int column = Integer.parseInt(raiz.getChildText("columnsNum"));
			int size = Integer.parseInt(raiz.getChildText("imageSize"));
			String path = raiz.getChildText("path");
			System.out.println(path);

			List list = raiz.getChild("position").getChildren("posicion");
			List<MoveCommand> commandList = new ArrayList<MoveCommand>();
			System.out.println(list.size());

			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				commandList.add(new MoveCommand(Integer.parseInt(node.getChildText("blank")),
						Integer.parseInt(node.getChildText("moved"))));
			}
			GameData data = new GameData(row, column, size, path, commandList);
			return data;

		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeXML(GameData data) {

		String fileSeparator = System.getProperty("file.separator");
		String imagePath = System.getProperty("user.dir") + fileSeparator + "resources" + fileSeparator;

		Element root = new Element("confi");
		Document d = new Document();
		d.setRootElement(root);

		root.addContent(new Element("rowsNum").setText(Integer.toString(data.getRowNum())));
		root.addContent(new Element("columnsNum").setText(Integer.toString(data.getColumnNum())));
		root.addContent(new Element("imageSize").setText(Integer.toString(data.imageSize)));
		root.addContent(new Element("path").setText(data.path));
		Element position = new Element("position");

		for (int i = 0; i < data.getMovements().size(); i++) {
			Element posicion = new Element("posicion");
			int[] c = data.getMovements().get(i).getAction();
			posicion.addContent(new Element("blank").setText(String.valueOf(c[0])));
			posicion.addContent(new Element("moved").setText(String.valueOf(c[1])));
			position.addContent(posicion);
		}
		root.addContent(position);

		XMLOutputter XMLFile = new XMLOutputter();
		XMLFile.setFormat(Format.getPrettyFormat());

		try {
			XMLFile.output(d, System.out);
			XMLFile.output(d, new FileOutputStream(new File(imagePath + "save.xml")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
