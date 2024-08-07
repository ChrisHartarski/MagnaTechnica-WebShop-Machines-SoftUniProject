package bg.magna.machines.controller;

import bg.magna.machines.model.dto.AddMachineDTO;
import bg.magna.machines.model.dto.FullMachineDTO;
import bg.magna.machines.model.dto.ShortMachineDTO;
import bg.magna.machines.service.MachineService;
import bg.magna.machines.util.exceptions.MachineNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @GetMapping("/all")
    public ResponseEntity<PagedModel<ShortMachineDTO>> getAllMachines(@PageableDefault(size = 6, sort = "createdOn", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(machineService.getAllMachines(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullMachineDTO> getMachine(@PathVariable String id) {
        return ResponseEntity.ok(machineService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable String id) {
        boolean result = machineService.deleteById(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FullMachineDTO> editById(@PathVariable String id, @RequestBody FullMachineDTO fullMachineDTO) {
        FullMachineDTO machineDTO = machineService.editById(id, fullMachineDTO);
        return ResponseEntity.ok().body(machineDTO);
    }

    @GetMapping("/exist/{serialNumber}")
    public ResponseEntity<Boolean> isMachineExist(@PathVariable String serialNumber) {
        boolean result = machineService.machineExists(serialNumber);

        if(result) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/repository/empty")
    public ResponseEntity<Boolean> isRepositoryEmpty() {
        boolean result = machineService.repositoryEmpty();

        if(result) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<FullMachineDTO> addMachine(@RequestBody AddMachineDTO addMachineDTO) {
        if (!machineService.machineExists(addMachineDTO.getSerialNumber())) {
            FullMachineDTO fullMachineDTO = machineService.add(addMachineDTO);
            return ResponseEntity.created(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(fullMachineDTO.getId())
                            .toUri()
            ).body(fullMachineDTO);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @ExceptionHandler(MachineNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(MachineNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
