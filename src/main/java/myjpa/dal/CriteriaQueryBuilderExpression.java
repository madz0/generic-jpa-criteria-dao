package myjpa.dal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface CriteriaQueryBuilderExpression<T,X> {

	CriteriaQuery<X> build(CriteriaBuilder cb, Root<T> root, CriteriaQuery<X> q);
}
