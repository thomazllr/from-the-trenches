package com.thomazllr.mapper;

import com.thomazllr.domain.User;
import com.thomazllr.request.UserPostRequest;
import com.thomazllr.request.UserPutRequest;
import com.thomazllr.response.UserGetResponse;
import com.thomazllr.response.UserPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(10, 1000))")
    User toEntity(UserPostRequest request);

    User toEntityFromUserPutRequest(UserPutRequest entity);

    List<UserGetResponse> toUserGetResponseList(List<User> userList);

    UserPostResponse toUserPostResponse(User entity);

    UserGetResponse toResponse(User entity);
}
