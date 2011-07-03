package quantumlunch.bestgraph.semibruteforce;

class FixedValueSource implements ValueSource {
    private final Long value;

    public static FixedValueSource fixedValue(long value) {
        return new FixedValueSource(value);
    }

    public FixedValueSource(long value) {
        this.value = value;
    }

    public Long value() {
        return value;
    }
}
