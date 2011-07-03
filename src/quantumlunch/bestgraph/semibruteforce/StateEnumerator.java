package quantumlunch.bestgraph.semibruteforce;

interface StateEnumerator {
    boolean advance();
    Object value();
}
