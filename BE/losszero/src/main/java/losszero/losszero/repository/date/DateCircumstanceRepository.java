package losszero.losszero.repository.date;

import losszero.losszero.entity.date.DateCircumstance;
import losszero.losszero.entity.date.DateProd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DateCircumstanceRepository extends JpaRepository<DateCircumstance, Integer> {

    @Query("SELECT d FROM DateCircumstance d WHERE d.lineId = :lineId AND d.date BETWEEN :startDate AND :endDate")
    List<DateCircumstance> findByLineIdAndDateBetween(
            @Param("lineId") int lineId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT d FROM DateCircumstance d WHERE d.lineId = :lineId ORDER BY d.date DESC")
    List<DateCircumstance> findTop7ByLineIdOrderByDateDesc(@Param("lineId") int lineId);

    @Query("SELECT d FROM DateCircumstance d WHERE d.lineId = :lineId AND d.date = :date")
    Optional<DateCircumstance> findByLineIdAndDate(@Param("lineId") int lineId, @Param("date") LocalDate date);
}
