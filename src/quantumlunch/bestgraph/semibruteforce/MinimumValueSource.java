package quantumlunch.bestgraph.semibruteforce;

final class MinimumValueSource implements ValueSource {

    public static MinimumValueSource minimumValue(ValueSource... values) {
        return new MinimumValueSource(values);
    }

    private final ValueSource[] values;

    public MinimumValueSource(ValueSource... values) {
        this.values = values;
    }


    public Integer value() {
        int min = Integer.MAX_VALUE;
        for (ValueSource valueSource : values) {
            if (valueSource != null) {
                int value = valueSource.value();
                if (value < min) min = value;
            }
        }
        return min;
    }
}
