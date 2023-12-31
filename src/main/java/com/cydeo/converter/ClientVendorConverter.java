package com.cydeo.converter;

import com.cydeo.dto.ClientVendorDTO;
import com.cydeo.service.ClientVendorService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientVendorConverter implements Converter<String, ClientVendorDTO> {

ClientVendorService clientVendorService;

    public ClientVendorConverter(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @Override
    public ClientVendorDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }

        return clientVendorService.findById(Long.parseLong(source));    }
}
