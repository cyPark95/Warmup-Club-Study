package com.group.miniproject.util;

import com.group.miniproject.domain.team.entity.Team;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class TeamFixtureFactory {

    public static Team createTeam() {
        Predicate<Field> idPredicate = FieldPredicates.named("id")
                .and(FieldPredicates.ofType(Long.class))
                .and(FieldPredicates.inClass(Team.class));

        Predicate<Field> namePredicate = FieldPredicates.named("name")
                .and(FieldPredicates.ofType(String.class))
                .and(FieldPredicates.inClass(Team.class));

        EasyRandomParameters parameters = new EasyRandomParameters()
                .excludeField(idPredicate)
                .randomize(namePredicate, () -> new StringRandomizer(50).getRandomValue());

        return new EasyRandom(parameters)
                .nextObject(Team.class);
    }
}
