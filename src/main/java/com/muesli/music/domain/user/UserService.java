package com.muesli.music.domain.user;

public interface UserService {
    UserInfo.Main registerUser(UserCommand.RegisterUserRequest command);

    UserInfo.Main findUserInfo(String email);

    UserInfo.UsertokenInfo loginUser(UserCommand.LoginUserRequest command);

    void changeConfirmed(String email);

    void registerUserUuid(String email);

    // 닉네임 중복 여부
    boolean isDuplicateUsername(String username);

    // 비밀번호 변경
    void changePassword(UserCommand.ChangeUserPassword command, String email);

    // 프로필 사진 업로드
    // 사용자 선호 취향 리스트


}
