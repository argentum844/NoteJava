package mapper;

import dto.Users.Controller.*;
import dto.Users.View.*;
import entity.Users;
import mapper.converter.ConvertString;

import java.util.function.BiFunction;

public class UserMapper {
    public static UsersControllerDTO map(UsersViewDTO user) {
        return UsersControllerDTO.builder()
                .idUser(user.idUser())
                .login(user.login())
                .userName(user.userName())
                .userSurname(user.userSurname())
                .isAdmin(ConvertString.toBoolean(user.isAdmin()))
                .birthdayDate(ConvertString.toDate(user.birthdayDate()))
                .build();
    }

    public static UsersViewDTO map(UsersControllerDTO user) {
        return UsersViewDTO.builder()
                .idUser(user.idUser())
                .login(user.login())
                .userName(user.userName())
                .userSurname(user.userSurname())
                .isAdmin(String.valueOf(user.isAdmin()))
                .birthdayDate(ConvertString.dateToString(user.birthdayDate()))
                .build();
    }

    public static UsersControllerDTO map(Users user) {
        return UsersControllerDTO.builder()
                .idUser(user.getIdUser())
                .login(user.getLogin())
                .userName(user.getUserName())
                .userSurname(user.getUserSurname())
                .isAdmin(user.getIsAdmin())
                .birthdayDate(user.getBirthdayDate())
                .build();
    }

    public static Users map(UserInsertControllerDTO dto, String salt, BiFunction<String, String, String> function) {
        return new Users(dto.login(), function.apply(dto.userPassword(), salt), dto.userName(), dto.userSurname(),
                dto.isAdmin(), dto.birthdayDate(), salt);
    }

    public static UserInsertViewDTO map(UserInsertControllerDTO dto){
        return UserInsertViewDTO.builder()
                .login(dto.login())
                .userPassword(dto.userPassword())
                .userName(dto.userName())
                .userSurname(dto.userSurname())
                .birthdayDate(ConvertString.dateToString(dto.birthdayDate()))
                .isAdmin(String.valueOf(dto.isAdmin()))
                .build();
    }

    public static Users map(UserLogoPassControllerDTO dto, String salt, BiFunction<String, String, String> function) {
        Users res = new Users();
        res.setLogin(dto.login());
        res.setUserPassword(function.apply(dto.newPass(), salt));
        res.setSalt(salt);
        return res;
    }

    public static Users map(UserUpdateDescriptionControllerDTO dto) {
        return new Users(dto.idUser(), dto.login(), dto.userName(), dto.userSurname(), dto.isAdmin(), dto.birthdayDate());
    }
    public static UserUpdateDescriptionControllerDTO map(UserUpdateDescriptionViewDTO dto){
        return UserUpdateDescriptionControllerDTO.builder()
                .idUser(dto.idUser())
                .login(dto.login())
                .userName(dto.userName())
                .userSurname(dto.userSurname())
                .isAdmin(ConvertString.toBoolean(dto.isAdmin()))
                .birthdayDate(ConvertString.toDate(dto.birthdayDate()))
                .build();
    }

    public static UsersControllerDTO map(UserConstFieldsControllerDTO constFields, UserUpdateDescriptionControllerDTO dto) {
        return UsersControllerDTO.builder()
                .idUser(constFields.idUser())
                .login(dto.login())
                .userName(dto.userName())
                .userSurname(dto.userSurname())
                .isAdmin(constFields.isAdmin())
                .birthdayDate(dto.birthdayDate())
                .build();
    }

    public static UserLogoPassControllerDTO map(UserLogoPassViewDTO dto){
        return UserLogoPassControllerDTO.builder()
                .login(dto.login())
                .oldPass(dto.oldPass())
                .newPass(dto.newPass())
                .build();
    }

    public static UserInsertControllerDTO map(UserInsertViewDTO dto){
        return UserInsertControllerDTO.builder()
                .login(dto.login())
                .userName(dto.userName())
                .userSurname(dto.userSurname())
                .userPassword(dto.userPassword())
                .isAdmin(ConvertString.toBoolean(dto.isAdmin()))
                .birthdayDate(ConvertString.toDate(dto.birthdayDate()))
                .build();
    }

    public static UserPasswordAndSaltControllerDTO mapToPSController(Users user) {
        return UserPasswordAndSaltControllerDTO.builder()
                .userPassword(user.getUserPassword())
                .salt(user.getSalt())
                .build();
    }

    public static UserForAdminControllerDTO mapToAdminController(Users user) {
        return UserForAdminControllerDTO.builder()
                .idUser(user.getIdUser())
                .login(user.getLogin())
                .userName(user.getUserName())
                .userSurname(user.getUserSurname())
                .birthdayDate(user.getBirthdayDate())
                .build();
    }

    public static UserLoginControllerDTO map(UserLoginViewDTO userLoginViewDto) {
        return UserLoginControllerDTO.builder()
                .login(userLoginViewDto.login())
                .password(userLoginViewDto.password())
                .build();
    }
    public static UserForAdminViewDTO map(UserForAdminControllerDTO dto){
        return UserForAdminViewDTO.builder()
                .login(dto.login())
                .idUser(dto.idUser())
                .birthdayDate(ConvertString.dateToString(dto.birthdayDate()))
                .userName(dto.userName())
                .userSurname(dto.userSurname())
                .build();
    }
}
