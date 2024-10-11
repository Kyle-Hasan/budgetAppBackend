package com.kyle.budgetAppBackend.base;

public abstract class BaseController<T extends BaseEntity> {
    private BaseService<T> baseService;


    public BaseController(BaseService<T> baseService) {
        this.baseService = baseService;
    }


}
