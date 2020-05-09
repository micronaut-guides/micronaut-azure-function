package example.micronaut;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import io.micronaut.azure.function.AzureFunction;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function extends AzureFunction {
    @Inject // <1>
    NameTransformer transformer;

    public String echo(
        @HttpTrigger(name = "req", methods = HttpMethod.POST, authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
        ExecutionContext context) {
        if (context != null) {
            context.getLogger().info("Executing Function: " + getClass().getName());
        }
        try {
            String name = request.getQueryParameters().get("name");
            return transformer.transform(name);
        } catch(ConstraintViolationException e) { // <2>
            return "The supplied name must be not blank";
        }
    }
}
