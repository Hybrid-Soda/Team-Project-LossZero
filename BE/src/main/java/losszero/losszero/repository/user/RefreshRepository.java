package losszero.losszero.repository.user;

import org.springframework.transaction.annotation.Transactional;
import losszero.losszero.entity.user.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {

    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
