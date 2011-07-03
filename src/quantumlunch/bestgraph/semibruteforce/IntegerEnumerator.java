package quantumlunch.bestgraph.semibruteforce;

class IntegerEnumerator implements StateEnumerator, ValueSource {
    private final ValueSource[] upperLimits;
    private long value = 0;

    public IntegerEnumerator(ValueSource... upperLimits) {
        this.upperLimits = upperLimits;
    }

    public boolean advance() {
        if (satisfiesAllLimits()) {
            value++;
            return true;
        }
        value = 0;
        return false;
    }

    public Long value() {
        return value;
    }

    private boolean satisfiesAllLimits() {
        if (upperLimits == null) return true;
        for (ValueSource limitingEnumerator: upperLimits) {
            if (limitingEnumerator != null && value >= limitingEnumerator.value()) return false;
        }
        return true;
    }

    public String toString() {
        return "IntegerEnumerator[" + value + "]";
    }
}
