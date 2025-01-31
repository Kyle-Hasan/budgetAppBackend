package com.kyle.budgetAppBackend.base;

import com.kyle.budgetAppBackend.notifications.NotificationController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity> {


    protected final NotificationController notificationController;

    protected BaseRepository<T> baseRepository;

    public BaseService(BaseRepository<T> baseRepository, NotificationController notificationController) {
        this.baseRepository = baseRepository;
        this.notificationController = notificationController;
    }


    public T create(T t, String entityType) {
        T retVal = baseRepository.save(t);
        notificationController.invalidateCache(entityType, true);
        return retVal;
    }

    @PreAuthorize("this.checkAuthorizationById(#t.getId())")
    public Optional<T> updateOverwrite(T t) {


        if (baseRepository.existsById(t.getId())) {
            return Optional.of(baseRepository.save(t));
        }
        return Optional.empty();
    }

    @PreAuthorize("this.checkAuthorization(#oldT)")
    public T updateFields(T t, T oldT, String entityType) {
        Field[] fields = oldT.getClass().getDeclaredFields();
        t.setCreatedBy(oldT.getCreatedBy());
        t.setCreatedAt(oldT.getCreatedAt());
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(t);

                field.set(oldT, value);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }

        this.notificationController.invalidateCache(entityType, true);
        return this.baseRepository.save(oldT);
    }


    @PreAuthorize("this.checkAuthorizationById(#id)")
    public Optional<T> get(Long id) {

        return baseRepository.findById(id);
    }

    public List<T> getAll() {
        return baseRepository.findAll();
    }

    @PreAuthorize("this.checkAuthorizationById(#id)")
    public void delete(Long id, String entityType) {

        baseRepository.deleteById(id);
        this.notificationController.invalidateCache(entityType, true);
    }

    public boolean checkAuthorization(T entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        var creator = entity.getCreatedBy();
        if (creator == null) {
            return false;
        } else {
            return creator.getUsername().equals(username);
        }

    }

    // check if user is allowed to edit this entity
    public boolean checkAuthorizationById(Long id) {
        Optional<T> optionalT = baseRepository.findById(id);
        if (optionalT.isEmpty()) {
            return false;
        }
        return (checkAuthorization(optionalT.get()));
    }

    public boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
