import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Tag("SimpleJUnit5Class")
public class SimpleJUnit5Test {
    @Tag("simpleJUnit5Test")
    @Test
    public void simpleTest() {
        assertEquals(1,2);
    }

    @Tag("simpleJUnit5Test")
    @Tag("Slow")
    @Test
    public void testDivide() {
        assertThrows(ArithmeticException.class, () -> {
            Integer.divideUnsigned(42, 0);
        });
    }

    @Tag("simpleJUnit5Test")
    @Test
    @DisplayName("1 + 1 = 2")
    public void addNumber() {
        assertEquals(2, Calculator.add(1, 1), "1 + 1 should equal 2");
    }

    @Tag("simpleJUnit5Test")
    @ParameterizedTest(name = "isOdd")
    @ValueSource(ints = {1,3,5,-3,Integer.MAX_VALUE})
    public void areNumbersOdd(int number) {
        assertTrue(Calculator.isOdd(number));
    }

    @Tag("simpleJUnit5Test")
    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
            "0,    1,   1",
            "1,    2,   3",
            "49,  51, 100",
            "1,  100, 101"
    })
    public void addNumbers(int first, int second, int expectedResult) {
        assertEquals(expectedResult, Calculator.add(first, second),
                () -> first + " + " + second + " should equal " + expectedResult);
    }

    @Tag("simpleJUnit5Test")
    @ParameterizedTest(name = "{0} + {1} = {2} - csv")
    @CsvFileSource(resources = "/numbers.csv", delimiter = ';', numLinesToSkip = 1)
    public void addNumbersCSV(int first, int second, int expectedResult) {
        assertEquals(expectedResult, Calculator.add(first, second),
                () -> first + " + " + second + " should equal " + expectedResult);
    }

    @Tag("simpleJUnit5Test")
    @ParameterizedTest(name = "isOdd - List")
    @MethodSource("getNumbersToTestOddList")
    public void addNumbersList(int number) {
        assertTrue(Calculator.isOdd(number));
    }

    private static List<Integer> getNumbersToTestOddList() {
        return Arrays.asList(1, 3, 5);
    }

    @Tag("simpleJUnit5Test")
    @ParameterizedTest(name = "{index} ==> {0} + {1} = {2} - Stream")
    @MethodSource("getNumbersToTestAddStream")
    public void addNumbersStream(int first, int second, int expectedResult) {
        assertEquals(expectedResult, Calculator.add(first, second),
                () -> first + " + " + second + " should equal " + expectedResult);
    }

    private static Stream<Arguments> getNumbersToTestAddStream() {
        return Stream.of(
                Arguments.of(1,2,3),
                Arguments.of(-1,1,0),
                Arguments.of(4,4,8)
        );
    }

    @ParameterizedTest
    @MethodSource("stringIntAndListProvider")
    public void testWithMultiArgMethodSource(String str, int num, List<String> list) {
        assertEquals(5, str.length());
        assertTrue(num >=1 && num <=2);
        assertEquals(2, list.size());
    }

    private static Stream<Arguments> stringIntAndListProvider() {
        return Stream.of(
                arguments("apple", 1, Arrays.asList("a", "b")),
                arguments("lemon", 2, Arrays.asList("x", "y"))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Florian, Fricke, 25, true",
            "Max, Mustermann, 28, false",
    })
    public void testWithArgumentsAccessor(ArgumentsAccessor arguments) {
        Person person = new Person(
                arguments.getString(0),
                arguments.getString(1),
                arguments.getInteger(2),
                arguments.getBoolean(3));
        assertTrue(person.getAge() < 40);
    }

    @ParameterizedTest
    @CsvSource({
            "Florian, Fricke, 25, true",
            "Max, Mustermann, 28, false",
    })
    public void testWithArgumentsAccessor2(@AggregateWith(PersonAggregator.class) Person person) {
        assertTrue(person.getAge() < 40);
    }

    public static class PersonAggregator implements ArgumentsAggregator {
        @Override
        public Person aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
            return new Person(
                    arguments.getString(0),
                    arguments.getString(1),
                    arguments.getInteger(2),
                    arguments.getBoolean(3));
        }
    }
}

