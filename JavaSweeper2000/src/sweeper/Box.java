package sweeper;

public enum Box { //класс-перечисление
	ZERO,
	NUM1,
	NUM2,
	NUM3,
	NUM4,
	NUM5,
	NUM6,
	NUM7,
	NUM8,
	BOMB,
	OPENED,
	CLOSED,
	FLAGED,
	BOMBED,
	NOBOMB;
	
	public Object image;

	Box getNextNumberBox() {
		return Box.values()[this.ordinal() + 1]; //берем весь массив и возвращаем следующее значение после текущего
	}
}
