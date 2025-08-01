package br.com.hadryan.api.mapper;

import br.com.hadryan.api.mapper.request.UserPostRequest;
import br.com.hadryan.api.mapper.response.UserResponse;
import br.com.hadryan.api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User requestToUser(UserPostRequest request);

    UserResponse userToResponse(User user);

}
