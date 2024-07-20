package bg.magna.machines.controller;

import bg.magna.machines.model.dto.AddMachineDTO;
import bg.magna.machines.model.dto.FullMachineDTO;
import bg.magna.machines.model.dto.ShortMachineDTO;
import bg.magna.machines.service.MachineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShortMachineDTO>> getAllMachines() {
        return ResponseEntity.ok(machineService.getAllMachines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullMachineDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(machineService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        machineService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FullMachineDTO> editById(@PathVariable String id, @RequestBody FullMachineDTO fullMachineDTO) {
        machineService.editById(id, fullMachineDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exist/{serialNumber}")
    public ResponseEntity<Boolean> isMachineExist(@PathVariable String serialNumber) {
        return ResponseEntity.ok(machineService.machineExists(serialNumber));
    }

    @PostMapping("/add")
    public ResponseEntity<FullMachineDTO> addMachine(@RequestBody AddMachineDTO addMachineDTO) {
        FullMachineDTO machine = machineService.add(addMachineDTO);
        return ResponseEntity.ok().build();
    }




}
