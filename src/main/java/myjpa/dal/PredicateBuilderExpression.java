package myjpa.dal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface PredicateBuilderExpression <T> {
	
    Predicate build(CriteriaBuilder criteriaBuilder, Root<T> root);
}