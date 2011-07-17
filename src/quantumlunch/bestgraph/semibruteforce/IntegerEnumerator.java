package quantumlunch.bestgraph.semibruteforce;

final class IntegerEnumerator implements StateEnumerator, ValueSource {
    private final ValueSource minimumValue;
    private final ValueSource upperLimit;
    private int value;
    private boolean valueRequiresReset = true;

    public IntegerEnumerator(ValueSource minimumValue, ValueSource upperLimit) {
        this.minimumValue = minimumValue;
        this.upperLimit = upperLimit;
    }

    IntegerEnumerator(ValueSource upperLimit) {
        this(FixedValueSource.ZERO, upperLimit);
    }

    public boolean advance() {
        if (withinUpperLimit()) {
            value++;
            return true;
        }
        valueRequiresReset = true;
        return false;
    }

    public Integer value() {
        if (valueRequiresReset) {
            value = minimumValue.value();
            valueRequiresReset = false;
        }
        return value;
    }

    private boolean withinUpperLimit() {
        return (upperLimit == null || value()  < upperLimit.value());
    }

    public String toString() {
        return "IntegerEnumerator[" + value() + "]";
    }
}
