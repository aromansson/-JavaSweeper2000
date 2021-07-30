package sweeper;

public class Game { //создаем основной игровой класс, в котором будет всё происходить??????
	
	private Bomb bomb; // объявляем переменную бомб 
	private Flag flag; //объявляем переменную флагов
	
	public Game(int cols, int rows, int bombs) { //конструктор принимает количество столбцов, строк и кол-ва бомб
		Ranges.setSize(new Coord(cols, rows)); //????сеттер нашего класса
		bomb = new Bomb(bombs); //создаем экземпляр нашего класса бомб (передаем общее количество бомб)
		flag = new Flag(); //создаем экземпляр нашего класса флагов
	}
	
	public void start() { //метод для старта игры
		bomb.start(); //проинициализируем бомбы
		flag.start(); //флаги тоже проинициализируем
	}
	
	public Box getBox(Coord coord) {//метод, который будет говорить, что изобразить в том или ином месте нашего экрана
		if(flag.get(coord) == Box.OPENED) //делаем проверку: если во флаге находится ОПЕНЕД
			return bomb.get(coord); //тогда возвращаем значение из матрицы бомб
		else
			return flag.get(coord); //а если нет, тогда получаем получаем координату из класса флагов
	}

	public void pressLeftButton(Coord coord) { //метод для обработки клика левой кнопкой мыши
		flag.setOpenedToBox (coord); //устанавливаем значение ячейки как открытой по координатам
	}

	public void pressRightButton(Coord coord) { //метод для обработки клика правой кнопкой мыши
		flag.setFlagedToBox(coord); //при нажатии устанавливаем на ячейку флажок
	}
}
