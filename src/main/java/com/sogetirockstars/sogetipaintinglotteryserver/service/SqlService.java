package com.sogetirockstars.sogetipaintinglotteryserver.service;

import java.util.List;

/**
 * Service
 */
public interface SqlService<T> {
    public List<T> getAllPaintings();
    public T getItem(Long id);
    public T save(T lotteryItem);
    public boolean delete(Long id);
    public T add(T item);
    public T update(T newItem);
    public boolean existsById(Long id);
}
