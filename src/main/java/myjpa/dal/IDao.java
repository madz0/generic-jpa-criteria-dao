package myjpa.dal;

import java.util.List;

public interface IDao {

	<T> List<T> findAll();
    <T> List<T> find(Class<T> entityClass, PredicateBuilderExpression<T> pb);
    <T> List<T> find(Class<T> entityClass, CriteriaQueryBuilderExpression<T, T> pb);
    <T, X> List<X> find(Class<T> entityClass, Class<X> out, CriteriaQueryBuilderExpression<T, X> pb);
    <T> boolean add(T entity) throws Exception;
    <T> boolean update(T entity) throws Exception;
    <T> boolean delete(T entity) throws Exception;
    
    void close();
}
