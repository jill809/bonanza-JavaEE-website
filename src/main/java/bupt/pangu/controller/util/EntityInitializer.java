package bupt.pangu.controller.util;

import java.lang.reflect.Method;
import java.util.Date;

public class EntityInitializer {

    public static void init(Object obj) {
        Class cls = obj.getClass();
        String name = cls.getSimpleName();

        // 试图找到 id 的 setter
        String idSetterName = "set" + name + "Id";
        Method setter = findMethod(cls, idSetterName, new Class[]{Integer.class, int.class});

        // 调用 setter，将 id 设为默认值
        try {
            // TODO: 用一个常量来代替这个值
            setter.invoke(obj, -1);
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }

        // 并设置日期为当前日期
        String dateSetterName = "set" + name + "Date";
        Method dateSetter = findMethod(cls, dateSetterName, new Class[]{Date.class});

        try {
            dateSetter.invoke(obj, new Date());
        } catch (Exception exp) {
            throw new RuntimeException(exp);
        }
    }

    private static Method findMethod(Class targetClass, String methodName, Class[] paramPossiblity) {
        Method method = null;

        for (Class c : paramPossiblity) {
            try {
                method = targetClass.getMethod(methodName, c);
                break;
            } catch (NoSuchMethodException exp) {
                // Try another way
            }
        }

        if (method == null) {
            throw new IllegalArgumentException("Method " + methodName
                + " not found on class [" + targetClass.getName() + "].");
        }

        return method;
    }
}
