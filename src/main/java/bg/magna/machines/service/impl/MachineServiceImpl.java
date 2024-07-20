package bg.magna.machines.service.impl;

import bg.magna.machines.repository.MachineRepository;
import bg.magna.machines.service.MachineService;
import org.springframework.stereotype.Service;

@Service
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;

    public MachineServiceImpl(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }
}
