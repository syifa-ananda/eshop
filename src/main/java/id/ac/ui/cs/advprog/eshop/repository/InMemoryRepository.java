package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public abstract class InMemoryRepository<T> implements BaseRepository<T, String> {
    protected List<T> data = new ArrayList<>();

    // These abstract methods allow subclasses to specify entity-specific behavior.
    protected abstract String getId(T entity);
    protected abstract void setId(T entity, String id);
    protected abstract boolean idMatches(T entity, String id);
    protected abstract void updateEntity(T existingEntity, T updatedEntity);

    @Override
    public T create(T entity) {
        if (getId(entity) == null) {
            setId(entity, UUID.randomUUID().toString());
        }
        data.add(entity);
        return entity;
    }

    @Override
    public Iterator<T> findAll() {
        return data.iterator();
    }

    @Override
    public T findById(String id) {
        for (T entity : data) {
            if (idMatches(entity, id)) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public T update(String id, T updatedEntity) {
        for (int i = 0; i < data.size(); i++) {
            T entity = data.get(i);
            if (idMatches(entity, id)) {
                updateEntity(entity, updatedEntity);
                return entity;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        data.removeIf(entity -> idMatches(entity, id));
    }
}
