package com.kyle.budgetAppBackend.base;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity> {

    private BaseRepository<T> baseRepository;

    public BaseService(BaseRepository<T> baseRepository) {
        this.baseRepository = baseRepository;
    }


    public T create(T t) {
        return baseRepository.save(t);
    }

    public Optional<T> update(T t) {
        if (baseRepository.existsById(t.getId())) {
            return Optional.of(baseRepository.save(t));
        }
        return Optional.empty();
    }



    public Optional<T> get(Long id) {
        return baseRepository.findById(id);
    }

    public List<T> getAll() {
        return baseRepository.findAll();
    }

    public void delete(Long id) {
        baseRepository.deleteById(id);
    }

}
