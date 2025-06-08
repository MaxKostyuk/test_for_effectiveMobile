package api.data.provider.dashboard;

import api.models.CreateDashboardBodyDTO;
import net.datafaker.Faker;

public class DashboardProvider {
    private static final Faker faker = new Faker();

    public static CreateDashboardBodyDTO singleDashboardToCreateProvider() {
        return new CreateDashboardBodyDTO(faker.app().name(), faker.lorem().sentence());
    }
}
