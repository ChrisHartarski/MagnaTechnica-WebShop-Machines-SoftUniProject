package bg.magna.machines.service.impl;

import bg.magna.machines.model.dto.FullMachineDTO;
import bg.magna.machines.model.dto.ShortMachineDTO;
import bg.magna.machines.repository.MachineRepository;
import bg.magna.machines.service.MachineService;
import bg.magna.machines.util.exceptions.MachineNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    public MachineServiceImpl(MachineRepository machineRepository, ModelMapper modelMapper) {
        this.machineRepository = machineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ShortMachineDTO> getAllMachines() {
        return machineRepository.findAll().stream()
                .map(m -> modelMapper.map(m, ShortMachineDTO.class))
                .toList();
    }

    @Override
    public FullMachineDTO getById(String id) {
        return machineRepository.findById(id)
                .map(m -> modelMapper.map(m, FullMachineDTO.class))
                .orElseThrow(() -> new MachineNotFoundException("No such machine found"));
    }
}
