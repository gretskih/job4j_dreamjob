package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Java-разработчик в ВитаСофт",
                LocalDateTime.of(2023, Month.SEPTEMBER, 1, 8, 30)));
        save(new Vacancy(0, "Junior Java Developer", "Java/kotlin разработчик (офис)",
                LocalDateTime.of(2023, Month.SEPTEMBER, 2, 9, 36)));
        save(new Vacancy(0, "Junior+ Java Developer", "Программист Java (Junior/Middle)",
                LocalDateTime.of(2023, Month.OCTOBER, 20, 8, 13)));
        save(new Vacancy(0, "Middle Java Developer", "Java Developer в Спортмастер",
                LocalDateTime.of(2023, Month.OCTOBER, 15, 11, 43)));
        save(new Vacancy(0, "Middle+ Java Developer", "Java Junior Developer в ООО Айкам",
                LocalDateTime.of(2023, Month.AUGUST, 3, 11, 43)));
        save(new Vacancy(0, "Senior Java Developer", "Разработчик Java - удаленка в Сбер",
                LocalDateTime.of(2023, Month.AUGUST, 29, 8, 28)));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(),
                vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}