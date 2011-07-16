package quantumlunch.bestgraph.semibruteforce;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static quantumlunch.bestgraph.semibruteforce.FixedValueSource.fixedValue;

public class IntegerEnumeratorTest {
    @Test
    public void valueShouldInitiallyDefaultToZeoro() throws Exception {
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(5));
        assertThat(integerEnumerator.value(), is(0));
    }
    
    @Test
    public void valueShouldInitiallyEqualSuppliedMinimum() throws Exception {
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(3), fixedValue(5));
        assertThat(integerEnumerator.value(), is(3));
    }

    @Test
    public void advanceShouldIncrementValue() throws Exception {
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(5));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.value(), is(1));
    }

    @Test
    public void advanceShouldBeLimitedByUpperLimitAndResetToMinimum() throws Exception {
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(2), fixedValue(4));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(false));
        assertThat(integerEnumerator.value(), is(2));
    }

    @Test
    public void initialValueNotComputedUntilFirstRequested() throws Exception {
        VariableValueSource variableValue = new VariableValueSource();
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(variableValue, fixedValue(5));
        variableValue.value = 3;
        assertThat(integerEnumerator.value(), is(3));
    }

    @Test
    public void initialValueNotComputedUntilAdvanced() throws Exception {
        VariableValueSource variableValue = new VariableValueSource();
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(variableValue, fixedValue(5));
        variableValue.value = 3;
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.value(), is(4));
    }

    @Test
    public void valueIsNotResetToMinimumUntilFirstNeeded() throws Exception {
        VariableValueSource variableValue = new VariableValueSource();
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(variableValue, fixedValue(5));
        variableValue.value = 3;
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(false));
        variableValue.value = 2;
        assertThat(integerEnumerator.value(), is(2));
    }

    private static class VariableValueSource implements ValueSource {

        private Integer value;

        public Integer value() {
            return value;
        }
    }
}
