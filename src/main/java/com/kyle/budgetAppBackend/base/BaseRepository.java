package com.kyle.budgetAppBackend.base;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T,Long> {
}
