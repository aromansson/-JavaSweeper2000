package sweeper;

import javax.management.timer.Timer;

public class Game { //движок игры
	

	private Bomb bomb; // объявляем переменную бомб 
	private Flag flag; //объявляем переменную флагов
	private GameState state; //объявляем переменную состояния игры
	private int bombsRemain; //??объявляем переменную количества оставшихся бомб
	private long mSeconds; //??здесь буду хранить секунды
	
	public GameState getState() { //геттер состояния игры
		return state; //возвращает состояние

	}

	public Game(int cols, int rows, int bombs) { //конструктор принимает количество столбцов, строк и кол-ва бомб
		Ranges.setSize(new Coord(cols, rows)); //????сеттер нашего класса
		bomb = new Bomb(bombs); //создаем экземпляр нашего класса бомб (передаем общее количество бомб)
		flag = new Flag(); //создаем экземпляр нашего класса флагов
	}
	
	public void start() { //метод для старта игры
		bomb.start(); //проинициализируем бомбы
		flag.start(); //флаги тоже проинициализируем
		state = GameState.PLAYED; //состояние игры - играем
		mSeconds = System.currentTimeMillis(); //запускаем таймер
	}
	
	public Box getBox(Coord coord) {//метод, который будет говорить, что изобразить в том или ином месте нашего экрана
		if(flag.get(coord) == Box.OPENED) //делаем проверку: если во флаге находится ОПЕНЕД
			return bomb.get(coord); //тогда возвращаем значение из матрицы бомб
		else
			return flag.get(coord); //а если нет, тогда получаем получаем координату из класса флагов
	}

	public void pressLeftButton(Coord coord) { //метод для обработки клика левой кнопкой мыши
		if(gameOver()) return; //если игра закончена - ничего не делаем
		openBox(coord); //специальный сложный метод
		checkWinner(); //проверяем, не выиграли ли мы
	}
	
	private void checkWinner() { // метод для проверки на победу
		if (state == GameState.PLAYED) { // проверяем, не проиграли ли мы))
			if (flag.getCountOfClosedBoxes() == bomb.getTotalBombs()) { // если количество закрытых клеток равно
																		// количеству бомб
				for (Coord coord : Ranges.getAllCoords()) { // перебираем все координаты
					if (bomb.get(coord) == Box.BOMB) { // если в указанной координате бомба
						flag.setFlagedToOpenedBombBox(coord); //меняем флажки на бомбочки
					}
				} // открываем клетку на закрытой клетке с бомбой
				mSeconds = (System.currentTimeMillis() - mSeconds)/1000;
				state = GameState.WINNER; // то мы победили
			}
		}
	}
	
	private void openBox(Coord coord) { //метод для обработки поведения игры
		switch (flag.get(coord)) { //перечисляем состояния флагов
		case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return; //если открыт - отерываем ячейки вокруг
		case FLAGED: return; //если флажок - ничего не делаем
		case CLOSED: //если закрыт - перебираем варианты
			switch (bomb.get(coord)) { //перечисляем значения бомб
			case ZERO: openBoxesAround (coord); return; // если открыто, то специальным методом рекурсивно открываем клетки вокруг
			case BOMB: openBombs(coord); return; //если бомба, то вызываем метод открытия бомб
			default : flag.setOpenedToBox (coord); return; //устанавливаем значение ячейки как открытой по координатам;
			}
		}
	}
	
	private void setOpenedToClosedBoxesAroundNumber (Coord coord) {//метод, открывающий закрытые ячейки вокруг числа
		if (bomb.get(coord) != Box.BOMB) {
			if (flag.getCountOfFlagedBoxesAround(coord) == bomb.get(coord).getNumber()) {
				for (Coord around : Ranges.getCoordsAround(coord)) 
					if (flag.get(around) == Box.CLOSED) 
						openBox(around);
			}
		}
	}
	
	private void openBombs(Coord bombed) { //метод для открытия всех бомб
		state = GameState.BOMBED; //устанавливаем состояние игры на бомбед
		flag.setBombedToBox(bombed); //передаем координату, где мы взорвались
		for (Coord coord : Ranges.getAllCoords()) { //перебираем все координаты
			if (bomb.get(coord) == Box.BOMB) { //если в указанной координате бомба
				flag.setOpenedToClosedBombBox (coord); //открываем клетку на закрытой клетке с бомбой
			}
			else {
				flag.setNobombToFlagedSafeBox (coord); //в ином случае ставим значок НОбомб
			}
		}
	}

	private void openBoxesAround(Coord coord) { //метод для рекурсивного открытия клеток вокруг данной клетки
		flag.setOpenedToBox(coord); //открываем текущую клетку
		for (Coord around : Ranges.getCoordsAround(coord)) { //перебираем все клетки вокруг
			openBox(around);
		}
	}

	public void pressRightButton(Coord coord) { //метод для обработки клика правой кнопкой мыши
		if(gameOver()) return; //если игра закончена - ничего не делаем
		flag.toggleFlagedToBox(coord); //при нажатии устанавливаем/снимаем флажок
	}

	private boolean gameOver() { //метод для проверки, проиграли или нет
		if (state == GameState.PLAYED) { //если состояние игры - играем
			return false; //то ложно
		}
		mSeconds = (System.currentTimeMillis() - mSeconds)/1000;
		return true; //иначе - правда
	}
	
	public int getCountOfFlags() { //??геттер для количества флагов
		return flag.countOfFlags; //??возвращаем количество флажков
	}
	
	public int getBombsRemain() { //??геттер количества оставшихся (предположительно) бомб
		int bombRemains = bomb.getTotalBombs() - flag.countOfFlags;
		if (bombRemains < 0) bombRemains = 0;
		return bombRemains;
	}
	
	public long getTime() {
		return mSeconds;
	}
}
