package bg.magna.machines.service;

import bg.magna.machines.model.dto.FullMachineDTO;
import bg.magna.machines.model.dto.ShortMachineDTO;

import java.util.List;

public interface MachineService {
    List<ShortMachineDTO> getAllMachines();

    FullMachineDTO getById(String id);
}
