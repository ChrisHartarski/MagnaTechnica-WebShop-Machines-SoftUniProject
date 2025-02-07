package bg.magna.machines.service.impl;

import bg.magna.machines.model.dto.AddMachineDTO;
import bg.magna.machines.model.dto.FullMachineDTO;
import bg.magna.machines.model.dto.ShortMachineDTO;
import bg.magna.machines.model.entity.Machine;
import bg.magna.machines.repository.MachineRepository;
import bg.magna.machines.service.MachineService;
import bg.magna.machines.util.exceptions.MachineNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

@Service
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;
    private final ModelMapper modelMapper;

    public MachineServiceImpl(MachineRepository machineRepository, ModelMapper modelMapper) {
        this.machineRepository = machineRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PagedModel<ShortMachineDTO> getAllMachines(Pageable pageable) {
        return new PagedModel<>(machineRepository.findAll(pageable)
                .map(m -> modelMapper.map(m, ShortMachineDTO.class)));
    }

    @Override
    public FullMachineDTO getById(String id) {
        return machineRepository.findById(id)
                .map(m -> modelMapper.map(m, FullMachineDTO.class))
                .orElseThrow(() -> new MachineNotFoundException("No such machine found"));
    }

    @Override
    public Boolean machineExists(String serialNumber) {
        return machineRepository.existsBySerialNumber(serialNumber);
    }

    @Override
    public FullMachineDTO add(AddMachineDTO addMachineDTO) {
        Machine machine = modelMapper.map(addMachineDTO, Machine.class);
        machineRepository.saveAndFlush(machine);

        return modelMapper.map(machine, FullMachineDTO.class);
    }

    @Override
    public boolean deleteById(String id) {
        machineRepository.deleteById(id);
        return !machineRepository.existsById(id);
    }

    @Override
    public FullMachineDTO editById(String id, FullMachineDTO fullMachineDTO) {
        Machine machine = machineRepository.findById(id).orElseThrow(() -> new MachineNotFoundException("No such machine found"));
        machine.setSerialNumber(fullMachineDTO.getSerialNumber());
        machine.setName(fullMachineDTO.getName());
        machine.setImageURL(fullMachineDTO.getImageURL());
        machine.setYear(fullMachineDTO.getYear());
        machine.setBrandName(fullMachineDTO.getBrandName());
        machine.setDescriptionEn(fullMachineDTO.getDescriptionEn());
        machine.setDescriptionBg(fullMachineDTO.getDescriptionBg());
        machine.setWorkingWidth(fullMachineDTO.getWorkingWidth());
        machine.setWeight(fullMachineDTO.getWeight());
        machine.setRequiredPower(fullMachineDTO.getRequiredPower());
        machine.setMoreInfoEn(fullMachineDTO.getMoreInfoEn());
        machine.setMoreInfoBg(fullMachineDTO.getMoreInfoBg());

        machineRepository.saveAndFlush(machine);
        Machine editedMachine = machineRepository.findById(machine.getId()).orElseThrow(() -> new MachineNotFoundException("No such machine found"));
        return modelMapper.map(editedMachine, FullMachineDTO.class);
    }

    @Override
    public Boolean repositoryEmpty() {
        return machineRepository.count() == 0;
    }
}
