package rocks.inspectit.oce.eum.server.exporters.configuration;

import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This Test class tests whether the annotations on {@link TraceExportersConfiguration#jaegerSpanExporter()} are working as expected,
 * i.e. whether the Bean only gets created when 'jaeger.enabled' is not set to DISABLED and 'jaeger.endpoint' is not empty.
 */
@SpringBootTest
class TraceExportersConfigurationTest {

    @TestPropertySource(properties = {"inspectit-eum-server.exporters.tracing.jaeger.endpoint=", "inspectit-eum-server.exporters.tracing.jaeger.enabled=ENABLED"})
    @Nested
    public class MissingEndpointTest {

        @Autowired(required = false)
        JaegerGrpcSpanExporter exporter;

        @Test
        public void testBeanWasNotCreated() {
            assertThat(exporter).isNull();
        }
    }

    @TestPropertySource(properties = {"inspectit-eum-server.exporters.tracing.jaeger.endpoint=localhost:1234", "inspectit-eum-server.exporters.tracing.jaeger.enabled=DISABLED", "inspectit-eum-server.exporters.tracing.jaeger.protocol=grpc"})
    @Nested
    public class DisabledTest {

        @Autowired(required = false)
        JaegerGrpcSpanExporter exporter;

        @Test
        public void testBeanWasNotCreated() {
            assertThat(exporter).isNull();
        }
    }

    @TestPropertySource(properties = {"inspectit-eum-server.exporters.tracing.jaeger.endpoint=localhost:1234", "inspectit-eum-server.exporters.tracing.jaeger.enabled=ENABLED", "inspectit-eum-server.exporters.tracing.jaeger.protocol=grpc"})
    @Nested
    public class BothAvailableTest {

        @Autowired
        JaegerGrpcSpanExporter exporter;

        @Test
        public void testBeanWasCreated() {
            assertThat(exporter).isNotNull();
        }
    }

}