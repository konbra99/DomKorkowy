package logic;

public class WeaponUtils {

	// funkcja uderzenia
	public final static int FUNC1 = 0;
	public final static int FUNC2 = 1;
	public final static int FUNC3 = 2;

	// kierunek uderzenia
	public static int CLOCKWISE = 1;
	public static int NONCLOCKWISE = -1;

	// stale matematyczne
	public final static float DEG_TO_RAD = 0.01745329251f;      /*  2pi/360  */
	public final static float RAD_TO_DEG = 57.2957795131f;      /*  360/2pi  */

	/**
	 * Oblicza kat odchylenia miecza w radianach. Wszystkie funkcje sa znormalizowane
	 * do [0,1]x[0,1], manipulujac parametrami zwiekszamy ten zakres
	 * @param function   wybrana funkcja uderzenia
	 * @param direction  kierunek uderzenia        (zgodnie/przeciwnie do ruchu wskazowek zegara)
	 * @param angle      maksymalny kat odchylenie (w stopniach)
	 * @param time       czas trwania uderzenia    (w klatkach)
	 * @param frame      bierzaca klatka
	 * @return kat odchylenia
	 */
	public static float angle(int function, int direction, float angle, int time, int frame) {
		float x = (float)frame / (float)time;

		float y = switch (function) {
			case FUNC1 -> func1(x);
			case FUNC2 -> func2(x);
			case FUNC3 -> func3(x);
			default    -> 0.0f;
		};

		return  angle * direction * y;
	}

	/**
	 * Zwraca tablice wszystkich katow odchylen.
	 * @return   tablica katow odchylen
	 */
	public static float[] anglesArray(int function, int direction, float angle, int time) {
		float [] angles = new float[time];
		for(int i = 0; i < time; i++)
			angles[i] = angle(function, direction, angle, time, i);
		return angles;
	}

	// parabola wklesla
	private static float func1(float x) {
		return 4*x - 4*x*x;
	}

	// 7.583*x-16.750*x^2+9.167*x^3
	private static float func2(float x) {
		return 7*x - 16*x*x + 9*x*x*x;
	}

	// podwojne uderzenie
	// 24.733*x-163.944*x^2+404.162*x^3-419.882*x^4+154.930*x^5
	private static float func3(float x) {
		return 24.733f*x - 163.944f*x*x + 404.162f*x*x*x - 419.882f*x*x*x*x + 154.930f*x*x*x*x*x;
	}
}
