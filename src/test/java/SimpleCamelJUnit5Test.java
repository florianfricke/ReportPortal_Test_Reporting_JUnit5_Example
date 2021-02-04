import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleCamelJUnit5Test extends CamelTestSupport {
//    private static final Logger LOGGER = LogManager.getLogger(SimpleCamelJUnit5Test.class);

    private RouteBuilder getSimpleRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .process(exchange -> {
                            Thread.sleep(20000);
                        })
                        .to("mock:result");
            }
        };
    }

    @ParameterizedTest(name = "isBodyEqual")
    @ValueSource(strings = {"Hello World!", "Cat Party"})
    public void testExchangeBodyTest1(String body) throws Exception {
        MockEndpoint mockResult = getMockEndpoint("mock:result");
        context.addRoutes(getSimpleRoute());
//        LOGGER.info("TEST WIRD AUSGEFÜHRT!");
        template.sendBody("direct:start", body);
        mockResult.expectedMessageCount(1);
        assertEquals(body, mockResult.getExchanges().get(0).getIn().getBody(String.class));
        mockResult.assertIsSatisfied();
    }
}
