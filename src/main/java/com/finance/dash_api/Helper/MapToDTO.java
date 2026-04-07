package com.finance.dash_api.Helper;

import com.finance.dash_api.DTO.RecordDTO;
import com.finance.dash_api.DTO.reqUserDto;
import com.finance.dash_api.DTO.resUserDto;
import com.finance.dash_api.entity.User;
import com.finance.dash_api.entity.Record;
import org.springframework.stereotype.Component;

@Component
public class MapToDTO {

    public resUserDto toDTO(User user) {
        return new resUserDto(user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.isActive(),
                user.getCreationDate());
    }

    public User toUser(reqUserDto reqUserDto){
        return new User(reqUserDto.getName(),
                reqUserDto.getEmail(),
                reqUserDto.getPassword(),
                reqUserDto.getRole(),
                reqUserDto.isActive());
    }

    public RecordDTO toRecordDTO(Record record) {
        return new RecordDTO(
                record.getId(),
                record.getAmount(),
                record.getType(),
                record.getCategory(),
                record.getCreationDate(),
                record.getNotes(),
                record.getUser().getId(),
                record.getUser().getName());
    }

    public Record toRecord(RecordDTO recordDTO, User user) {
        return new Record(recordDTO.getAmount(),
                recordDTO.getType(),
                recordDTO.getCategory(),
                recordDTO.getNotes(),
                user);
    }
}
