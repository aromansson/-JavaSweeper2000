import java.awt.Dimension;
import sweeper.Box; //импортим наш класс, чтобы работал цикл, перебирающий картинки
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class JavaSweeper extends JFrame { //я так понимаю это "движок игры"
	
	private Game game; //объявляем переменную Гейм
	private JPanel panel; //объявляем переменную класса панель
	private final int COLS = 9; //сколько у нас будет столбцов
	private final int ROWS = 9; //сколько будет строк
	private final int BOMBS = 10; //сколько будет бомб
	private final int IMAGE_SIZE = 50; //размер картинки
	
	public static void main(String[] args) {
		new JavaSweeper(); //создаем класс джава свипер
	}
	
	private JavaSweeper() { //пишем конструктор класса
		game = new Game(COLS, ROWS, BOMBS); //инициализируем фасадный класс гейм
		game.start(); //запускаем игру
		setImages(); //подгружаем все картинки
		initPanel(); //инициализируем панель
		initFrame(); //инициалиризуем фрейм
	}
	
	private void initPanel() {
		panel = new JPanel() { //инициализируем панель через анонимный класс
			@Override
			protected void paintComponent(Graphics g) { //переопределяем метод пейнткомпонент
				super.paintComponent(g); //вызываем его из родительского класса
				for (Coord coord :Ranges.getAllCoords()) {//с помощью цикла
					g.drawImage((Image) game.getBox(coord).image, coord.x *IMAGE_SIZE, coord.y* IMAGE_SIZE, this); //задаем координаты для отрисовки, взяв их из фасадного класса с помощью переменной координат
				}
			}
		}; //конец анонимного класса
		
		panel.addMouseListener(new MouseAdapter() { //добавляем слушатель мышки (адаптер мышки)
			@Override
			public void mousePressed(MouseEvent e) { //переопределяем метод нажатия
				int x = e.getX() / IMAGE_SIZE; // если координату Х поделим на размер изображения, получим место, где мы находися
				int y = e.getY() / IMAGE_SIZE; // аналогично
				Coord coord = new Coord(x, y); // создаем переменную по полученным координатам
				if (e.getButton() == MouseEvent.BUTTON1) //проверка :если была нажата левая кнопка мыши
					game.pressLeftButton (coord); //то на указанной координате вызываем специальный метод
				if (e.getButton() == MouseEvent.BUTTON3) //проверка :если была нажата правая кнопка мыши
					game.pressRightButton (coord); //то на указанной координате вызываем специальный метод
				panel.repaint(); //обязательно перерисовываем панель
			}
		});
		
		panel.setPreferredSize(new Dimension //назначаем ей размер с помощью специального класса с размерами
				(Ranges.getSize().x* IMAGE_SIZE, 
						Ranges.getSize().y * IMAGE_SIZE)); 
		add(panel); //добавляем панель на фрейм
	}
	
	private void initFrame() { //специальный метод, в котором создаем фрейм и задаем необходимые свойства
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //программа будет завершаться при закрытии окна
		setTitle("Java Sweeper"); //заголовок
		setLocationRelativeTo(null); //размещение по центру экрана
		setResizable(false); //не даем разворачивать окно
		setVisible(true); //делаем окно видимым
		setIconImage(getImage("icon")); //устанавливаем иконку окна
		pack(); //подбирает оптимальный размер для размещения всех компонентов (уже всё создали, поэтому размер будет оптимальным)
	}
	
	private void setImages() { //установка всех картинок на экране сразу
		for (Box box : Box.values()) { //цикл, перебирающий все картинки
			box.image = getImage(box.name().toLowerCase()); //для каждого экземпляра бокс устанавливаем картинку, передаем имя нашего элемента
		}
	}
	
	private Image getImage (String name) { //метод для нахождения картинок и их получения
		String filename = "res/img/" + name + ".png"; //задаем название файла
	//	ImageIcon icon = new ImageIcon (getClass().getResource(filename)); //присваиваем файл с изображением переменной айкон
//		ImageIcon icon = new ImageIcon("res/img/bomb.png");
		ImageIcon icon = new ImageIcon(filename); //создаем изображение, найдя его по имени файла
		return icon.getImage(); //возвращаем переменную айкон и получаем картинку
	}

}
