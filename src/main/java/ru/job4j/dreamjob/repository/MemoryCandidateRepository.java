package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Антонов Илья", "Java 17; Spring; PostgreSQL; HTML 5",
                LocalDateTime.of(2023, Month.OCTOBER, 1, 8, 30), 1, 0));
        save(new Candidate(0, "Клушин Федор", "Java 8; Spring MVC; Junit 5; CSS",
                LocalDateTime.of(2023, Month.OCTOBER, 4, 9, 30), 2, 0));
        save(new Candidate(0, "Путин Петр", "Java 18; Spring Framework; Multithreading; J2E",
                LocalDateTime.of(2023, Month.OCTOBER, 5, 8, 0), 3, 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.getAndIncrement());
        candidates.putIfAbsent(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> new Candidate(oldCandidate.getId(),
                candidate.getName(), candidate.getDescription(), candidate.getCreationDate(), candidate.getCityId(), candidate.getFileId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
