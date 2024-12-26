package com.hit.jpa;

import com.hit.jpa.exception.DBException;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
public abstract class BaseJPAAdapter<T, ID, R extends BaseJPARepository<T, ID>> {

    @Setter(onMethod_={@Autowired(required = false)})
    protected R repository;

    protected Field fieldID;

    @PostConstruct
    private void init() {
        this.fieldID = Arrays.stream(this.getEntityClass().getDeclaredFields())
                .filter(field -> field.getName().equalsIgnoreCase("id"))
                .findFirst()
                .orElse(null);
    }

    protected abstract Class<T> getEntityClass();

    @SuppressWarnings({"unchecked"})
    private ID getEntityId(T entity) {
        try {
            if (!fieldID.canAccess(entity)) {
                fieldID.setAccessible(true);
            }
            return (ID) fieldID.get(entity);
        } catch (Exception e) {
            throw new DBException("getEntityId error", e);
        }
    }

    public List<ID> getAllId() {
        return this.repository.findAll().stream().map(this::getEntityId).toList();
    }

    public List<T> getAll() {
        return this.repository.findAll();
    }

    public List<T> getAll(Specification<T> specification) {
        return this.repository.findAll(specification);
    }

    public Page<T> getAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    public List<T> getAll(Sort sort) {
        return this.repository.findAll(sort);
    }

    public List<T> getAllInIds(Collection<ID> ids) {
        return this.repository.findAllById(ids);
    }

    public Map<ID, T> getMapId(Collection<ID> ids) {
        List<T> entities = this.repository.findAllById(ids);
        return entities.stream().collect(Collectors.toMap(this::getEntityId, Function.identity()));
    }

    public T get(ID id) {
        return this.repository.findById(id).orElse((T) null);
    }

    public boolean exists(ID id) {
        return this.repository.existsById(id);
    }

    public T save(T entity) {
        return this.repository.save(entity);
    }

    public List<T> saveAll(Collection<T> entity) {
        return this.repository.saveAll(entity);
    }

    public T saveAndFlush(T entity) {
        return this.repository.saveAndFlush(entity);
    }

    public List<T> saveAllAndFlush(Collection<T> entity) {
        List<T> temp = this.repository.saveAll(entity);
        this.repository.flush();
        return temp;
    }

    public T update(T entity) {
        return this.repository.save(entity);
    }

    public void delete(ID id) {
        this.repository.deleteById(id);
    }

}
