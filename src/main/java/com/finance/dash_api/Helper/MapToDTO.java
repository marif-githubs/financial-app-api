package com.finance.dash_api.Helper;

import com.finance.dash_api.DTO.RecordDTO;
import com.finance.dash_api.DTO.UserDTO;
import com.finance.dash_api.entity.User;
import com.finance.dash_api.entity.Record;
import org.springframework.stereotype.Component;

@Component
public class MapToDTO {

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(),
                user.getName(),
                user.getEmail(),
                "********",
                user.getRole(),
                user.isActive(),
                user.getCreationDate());
    }

    public User toUser(UserDTO user){
        return new User(user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.isActive(),
                user.getCreationDate());
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
        return new Record(recordDTO.getId(),
                recordDTO.getAmount(),
                recordDTO.getType(),
                recordDTO.getCategory(),
                recordDTO.getCreationDate(),
                recordDTO.getNotes(),
                user);
    }
}
