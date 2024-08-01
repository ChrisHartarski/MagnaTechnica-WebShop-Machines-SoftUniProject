package bg.magna.machines.controller;

import bg.magna.machines.model.dto.AddMachineDTO;
import bg.magna.machines.model.dto.FullMachineDTO;
import bg.magna.machines.model.dto.ShortMachineDTO;
import bg.magna.machines.model.entity.Machine;
import bg.magna.machines.repository.MachineRepository;
import bg.magna.machines.service.MachineService;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MachineControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private Gson gson;
    @Autowired
    private ModelMapper modelMapper;

    @AfterEach
    public void tearDown() {
        machineRepository.deleteAll();
    }

    @Test
    public void testGetAllMachines() throws Exception {
        Machine testMachine1 = createTestMachine("serial1", "machine1");
        Machine testMachine2 = createTestMachine("serial2", "machine2");
        machineRepository.saveAllAndFlush(List.of(testMachine1, testMachine2));

        PagedModel<ShortMachineDTO> result = new PagedModel<>(machineRepository.findAll(Pageable.ofSize(6))
                                            .map(m -> modelMapper.map(m, ShortMachineDTO.class)));

        String expected = gson.toJson(result);

        mockMvc.perform(get("/machines/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void testGetMachine() throws Exception {
        Machine testMachine1 = createTestMachine("serial1", "machine1");
        testMachine1 = machineRepository.saveAndFlush(testMachine1);

        FullMachineDTO machineDTO = modelMapper.map(testMachine1, FullMachineDTO.class);
        String expected = gson.toJson(machineDTO);

        mockMvc.perform(get("/machines/" + testMachine1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void testGetMachine_returnsNotFound() throws Exception {
        mockMvc.perform(get("/machines/" + "1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMachine() throws Exception {
        Assertions.assertEquals(0, machineRepository.count());
        Machine testMachine1 = createTestMachine("serial1", "machine1");
        testMachine1 = machineRepository.saveAndFlush(testMachine1);
        Assertions.assertEquals(1, machineRepository.count());

        mockMvc.perform(delete("/machines/" + testMachine1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));

        Assertions.assertEquals(0, machineRepository.count());
    }

    @Test
    public void testEditMachine() throws Exception {
        Machine testMachine1 = createTestMachine("serial1", "machine1");
        testMachine1 = machineRepository.saveAndFlush(testMachine1);

        FullMachineDTO editMachineDTO = modelMapper.map(testMachine1, FullMachineDTO.class);
        editMachineDTO.setDescriptionEn("newDescEn");
        editMachineDTO.setDescriptionBg("newDescBg");
        String input = gson.toJson(editMachineDTO);

        mockMvc.perform(put("/machines/edit/" + testMachine1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(testMachine1.getId())))
                .andExpect(jsonPath("$.descriptionEn", is("newDescEn")))
                .andExpect(jsonPath("$.descriptionBg", is("newDescBg")));
    }

    @Test
    public void testEditMachine_returnsNotFound() throws Exception {
        String input = gson.toJson(new FullMachineDTO());

        mockMvc.perform(put("/machines/edit/" + "1000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testIsMachineExist_returnsTrue() throws Exception {
        Assertions.assertEquals(0, machineRepository.count());
        Machine testMachine1 = createTestMachine("serial1", "machine1");
        testMachine1 = machineRepository.saveAndFlush(testMachine1);
        Assertions.assertEquals(1, machineRepository.count());

        mockMvc.perform(get("/machines/exist/" + testMachine1.getSerialNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void testIsMachineExist_returnsFalse() throws Exception {
        Assertions.assertEquals(0, machineRepository.count());

        mockMvc.perform(get("/machines/exist/" + "someSerialNumber"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    public void testIsRepositoryEmpty_returnsTrue() throws Exception {
        Assertions.assertEquals(0, machineRepository.count());

        mockMvc.perform(get("/machines/repository/empty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void testIsRepositoryEmpty_returnsFalse() throws Exception {
        Assertions.assertEquals(0, machineRepository.count());
        Machine testMachine1 = createTestMachine("serial1", "machine1");
        testMachine1 = machineRepository.saveAndFlush(testMachine1);
        Assertions.assertEquals(1, machineRepository.count());

        mockMvc.perform(get("/machines/repository/empty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    public void testAddMachine() throws Exception {
        Assertions.assertEquals(0, machineRepository.count());
        AddMachineDTO addMachineDTO = createAddMachineDTO("serial1", "machine1");
        String input = gson.toJson(addMachineDTO);
        FullMachineDTO expected = modelMapper.map(addMachineDTO, FullMachineDTO.class);

        mockMvc.perform(post("/machines/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(input))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.serialNumber", is(expected.getSerialNumber())))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.imageURL", is(expected.getImageURL())))
                .andExpect(jsonPath("$.year", is(expected.getYear())))
                .andExpect(jsonPath("$.brandName", is(expected.getBrandName())))
                .andExpect(jsonPath("$.descriptionBg", is(expected.getDescriptionBg())))
                .andExpect(jsonPath("$.descriptionEn", is(expected.getDescriptionEn())))
                .andExpect(jsonPath("$.workingWidth", is(expected.getWorkingWidth())))
                .andExpect(jsonPath("$.weight", is(expected.getWeight())))
                .andExpect(jsonPath("$.requiredPower", is(expected.getRequiredPower())))
                .andExpect(jsonPath("$.moreInfoEn", is(expected.getMoreInfoEn())))
                .andExpect(jsonPath("$.moreInfoBg", is(expected.getMoreInfoBg())));

        Assertions.assertEquals(1, machineRepository.count());
    }

    @Test
    public void testAddMachine_doesNotAddMachineIfSerialExists() throws Exception {
        Assertions.assertEquals(0, machineRepository.count());
        Machine testMachine1 = createTestMachine("serial1", "machine1");
        testMachine1 = machineRepository.saveAndFlush(testMachine1);
        Assertions.assertEquals(1, machineRepository.count());

        AddMachineDTO addMachineDTO = createAddMachineDTO("serial1", "machine1");
        String input = gson.toJson(addMachineDTO);

        mockMvc.perform(post("/machines/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isNoContent());

        Assertions.assertEquals(1, machineRepository.count());
    }

    private Machine createTestMachine(String serialNumber, String name) {
        Machine machine = new Machine(
                serialNumber,
                name,
                "https://someUrl.com",
                2020,
                "brandName",
                "descEn",
                "descBg",
                5,
                200,
                100,
                "moreInfoEn",
                "moreInfoBg"
        );
        machine.setCreatedOn(LocalDateTime.now());
        return machine;
    }

    private AddMachineDTO createAddMachineDTO(String serialNumber, String name) {
        return new AddMachineDTO(
                serialNumber,
                name,
                "https://someUrl.com",
                2020,
                "brandName",
                "descEn",
                "descBg",
                5,
                200,
                100,
                "moreInfoEn",
                "moreInfoBg"
        );
    }

}
