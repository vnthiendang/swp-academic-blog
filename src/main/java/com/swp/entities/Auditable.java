package com.swp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt", "createdBy", "updatedBy"},
    allowGetters = true
)
@Getter
@Setter
public abstract class Auditable<T> {


}
