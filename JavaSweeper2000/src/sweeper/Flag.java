package sweeper;
/* в этом классе описывается "верхний слой" нашего сапера, где располагаются
 *флажки, отметки взорванных бомб и тому подобное 
 */
class Flag {
	private Matrix flagMap; //объявляем матрицу наших флагов
	
	void start() { //метод для запуска класса
		flagMap = new Matrix(Box.CLOSED); //инициализируем нашу матрицу с закрытыми ячейками
	}
	
	Box get (Coord coord) { //геттер
		return flagMap.get(coord); //возвращаем координату ячейки нашей матрицы
	}

	public void setOpenedToBox(Coord coord) { //метод для открытия ячейки
		flagMap.set(coord, Box.OPENED); //по переданным координатам устанавливаем ячейку открытой
	}
}
