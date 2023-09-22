package com.swp.model.criteria.filter;

import java.util.Objects;

public class StringFilter extends Filter<String> {

    private static final long serialVersionUID = 1L;

    private String contains;
    private String doesNotContain;

    /**
     * <p>Constructor for StringFilter.</p>
     */
    public StringFilter() {
    }

    /**
     * <p>Constructor for StringFilter.</p>
     *
     * @param filter a {@link io.github.jhipster.service.filter.StringFilter} object.
     */
    public StringFilter(final StringFilter filter) {
        super(filter);
        this.contains = filter.contains;
        this.doesNotContain = filter.doesNotContain;
    }

    /**
     * <p>copy.</p>
     *
     * @return a {@link io.github.jhipster.service.filter.StringFilter} object.
     */
    public StringFilter copy() {
        return new StringFilter(this);
    }

    /**
     * <p>Getter for the field <code>doesNotContain</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getDoesNotContain() {
        return doesNotContain;
    }

    /**
     * <p>Setter for the field <code>doesNotContain</code>.</p>
     *
     * @param doesNotContain a {@link String} object.
     * @return a {@link io.github.jhipster.service.filter.StringFilter} object.
     */
    public StringFilter setDoesNotContain(String doesNotContain) {
        this.doesNotContain = doesNotContain;
        return this;
    }

    /**
     * <p>Getter for the field <code>contains</code>.</p>
     *
     * @return a {@link String} object.
     */
    public String getContains() {
        return contains;
    }

    /**
     * <p>Setter for the field <code>contains</code>.</p>
     *
     * @param contains a {@link String} object.
     * @return a {@link io.github.jhipster.service.filter.StringFilter} object.
     */
    public StringFilter setContains(String contains) {
        this.contains = contains;
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
        final StringFilter that = (StringFilter) o;
        return Objects.equals(contains, that.contains) &&
            Objects.equals(doesNotContain, that.doesNotContain);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contains, doesNotContain);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getFilterName() + " ["
            + (getContains() != null ? "contains=" + getContains() + ", " : "")
            + (getDoesNotContain() != null ? "doesNotContain=" + getDoesNotContain() + ", " : "")
            + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
            + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
            + (getSpecified() != null ? "specified=" + getSpecified() : "")
            + "]";
    }

}