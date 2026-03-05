package com.aspnt.mddl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import org.hamcrest.Matcher;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.aspnt.mddl.dsl.Given;
import ru.softmachine.odyssey.backend.test.matcher.Matchers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.argThat;

@AutoConfigureMockMvc(print = MockMvcPrint.DEFAULT)
@AutoConfigureWireMock(port = 0)
@SpringBootTest
@ActiveProfiles({"test", "postgres-test"})
public abstract class TestBase {

    @Autowired
    protected EasyRandom easyRandom;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected Given given;
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private List<CacheManager> cacheManagers;

    @Value("${wiremock.server.url}")
    protected String wireMockUrl;

    @BeforeEach
    public void setUp() {
        WireMock.reset();
        WireMock.resetAllRequests();
        cacheManagers.forEach(cm -> cm.getCacheNames().forEach(n -> cm.getCache(n).clear()));
    }

    @SneakyThrows
    protected String json(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    protected <T> T fromJson(String json, Class<T> type) {
        return objectMapper.readValue(json, type);
    }

    protected Map<?, ?> asMap(Object object) {
        return objectMapper.convertValue(object, Map.class);
    }

    protected <T> T as(Object object, Class<T> type) {
        return objectMapper.convertValue(object, type);
    }

    protected <T> T randomElement(List<T> list) {
        return list.get(easyRandom.nextInt(list.size()));
    }

    protected <T> T randomElement(T[] array) {
        return array[easyRandom.nextInt(array.length)];
    }

    protected <T> void verifyTableState(CrudRepository<T, ?> repository, Map<Matcher<? super T>, Matcher<? super T>> matchers) {
        var actualItems = Streamable.of(repository.findAll());
        matchers.keySet()
                .forEach(keyMatcher ->
                        actualItems.stream()
                                .filter(keyMatcher::matches)
                                .peek(i -> assertThat("Item " + i + " should match", i, matchers.get(keyMatcher)))
                                .findAny()
                                .orElseThrow(() -> new AssertionError("Item not found " + keyMatcher))
                );
    }

    protected static <T> T withId(String id) {
        return argThat(new HamcrestArgumentMatcher<>(Matchers.hasId(id)));
    }

    protected static String toString(OffsetDateTime dt) {
        return dt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
