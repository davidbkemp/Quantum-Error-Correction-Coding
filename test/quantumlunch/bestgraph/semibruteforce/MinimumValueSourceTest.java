package quantumlunch.bestgraph.semibruteforce;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static quantumlunch.bestgraph.semibruteforce.FixedValueSource.fixedValue;
import static quantumlunch.bestgraph.semibruteforce.MinimumValueSource.minimumValue;
import static org.hamcrest.CoreMatchers.is;

public class MinimumValueSourceTest {
    @Test
    public void shouldReturnMinimumValue() throws Exception {
        assertThat(minimumValue(fixedValue(3), fixedValue(2), fixedValue(5)).value(), is(2));
    }

    @Test
    public void shouldIgnoreNulls() throws Exception {
        assertThat(minimumValue(fixedValue(3), null, fixedValue(5)).value(), is(3));
    }
}
