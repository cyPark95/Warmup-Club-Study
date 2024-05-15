package com.group.miniproject.util;

import com.group.miniproject.domain.employee.dto.EmployeeRegisterRequest;
import com.group.miniproject.domain.employee.entity.Employee;
import com.group.miniproject.domain.employee.entity.EmployeeRole;
import com.group.miniproject.domain.team.entity.Team;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.text.StringRandomizer;
import org.jeasy.random.randomizers.time.LocalDateRandomizer;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class EmployeeFixtureFactory {

    public static Employee createEmployee(EmployeeRole role) {
        Predicate<Field> idPredicate = named("id")
                .and(ofType(Long.class))
                .and(inClass(Employee.class));

        Predicate<Field> teamPredicate = named("team")
                .and(ofType(Team.class))
                .and(inClass(Employee.class));

        Predicate<Field> namePredicate = named("name")
                .and(ofType(String.class))
                .and(inClass(Employee.class));

        Predicate<Field> rolePredicate = named("role")
                .and(ofType(EmployeeRole.class))
                .and(inClass(Employee.class));

        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(idPredicate)
                .excludeField(teamPredicate)
                .randomize(namePredicate, () -> new StringRandomizer(20).getRandomValue())
                .randomize(rolePredicate, () -> role);

        return new EasyRandom(parameters)
                .nextObject(Employee.class);
    }

    public static EmployeeRegisterRequest createEmployeeRegisterRequest(boolean isManager, String teamName) {
        return new EmployeeRegisterRequest(
                new StringRandomizer(20).getRandomValue(),
                teamName,
                isManager,
                new LocalDateRandomizer().getRandomValue(),
                new LocalDateRandomizer().getRandomValue()
        );
    }
}
