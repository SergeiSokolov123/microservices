package utils;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.AuditDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.dto.RegistrationDto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class DtoTestData {
    public static AccountDetailsIdDto getAccountDetailsIdDto() {
        return new AccountDetailsIdDto(
                null,
                1L,
                getProfileDto()
        );
    }

    public static ProfileDto getProfileDto() {
        return new ProfileDto(
                null,
                9281234567L,
                "petrov@mail.ru",
                "IVAN PETROV",
                123456789012L,
                12345678901L,
                getPassportDto(),
                getActualRegistrationDto()
        );
    }

    public static PassportDto getPassportDto() {
        return new PassportDto(
                null,
                2505,
                123456L,
                "Петров",
                "Иван",
                "Сидорович",
                "МУЖ",
                LocalDate.of(1990, 1, 1),
                "Москва",
                "ОВД",
                LocalDate.of(2010, 2, 1),
                1,
                LocalDate.of(2028, 2, 1),
                getRegistrationDto()
        );
    }

    public static RegistrationDto getRegistrationDto() {
        return new RegistrationDto(
                null,
                "Россия",
                "Саратовская обл.",
                "Саратов",
                "Ленинский",
                "-",
                "Ленина",
                "100",
                "1",
                "25",
                234567L
        );
    }

    public static ActualRegistrationDto getActualRegistrationDto() {
        return new ActualRegistrationDto(
                null,
                "Россия",
                "Москва",
                "Москва",
                "Хамовники",
                "-",
                "Центральная",
                "5",
                "1",
                "34",
                123456L
        );
    }

    public static AuditDto getAuditDto() {
        return new AuditDto(
                1L,
                "Passport",
                "update",
                "user1",
                "user2",
                Timestamp.valueOf("2023-06-12 12:30:55.888"),
                Timestamp.valueOf("2023-07-12 12:30:55.888"),
                "{}",
                "{}");
    }

    public static List<AccountDetailsIdDto> getListOfAccountDetailsIdDto() {
        return List.of(getAccountDetailsIdDto(),
                new AccountDetailsIdDto(
                        2L,
                        2L,
                        new ProfileDto()
                ));
    }

    public static List<ActualRegistrationDto> getListOfActualRegistrationDto() {
        return List.of(getActualRegistrationDto(),
                new ActualRegistrationDto(
                        2L,
                        "Россия",
                        "Московская область",
                        "Пушкино",
                        "Центральный",
                        "-",
                        "Московская",
                        "5",
                        "1",
                        "34",
                        456789L
                ));
    }

    public static List<PassportDto> getListOfPassportDto() {
        return List.of(getPassportDto(),
                new PassportDto(
                        2L,
                        2605,
                        456789L,
                        "Петрова",
                        "Марина",
                        "Николаевна",
                        "ЖЕН",
                        LocalDate.of(1992, 1, 1),
                        "Москва",
                        "ОВД",
                        LocalDate.of(2012, 2, 1),
                        1,
                        LocalDate.of(2030, 2, 1),
                        new RegistrationDto()
                ));
    }

    public static List<ProfileDto> getListOfProfileDto() {
        return List.of(getProfileDto(),
                new ProfileDto(
                        2L,
                        9151234567L,
                        "petrova@mail.ru",
                        "MARINA PETROVA",
                        341256789012L,
                        34125678901L,
                        new PassportDto(),
                        new ActualRegistrationDto()
                ));
    }

    public static List<RegistrationDto> getListOfRegistrationDto() {
        return List.of(getRegistrationDto(),
                new RegistrationDto(
                        2L,
                        "Россия",
                        "Московская область",
                        "Пушкино",
                        "Центральный",
                        "-",
                        "Московская",
                        "5",
                        "1",
                        "34",
                        456789L
                ));
    }
}
