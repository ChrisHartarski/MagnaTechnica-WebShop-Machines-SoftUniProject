package bg.magna.machines.service;

import bg.magna.machines.model.dto.AddMachineDTO;
import bg.magna.machines.model.dto.FullMachineDTO;
import bg.magna.machines.model.dto.ShortMachineDTO;

import java.util.List;

public interface MachineService {
    List<ShortMachineDTO> getAllMachines();

    FullMachineDTO getById(String id);

    Boolean machineExists(String serialNumber);

    FullMachineDTO add(AddMachineDTO addMachineDTO);

    boolean deleteById(String id);

    FullMachineDTO editById(String id, FullMachineDTO fullMachineDTO);

    Boolean repositoryEmpty();
}
