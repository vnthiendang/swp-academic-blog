package com.swp.model.criteria.filter;

public class LongFilter extends RangeFilter<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for IntegerFilter.</p>
     */
    public LongFilter() {
    }

    /**
     * <p>Constructor for IntegerFilter.</p>
     *
     * @param filter a {@link io.github.jhipster.service.filter.IntegerFilter} object.
     */
    public LongFilter(final LongFilter filter) {
        super(filter);
    }

    /**
     * <p>copy.</p>
     *
     * @return a {@link io.github.jhipster.service.filter.IntegerFilter} object.
     */
    public LongFilter copy() {
        return new LongFilter(this);
    }


}
