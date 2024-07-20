package bg.magna.machines.repository;

import bg.magna.machines.model.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MachineRepository extends JpaRepository<Machine, String> {
    Optional<Machine> findBySerialNumber(String serialNumber);

    Boolean existsBySerialNumber(String serialNumber);
}
