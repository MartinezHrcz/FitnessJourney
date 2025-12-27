package hu.hm.fitjourneyapi.mapper.social;

import hu.hm.fitjourneyapi.dto.social.message.CreateMessageDTO;
import hu.hm.fitjourneyapi.dto.social.message.MessageDTO;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.social.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    @Mapping(source = "sender", target = "senderId", qualifiedByName = "userToId")
    @Mapping(source = "recipient", target = "recipientId", qualifiedByName = "userToId")
    MessageDTO toDTO(Message message);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sender", expression = "java(sender)")
    @Mapping(target = "recipient", expression = "java(recipient)")
    Message toMessage(MessageDTO dto,User sender, User recipient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sender", expression = "java(sender)")
    @Mapping(target = "recipient", expression = "java(recipient)")
    Message toMessage(CreateMessageDTO dto, User sender, User recipient);

    List<MessageDTO> toDTO(List<Message> messages);

    @Named("userToId")
    static UUID userToId(User user) {
        return user != null ? user.getId() : null;
    }
}
