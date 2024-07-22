package bg.magna.machines.service.impl;

import bg.magna.machines.model.dto.AddMachineDTO;
import bg.magna.machines.model.dto.FullMachineDTO;
import bg.magna.machines.model.dto.ShortMachineDTO;
import bg.magna.machines.model.entity.Machine;
import bg.magna.machines.repository.MachineRepository;
import bg.magna.machines.util.exceptions.MachineNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MachineServiceImplTest {
    private MachineServiceImpl toTest;
    private static final Machine TEST_MACHINE_1 = new Machine("UUID1", "serial1", "machine1", "image1URL", 2021, "brandName1", "descriptionEn1", "descriptionBg1", 1.0, 1, 111, "moreInfoEn1", "moreInfoBg1");
    private static final Machine TEST_MACHINE_2 = new Machine("UUID2", "serial2", "machine2", "image2URL", 2022, "brandName2", "descriptionEn2", "descriptionBg2", 2.0, 2, 222, "moreInfoEn2", "moreInfoBg2");
    private static final ShortMachineDTO TEST_SHORT_DTO_1 = new ShortMachineDTO("UUID1", "machine1", "image1URL", 2021, "brandName1", "descriptionEn1", "descriptionBg1");
    private static final ShortMachineDTO TEST_SHORT_DTO_2 = new ShortMachineDTO("UUID2", "machine2", "image2URL", 2022, "brandName2", "descriptionEn2", "descriptionBg2");
    private static final FullMachineDTO TEST_FULL_DTO_1 = new FullMachineDTO("UUID1", "serial1", "machine1", "image1URL", 2021, "brandName1", "descriptionEn1", "descriptionBg1", 1.0, 1, 111, "moreInfoEn1", "moreInfoBg1");
    private static final AddMachineDTO TEST_ADD_DTO_1 = new AddMachineDTO("serial1", "machine1", "image1URL", 2021, "brandName1", "descriptionEn1", "descriptionBg1", 1.0, 1, 111, "moreInfoEn1", "moreInfoBg1");

    @Mock
    private MachineRepository machineRepository;

    @Captor
    private ArgumentCaptor<Machine> machineCaptor;

    @BeforeEach
    public void setUp() {
        this.toTest = new MachineServiceImpl(machineRepository, new ModelMapper());
    }

    @Test
    void testGetAllMachines() {
        List<ShortMachineDTO> expected = List.of(TEST_SHORT_DTO_1, TEST_SHORT_DTO_2);
        when(machineRepository.findAll()).thenReturn(List.of(TEST_MACHINE_1, TEST_MACHINE_2));

        List<ShortMachineDTO> actual = toTest.getAllMachines();

        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0).getId(), actual.get(0).getId());
        Assertions.assertEquals(expected.get(0).getName(), actual.get(0).getName());
        Assertions.assertEquals(expected.get(0).getBrandName(), actual.get(0).getBrandName());
        Assertions.assertEquals(expected.get(0).getYear(), actual.get(0).getYear());
        Assertions.assertEquals(expected.get(0).getDescriptionEn(), actual.get(0).getDescriptionEn());
        Assertions.assertEquals(expected.get(0).getDescriptionBg(), actual.get(0).getDescriptionBg());
        Assertions.assertEquals(expected.get(1).getId(), actual.get(1).getId());
        Assertions.assertEquals(expected.get(1).getName(), actual.get(1).getName());
        Assertions.assertEquals(expected.get(1).getBrandName(), actual.get(1).getBrandName());
        Assertions.assertEquals(expected.get(1).getYear(), actual.get(1).getYear());
        Assertions.assertEquals(expected.get(1).getDescriptionEn(), actual.get(1).getDescriptionEn());
        Assertions.assertEquals(expected.get(1).getDescriptionBg(), actual.get(1).getDescriptionBg());
    }

    @Test
    void testGetAllMachines_ReturnsZeroResults() {
        when(machineRepository.findAll()).thenReturn(List.of());

        List<ShortMachineDTO> actual = toTest.getAllMachines();

        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void testGetById() {
        when(machineRepository.findById("UUID1")).thenReturn(Optional.of(TEST_MACHINE_1));

        FullMachineDTO expected = TEST_FULL_DTO_1;
        FullMachineDTO actual = toTest.getById("UUID1");

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getBrandName(), actual.getBrandName());
        Assertions.assertEquals(expected.getYear(), actual.getYear());
        Assertions.assertEquals(expected.getDescriptionEn(), actual.getDescriptionEn());
        Assertions.assertEquals(expected.getDescriptionBg(), actual.getDescriptionBg());
        Assertions.assertEquals(expected.getYear(), actual.getYear());
        Assertions.assertEquals(expected.getWorkingWidth(), actual.getWorkingWidth());
        Assertions.assertEquals(expected.getWeight(), actual.getWeight());
        Assertions.assertEquals(expected.getMoreInfoEn(), actual.getMoreInfoEn());
        Assertions.assertEquals(expected.getMoreInfoBg(), actual.getMoreInfoBg());
    }

    @Test
    void testGetById_throwsExceptionWhenMachineNotFound() {
        when(machineRepository.findById("UUID1")).thenReturn(Optional.empty());

        Assertions.assertThrows(MachineNotFoundException.class, () -> toTest.getById("UUID1"));
    }

    @Test
    void testAdd_test() {
        Machine expected = TEST_MACHINE_1;

        toTest.add(TEST_ADD_DTO_1);
        verify(machineRepository).saveAndFlush(machineCaptor.capture());
        Machine actual = machineCaptor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getBrandName(), actual.getBrandName());
        Assertions.assertEquals(expected.getYear(), actual.getYear());
        Assertions.assertEquals(expected.getDescriptionEn(), actual.getDescriptionEn());
        Assertions.assertEquals(expected.getDescriptionBg(), actual.getDescriptionBg());
        Assertions.assertEquals(expected.getYear(), actual.getYear());
        Assertions.assertEquals(expected.getWorkingWidth(), actual.getWorkingWidth());
        Assertions.assertEquals(expected.getWeight(), actual.getWeight());
        Assertions.assertEquals(expected.getMoreInfoEn(), actual.getMoreInfoEn());
        Assertions.assertEquals(expected.getMoreInfoBg(), actual.getMoreInfoBg());
    }

    @Test
    void testEditById() {
        when(machineRepository.findById("UUID2")).thenReturn(Optional.of(TEST_MACHINE_2));

        String expectedId = TEST_MACHINE_2.getId();
        Machine expectedDetails = TEST_MACHINE_1;

        toTest.editById("UUID2", TEST_FULL_DTO_1);
        verify(machineRepository).saveAndFlush(machineCaptor.capture());
        Machine actual = machineCaptor.getValue();

        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expectedId, actual.getId());
        Assertions.assertEquals(expectedDetails.getName(), actual.getName());
        Assertions.assertEquals(expectedDetails.getBrandName(), actual.getBrandName());
        Assertions.assertEquals(expectedDetails.getYear(), actual.getYear());
        Assertions.assertEquals(expectedDetails.getDescriptionEn(), actual.getDescriptionEn());
        Assertions.assertEquals(expectedDetails.getDescriptionBg(), actual.getDescriptionBg());
        Assertions.assertEquals(expectedDetails.getYear(), actual.getYear());
        Assertions.assertEquals(expectedDetails.getWorkingWidth(), actual.getWorkingWidth());
        Assertions.assertEquals(expectedDetails.getWeight(), actual.getWeight());
        Assertions.assertEquals(expectedDetails.getMoreInfoEn(), actual.getMoreInfoEn());
        Assertions.assertEquals(expectedDetails.getMoreInfoBg(), actual.getMoreInfoBg());
    }

    @Test
    void testEditById_throwsExceptionWhenMachineNotFound() {
        when(machineRepository.findById("UUID2")).thenReturn(Optional.empty());

        Assertions.assertThrows(MachineNotFoundException.class, () -> toTest.editById("UUID2", TEST_FULL_DTO_1));
    }
}
