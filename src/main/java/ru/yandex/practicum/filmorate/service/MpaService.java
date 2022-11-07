package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDao;

import java.util.List;
import java.util.Optional;

@Service
public class  MpaService {

    private final MpaDao mpaDao;

    @Autowired
    public MpaService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }


    public Optional<Mpa> getById(int id) {
        return mpaDao.getById(id);
    }


    public List<Mpa> getAll() {
        return mpaDao.getAll();
    }
}