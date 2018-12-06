package com.lawfirm;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class TestConstant {

	public static <T> T createTestObject(Class<T> currentClass) {
		T object = null;
		try {
			object = currentClass.newInstance();

			List<Field> fields = Arrays.asList(currentClass.getDeclaredFields());

			for (Field field : fields) {
				if (field.getType() == String.class) {
					field.setAccessible(true);
					field.set(object, "test-field : " + field.getName());
				}
			}


		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		return object;
	}
}