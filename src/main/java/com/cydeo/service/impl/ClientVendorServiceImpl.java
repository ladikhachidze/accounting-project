package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.dto.CompanyDTO;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {
    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository,
                                   MapperUtil mapperUtil, CompanyService companyService) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
    }

    @Override
    public ClientVendorDTO findById(Long id) {
        return mapperUtil.convert(clientVendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientVendor couldn't find.")), new ClientVendorDTO());
    }

    @Override
    public List<ClientVendorDTO> getListOfClientVendors() {
        return clientVendorRepository.findAll()
                .stream()
                .map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ClientVendorDTO createClientVendor(ClientVendorDTO clientVendorDTO) {
        CompanyDTO companyDTO =companyService.getCompanyDTOByLoggedInUser();
        clientVendorDTO.setCompanyDTO(companyDTO);
        ClientVendor clientVendor = clientVendorRepository.save(mapperUtil.convert(clientVendorDTO, new ClientVendor()));
        return mapperUtil.convert(clientVendor, new ClientVendorDTO());
    }

    @Override
    public ClientVendorDTO updateClientVendor(Long id, ClientVendorDTO clientVendorDTO) {
        ClientVendor clientVendor = clientVendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClientVendor couldn't find"));
        ClientVendor convertedClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());
        convertedClientVendor.setId(clientVendor.getId());
        convertedClientVendor.setCompany(clientVendor.getCompany());
        clientVendorRepository.save(convertedClientVendor);
        return mapperUtil.convert(convertedClientVendor, new ClientVendorDTO());
    }
    //soft delete
    @Override
    public void delete(Long id) {

        ClientVendor clientVendor = clientVendorRepository.findByIdAndIsDeleted(id, false);
        clientVendor.setIsDeleted(true);
        clientVendorRepository.save(clientVendor);

    }

}