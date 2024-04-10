### How to instrument your Java applications using OpenTemetry -- Devoxx FR edition

#### To run the Datadog agent as OTel collector

Ensure to export `DD_API_KEY` then use the provided script:
```shell
export DD_API_KEY=<secret_api_key>
./run-agent.sh
```

Test the service endpoints using the `request.http` file and check the trace using [the Datadog backend](https://app.datadoghq.eu/account/login).
