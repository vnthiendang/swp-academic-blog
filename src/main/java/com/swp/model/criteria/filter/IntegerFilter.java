package com.swp.model.criteria.filter;

public class IntegerFilter extends RangeFilter<Integer>    {

    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for IntegerFilter.</p>
     */
    public IntegerFilter() {
    }

    /**
     * <p>Constructor for IntegerFilter.</p>
     *
     * @param filter a {@link io.github.jhipster.service.filter.IntegerFilter} object.
     */
    public IntegerFilter(final IntegerFilter filter) {
        super(filter);
    }

    /**
     * <p>copy.</p>
     *
     * @return a {@link io.github.jhipster.service.filter.IntegerFilter} object.
     */
    public IntegerFilter copy() {
        return new IntegerFilter(this);
    }

}
