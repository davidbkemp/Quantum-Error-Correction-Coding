package quantumlunch.bestgraph.semibruteforce;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static quantumlunch.bestgraph.semibruteforce.FixedValueSource.fixedValue;

public class IntegerEnumeratorTest {
    @Test
    public void valueShouldInitiallyBeZeoro() throws Exception {
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(5));
        assertThat(integerEnumerator.value(), is(0L));
    }

    @Test
    public void advanceShouldIncrementValue() throws Exception {
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(5));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.value(), is(1L));
    }

    @Test
    public void advanceShouldBeLimitedByUpperLimitAndResetToZero() throws Exception {
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(2));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(false));
        assertThat(integerEnumerator.value(), is(0L));
    }

    @Test
    public void advanceShouldBeLimitedByNextIntegerEnumeratorValue() throws Exception {
        IntegerEnumerator nextEnumerator = new IntegerEnumerator(fixedValue(8));
        nextEnumerator.advance();
        nextEnumerator.advance();
        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(8),  nextEnumerator);

        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(false));
        assertThat(integerEnumerator.value(), is(0L));

        assertThat(nextEnumerator.value(), is(2L)); // i.e. it is unchanged.
    }
    
    @Test
    public void advanceShouldBeLimitedByAllLimitingIntegerEnumeratorValue() throws Exception {
        IntegerEnumerator nextEnumerator1 = new IntegerEnumerator(fixedValue(8));
        nextEnumerator1.advance();
        nextEnumerator1.advance();
        nextEnumerator1.advance();

        IntegerEnumerator nextEnumerator2 = new IntegerEnumerator(fixedValue(8));
        nextEnumerator2.advance();
        nextEnumerator2.advance();

        IntegerEnumerator integerEnumerator = new IntegerEnumerator(fixedValue(8),  nextEnumerator1, nextEnumerator2);

        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(true));
        assertThat(integerEnumerator.advance(), is(false));
        assertThat(integerEnumerator.value(), is(0L));

    }
}
