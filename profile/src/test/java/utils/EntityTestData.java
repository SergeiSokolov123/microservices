package utils;

import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class EntityTestData {

    public static AccountDetailsIdEntity getAccountDetailsIdEntity() {
        return new AccountDetailsIdEntity(
                null,
                1L,
                getProfileEntity()
        );
    }

    public static ProfileEntity getProfileEntity() {
        return new ProfileEntity(
                null,
                9281234567L,
                "petrov@mail.ru",
                "IVAN PETROV",
                123456789012L,
                12345678901L,
                getPassportEntity(),
                getActualRegistrationEntity()
        );
    }

    public static PassportEntity getPassportEntity() {
        return new PassportEntity(
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
                getRegistrationEntity()
        );
    }

    public static RegistrationEntity getRegistrationEntity() {
        return new RegistrationEntity(
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

    public static ActualRegistrationEntity getActualRegistrationEntity() {
        return new ActualRegistrationEntity(
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

    public static AuditEntity getAuditEntity() {
        return new AuditEntity(
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

    public static List<AccountDetailsIdEntity> getListOfAccountDetailsIdEntity() {
        return List.of(getAccountDetailsIdEntity(),
                new AccountDetailsIdEntity(
                        2L,
                        2L,
                        new ProfileEntity()
                ));
    }

    public static List<ActualRegistrationEntity> getListOfActualRegistrationEntity() {
        return List.of(getActualRegistrationEntity(),
                new ActualRegistrationEntity(
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

    public static List<PassportEntity> getListOfPassportEntity() {
        return List.of(getPassportEntity(),
                new PassportEntity(
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
                        new RegistrationEntity()
                ));
    }

    public static List<ProfileEntity> getListOfProfileEntity() {
        return List.of(getProfileEntity(),
                new ProfileEntity(
                        2L,
                        9151234567L,
                        "petrova@mail.ru",
                        "MARINA PETROVA",
                        341256789012L,
                        34125678901L,
                        new PassportEntity(),
                        new ActualRegistrationEntity()
                ));
    }

    public static List<RegistrationEntity> getListOfRegistrationEntity() {
        return List.of(getRegistrationEntity(),
                new RegistrationEntity(
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
