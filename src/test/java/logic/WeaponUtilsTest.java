package logic;

import org.junit.Test;

public class WeaponUtilsTest {

	//@Test
	public void angleTest() {
		float angle = 30f;
		int time = 40;
		for(int i = 0; i < time; i++)
			System.out.println(WeaponUtils.angle(
					WeaponUtils.FUNC3,
					WeaponUtils.CLOCKWISE,
					angle, time, i));
	}

	//@Test
	public void anglesArrayTest() {
		float angle = 30f;
		int time = 40;
		float [] angles = WeaponUtils.anglesArray(WeaponUtils.FUNC1, WeaponUtils.CLOCKWISE, angle, time);
		for(var a: angles)
			System.out.println(a);
	}
}
