package aaberg.tools;

/**
 * Created by lars on 26.05.14.
 */
public abstract class FilterMethod {

    protected final FirFilterBuffer buffer;

    protected FilterMethod(int size) {
        this.buffer = new FirFilterBuffer(size);
    }

    public void insert(double value) {
        buffer.insert(value);
    }

    public abstract double getValue();


    public static FilterMethodFactory getMinFilterFactory() {
        return new FilterMethodFactory() {
            @Override
            public FilterMethod newFilterWrapper(int size) {
                return new FilterMethod(size) {
                    @Override
                    public double getValue() {
                        return buffer.minVal();
                    }
                };
            }

            @Override
            public String toString() {
                return "Min values";
            }
        };
    }

    public static FilterMethodFactory getMaxFilterFactory() {
        return new FilterMethodFactory() {
            @Override
            public FilterMethod newFilterWrapper(int size) {
                return new FilterMethod(size) {
                    @Override
                    public double getValue() {
                        return buffer.maxVal();
                    }
                };
            }

            @Override
            public String toString() {
                return "Max values";
            }
        };
    }

    public static FilterMethodFactory getMeanFilterFacory(){
        return new FilterMethodFactory() {
            @Override
            public FilterMethod newFilterWrapper(int size) {
                return new FilterMethod(size) {
                    @Override
                    public double getValue() {
                        return buffer.meanVal();
                    }
                };
            }

            @Override
            public String toString() {
                return "Mean values";
            }
        };
    }

    public static FilterMethodFactory getMedialFilterFactory() {
        return new FilterMethodFactory() {
            @Override
            public FilterMethod newFilterWrapper(int size) {
                return new FilterMethod(size) {
                    @Override
                    public double getValue() {
                        return buffer.medianVal();
                    }
                };
            }

            @Override
            public String toString() {
                return "Median values";
            }
        };
    }
}
