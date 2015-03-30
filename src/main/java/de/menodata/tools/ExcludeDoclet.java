
package de.menodata.tools;

import com.sun.javadoc.Doc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ProgramElementDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.doclets.standard.Standard;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;


/**
 * Verarbeitet das exclude-Tag.
 *
 * @author  Meno Hochschild
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ExcludeDoclet {

    //~ Statische Felder/Initialisierungen --------------------------------

    private static final String NAME = "doctags.exclude";

    //~ Methoden ----------------------------------------------------------

    public static boolean validOptions(
        String[][] options,
        DocErrorReporter reporter
    ) {

        return Standard.validOptions(options, reporter);

    }

    public static int optionLength(String option) {

        return Standard.optionLength(option);

    }

    public static LanguageVersion languageVersion() {

        return Standard.languageVersion();

    }

    public static boolean start(RootDoc root) {

        return Standard.start((RootDoc) process(root, RootDoc.class));

    }

    private static boolean exclude(Doc doc) {

        if (doc instanceof ProgramElementDoc) {
            PackageDoc pd = ((ProgramElementDoc) doc).containingPackage();

            if (pd.tags(NAME).length > 0) {
                return true;
            }

        }

        return doc.tags(NAME).length > 0;

    }

    private static Object process(
        Object obj,
        Class expect
    ) {

        if (obj == null) {
            return null;
        }

        Class cls = obj.getClass();

        if (cls.getName().startsWith("com.sun.")) {
            return Proxy.newProxyInstance(
                cls.getClassLoader(),
                cls.getInterfaces(),
                new ExcludeHandler(obj));
        } else if (expect.isArray()) {
            Class componentType = expect.getComponentType();
            Object[] array = (Object[]) obj;
            List list = new ArrayList(array.length);

            for (Object entry : array) {
                if (
                    (entry instanceof Doc)
                    && exclude((Doc) entry)
                ) {
                    continue;
                }

                list.add(process(entry, componentType));
            }

            return list.toArray(
                (Object[]) Array.newInstance(componentType, list.size()));
        } else {
            return obj;
        }

    }

    private static class ExcludeHandler
        implements InvocationHandler {

        private Object target;

        ExcludeHandler(Object target) {
            super();

            this.target = target;

        }

        @Override
        public Object invoke(
            Object proxy,
            Method method,
            Object[] args
        ) throws Throwable {

            if (args != null) {
                String methodName = method.getName();

                if (
                    methodName.equals("compareTo")
                    || methodName.equals("equals")
                    || methodName.equals("overrides")
                    || methodName.equals("subclassOf")
                ) {
                    args[0] = unwrap(args[0]);
                }
            }

            try {
                return process(
                    method.invoke(target, args),
                    method.getReturnType());
            } catch (InvocationTargetException ite) {
                throw ite.getTargetException();
            }

        }

        private Object unwrap(Object proxy) {
            if (proxy instanceof Proxy) {
                return ((ExcludeHandler) Proxy.getInvocationHandler(proxy)).target;
            }

            return proxy;
        }

    }

}
