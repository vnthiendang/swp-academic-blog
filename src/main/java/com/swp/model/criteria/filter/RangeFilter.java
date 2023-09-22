package com.swp.model.criteria.filter;

import java.util.Objects;

public class RangeFilter<FIELD_TYPE extends Comparable<? super FIELD_TYPE>> extends Filter<FIELD_TYPE> {

    private static final long serialVersionUID = 1L;

    private FIELD_TYPE greaterThan;
    private FIELD_TYPE lessThan;
    private FIELD_TYPE greaterThanOrEqual;
    private FIELD_TYPE lessThanOrEqual;

    /**
     * <p>Constructor for RangeFilter.</p>
     */
    public RangeFilter() {
    }

    /**
     * <p>Constructor for RangeFilter.</p>
     *
     * @param filter a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter(final RangeFilter<FIELD_TYPE> filter) {
        super(filter);
        this.greaterThan = filter.greaterThan;
        this.lessThan = filter.lessThan;
        this.greaterThanOrEqual = filter.greaterThanOrEqual;
        this.lessThanOrEqual = filter.lessThanOrEqual;
    }

    /**
     * <p>copy.</p>
     *
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> copy() {
        return new RangeFilter<>(this);
    }

    /**
     * <p>Getter for the field <code>greaterThan</code>.</p>
     *
     * @return a FIELD_TYPE object.
     */
    public FIELD_TYPE getGreaterThan() {
        return greaterThan;
    }

    /**
     * <p>Setter for the field <code>greaterThan</code>.</p>
     *
     * @param greaterThan a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> setGreaterThan(FIELD_TYPE greaterThan) {
        this.greaterThan = greaterThan;
        return this;
    }

    /**
     * <p>Getter for the field <code>greaterThanOrEqual</code>.</p>
     *
     * @return a FIELD_TYPE object.
     */
    public FIELD_TYPE getGreaterThanOrEqual() {
        return greaterThanOrEqual;
    }

    /**
     * <p>Setter for the field <code>greaterThanOrEqual</code>.</p>
     *
     * @param greaterThanOrEqual a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> setGreaterThanOrEqual(FIELD_TYPE greaterThanOrEqual) {
        this.greaterThanOrEqual = greaterThanOrEqual;
        return this;
    }

    /**
     * <p>Setter for the field <code>greaterThanOrEqual</code>.</p>
     *
     * @param greaterThanOrEqual a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     * @deprecated Equivalent to {@link #setLessThanOrEqual}
     */
    @Deprecated
    public RangeFilter<FIELD_TYPE> setGreaterOrEqualThan(FIELD_TYPE greaterThanOrEqual) {
         this.greaterThanOrEqual = greaterThanOrEqual;
         return this;
    }

    /**
     * <p>Getter for the field <code>lessThan</code>.</p>
     *
     * @return a FIELD_TYPE object.
     */
    public FIELD_TYPE getLessThan() {
        return lessThan;
    }

    /**
     * <p>Setter for the field <code>lessThan</code>.</p>
     *
     * @param lessThan a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> setLessThan(FIELD_TYPE lessThan) {
        this.lessThan = lessThan;
        return this;
    }

    /**
     * <p>Getter for the field <code>lessThanOrEqual</code>.</p>
     *
     * @return a FIELD_TYPE object.
     */
    public FIELD_TYPE getLessThanOrEqual() {
        return lessThanOrEqual;
    }

    /**
     * <p>Setter for the field <code>lessThanOrEqual</code>.</p>
     *
     * @param lessThanOrEqual a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     */
    public RangeFilter<FIELD_TYPE> setLessThanOrEqual(FIELD_TYPE lessThanOrEqual) {
        this.lessThanOrEqual = lessThanOrEqual;
        return this;
    }

    /**
     * <p>Setter for the field <code>lessThanOrEqual</code>.</p>
     *
     * @param lessThanOrEqual a FIELD_TYPE object.
     * @return a {@link io.github.jhipster.service.filter.RangeFilter} object.
     * @deprecated Equivalent to {@link #setLessThanOrEqual}
     */
    @Deprecated
    public RangeFilter<FIELD_TYPE> setLessOrEqualThan(FIELD_TYPE lessThanOrEqual) {
         this.lessThanOrEqual = lessThanOrEqual;
         return this;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final RangeFilter<?> that = (RangeFilter<?>) o;
        return Objects.equals(greaterThan, that.greaterThan) &&
            Objects.equals(lessThan, that.lessThan) &&
            Objects.equals(greaterThanOrEqual, that.greaterThanOrEqual) &&
            Objects.equals(lessThanOrEqual, that.lessThanOrEqual);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), greaterThan, lessThan, greaterThanOrEqual, lessThanOrEqual);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getFilterName() + " ["
            + (getGreaterThan() != null ? "greaterThan=" + getGreaterThan() + ", " : "")
            + (getGreaterThanOrEqual() != null ? "greaterThanOrEqual=" + getGreaterThanOrEqual() + ", " : "")
            + (getLessThan() != null ? "lessThan=" + getLessThan() + ", " : "")
            + (getLessThanOrEqual() != null ? "lessThanOrEqual=" + getLessThanOrEqual() + ", " : "")
            + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
            + (getSpecified() != null ? "specified=" + getSpecified() + ", " : "")
            + (getIn() != null ? "in=" + getIn() : "")
            + "]";
    }

}