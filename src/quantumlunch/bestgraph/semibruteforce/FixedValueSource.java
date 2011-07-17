package quantumlunch.bestgraph.semibruteforce;

final class FixedValueSource implements ValueSource {
    public static final ValueSource ZERO = fixedValue(0);

    private final Integer value;

    public static FixedValueSource fixedValue(int value) {
        return new FixedValueSource(value);
    }

    public FixedValueSource(int value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    @Override
    public String toString() {
        return "FixedValueSource{" +
                "value=" + value +
                '}';
    }
}
