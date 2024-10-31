package losszero.losszero.repository.date;

import losszero.losszero.entity.date.DateProd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DateProdRepository extends JpaRepository<DateProd, Integer> {

    @Query("SELECT d FROM DateProd d WHERE d.lineId = :lineId AND d.date BETWEEN :startDate AND :endDate")
    List<DateProd> findByLineIdAndDateBetween(
            @Param("lineId") int lineId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT d FROM DateProd d WHERE d.lineId = :lineId ORDER BY d.date DESC")
    List<DateProd> findTop7ByLineIdOrderByDateDesc(@Param("lineId") int lineId);

    @Query("SELECT d FROM DateProd d WHERE d.lineId = :lineId AND d.date = :date")
    Optional<DateProd> findByLineIdAndDate(@Param("lineId") int lineId, @Param("date") LocalDate date);

}