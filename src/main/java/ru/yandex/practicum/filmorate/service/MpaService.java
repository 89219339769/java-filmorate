package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaService {
    private final MpaDbStorage mpaDbStorage;



    public Mpa findMpaById(Long id) {
        Mpa mpa = mpaDbStorage.findMpaById(id);
        log.info("Найден MPA", id);
        return mpa;
    }

    public List<Mpa> findAllMpa() {
        List<Mpa> mpa = mpaDbStorage.findAllMpa();
        log.info("Все MPA найдены.");
        return mpa;
    }
}
