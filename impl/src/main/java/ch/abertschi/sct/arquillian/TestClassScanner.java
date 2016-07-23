package ch.abertschi.sct.arquillian;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.arquillian.test.spi.TestClass;
import org.junit.runner.RunWith;
import org.reflections.Reflections;

/**
 * @author Andrin Bertschi
 */
public enum TestClassScanner
{

    GET;

    private Reflections reflections;

    TestClassScanner()
    {
        reflections = new Reflections("");
    }

    public <T extends Annotation> List<TestClass> findTestClassAnnotatedBy(Class<T> annotation)
    {
        Set<TestClass> testClasses = new HashSet<>();
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RunWith.class);
        for (Class<?> type : classes)
        {
            try
            {
                if (type.isInterface())
                {
                    continue;
                }
                TestClass testClass = new TestClass(type);

                Method[] methods = testClass.getMethods(annotation);
                if (methods != null && methods.length > 0)
                {
                    testClasses.add(testClass);
                }
            }
            catch (SecurityException e)
            { // not relevant, just skip
            }
        }
        return new ArrayList<>(testClasses);
    }
}
